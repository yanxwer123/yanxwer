package com.kld.gsm.coord.timertask;

import com.kld.gsm.ATG.dao.DailyDailyBalanceDao;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.dao.OilDayStatDao;
import com.kld.gsm.coord.domain.OilDayStat;
import com.kld.gsm.coord.domain.Payoilstat;
import com.kld.gsm.coord.servcie.IShiftService;
import com.kld.gsm.coord.server.handler.DailyInforHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2016/6/28 10:52
 * @Description:
 */
public class ShiftStockThread extends Thread {

    private static final Logger log = LoggerFactory.getLogger(ShiftStockThread.class);
    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ShiftStockThread.class);


    @Override
    public void run() {

        try {
            //查询班结信息表 oss_daily_ShiftStock
            logger.info("进入班结查询");
            IShiftService shiftService = Context.getInstance().getBean(IShiftService.class);
            List<Payoilstat> shiftList=shiftService.selectDifferent();
            if(shiftList!=null && shiftList.size()>0){
                logger.info("进入班结更新阶段 ");
                for(Payoilstat Shift :shiftList ){
                    Date d = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    String dateNowStr = sdf.format(d);
                    String maxshift=dateNowStr+"99";
                    if(Integer.parseInt(Shift.getTeamvouchno())>Integer.parseInt(maxshift)){
                        continue;
                    }
                    String shiftno = Shift.getTeamvouchno();
                    logger.info("进入班结赋值"+shiftno);
                    shiftService.saveShiftInfo(shiftno);
                }
            }else{
                logger.info("没有未同步的班结数据");

            }
        }
        catch (Exception e){
            log.error("ShiftStock error:"+e.getMessage());
        }

        try{
            //查询日结信息表 oss_daily_DailyBalanc
            logger.info("进入日结查询");
            DailyDailyBalanceDao dailyDailyBalanceDao=Context.getInstance().getBean(DailyDailyBalanceDao.class);
            OilDayStatDao oilDayStatDao=Context.getInstance().getBean(OilDayStatDao.class);

            Date accountDate= dailyDailyBalanceDao.selectLastDate();
            if(accountDate!=null && !"".equals(accountDate) ){
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String AccountDate=sd.format(accountDate);
                List<OilDayStat> timeDateList=oilDayStatDao.findByDate(AccountDate);
                if(null!=timeDateList && timeDateList.size()>0){
                    for(OilDayStat Dailday :timeDateList){
                        if(Dailday.getAccountdate().after(new Date())){
                            continue;
                        }
                        Date timedate=Dailday.getAccountdate();
                        logger.info("进入日结操作" + timedate);

                        String t=sd.format(timedate);
                        Date timeDate = Timestamp.valueOf(t);
                        logger.info("日结操作转换" + timeDate);
                        DailyInforHandler. getInformation(timeDate);
                    }
                }else{
                    logger.info("没有未同步的日结数据");

                }
            }else{
                logger.info("日结时间为空");
            }

        } catch (Exception e){
            log.error("oss_daily_DailyBalanceStock error:"+e.getMessage());
        }
        while (true){

            try {
                int iSleep=TimeTaskPar.get("bjrjtb");
                if(iSleep>1){
                    sleep(TimeTaskPar.get("bjrjtb")*100);
                }else{
                    sleep(9000*100);
                }
            } catch (InterruptedException e) {
                logger.error("sleep:" + e);
                e.printStackTrace();
            }
        }

    }


}
