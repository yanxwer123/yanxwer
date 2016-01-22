package com.kld.gsm.coord.timertask;

import com.kld.gsm.ATG.dao.SysManageTimeSaleOutDao;
import com.kld.gsm.coord.Context;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/12/14 11:09
 * @Description:每天凌晨更新平均时点销量表
 */
public class TimeSaleOutPolling extends Thread {
    Logger logger = Logger.getLogger(TimeSaleOutPolling.class);
    boolean flag = true;
    @Override
    public void run(){
        SysManageTimeSaleOutDao sysManageTimeSaleOutDao = Context.getInstance().getBean(SysManageTimeSaleOutDao.class);
        while(true) {
            //首次进入判断，首次进入直接睡到第二天凌晨，以后都是凌晨运行
            /*if(flag){
                SimpleDateFormat sd = new SimpleDateFormat("HH");
                Date date = new Date();
                int now = Integer.parseInt(sd.format(date));
                try {
                    sleep((24-now)*60*60*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.error("平均时点销量表异常:"+e.getMessage());
                }
                flag = false;
            }else {*/
                try {
                    sleep(TimeTaskPar.get("pjsdxlsjjg")*1000);
                } catch (InterruptedException e) {
                    logger.error("sleep:" + e);
                    e.printStackTrace();
                }
            //}
            try {
                logger.error("进入TimeSaleOutPolling 平均时点销量表...");
                //删除平均时点销量表
                logger.info("删除平均时点销量表..");
                int delRet = sysManageTimeSaleOutDao.deleteAll();
                logger.info("delRet:" + delRet);
                //根据交易加油流水表oss_daily_TradeAccount算出平均时点销量，并存入删除平均时点销量表
                logger.info("根据交易加油流水表算出平均时点销量，并存入删除平均时点销量表..");
                int insRet = sysManageTimeSaleOutDao.insertAll();
                logger.info("insRet:" + insRet);
            } catch (Exception ex) {
                logger.error("平均时点销量表异常 error:" + ex);
            }

        }
    }
}
