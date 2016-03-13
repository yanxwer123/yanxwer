package com.kld.gsm.coord.timertask;

import com.kld.gsm.ATG.domain.DailyPowerRecord;
import com.kld.gsm.ATGDevice.ATGManager;
import com.kld.gsm.ATGDevice.atg_powerrecord_data_out_t;
import com.kld.gsm.ATGDevice.atg_powerrecord_in_t;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.servcie.DailyPowerService;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="yanxwer@163.com">yanxiaowei</a>
 * @version 1.0     d
 * @CreationTime: 2015/11/24 14:27
 * @Description:液位仪开关记录
 */
public class PowerRecordPolling extends Thread {
    Logger logger = Logger.getLogger(PowerRecordPolling.class);
    @Autowired
    private DailyPowerService dailyPowerService;
    String init_date = "";
    @Override
    public void run() {
        RuntimeMXBean rt = ManagementFactory.getRuntimeMXBean();
        String pid = rt.getName();
        MDC.put("PID", pid);
        while(true) {
            try {
                sleep(TimeTaskPar.get("ywykgjlsjjg")*1000);
            } catch (InterruptedException e) {
                logger.error("sleep:" + e);
                e.printStackTrace();
            }
            try {
                Integer iRet=getPowerRecordThread();
                if(iRet.equals(1))
                {
                    logger.error("FileStockThread Write File Timeout");
                }
            } catch (Exception e) {
                logger.error("PowerRecord-->ATGManager.PowerRecord is error"+e);
                e.printStackTrace();
            }

        }
    }

    public void getPowerRecord()
    {
        logger.error("get in PowerRecord 进入液位仪开关记录...");
        Date date = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        atg_powerrecord_in_t in_t = new atg_powerrecord_in_t();
        //第一次进入的时候初始化查询时间为当前时间，第二次使用第一次的查询时间。
        if("".equals(init_date)) {
            init_date = sd.format(date);
        }
        in_t.strDataTime = init_date;
        in_t.uReqCount = 20;
        List list = new ArrayList();
        list.add(in_t);
        init_date = sd.format(date);
        List<atg_powerrecord_data_out_t> out_ts = ATGManager.getPowerRecord(list);
        logger.info("液位仪开关记录out_ts.size():" + out_ts.size());
        for (atg_powerrecord_data_out_t out_t : out_ts) {
            try {
                DailyPowerRecord dailyPowerRecord = new DailyPowerRecord();
                dailyPowerRecord.setDate(out_t.strDate);
                dailyPowerRecord.setTime(out_t.strTime);
                dailyPowerRecord.setOperatetype(out_t.strOperateType);
                dailyPowerRecord.setOilcanno(out_t.uOilCanNO);
                dailyPowerRecord.setTotalheight(out_t.fTotalHeight);
                dailyPowerRecord.setWaterheight(out_t.fWaterHeight);
                dailyPowerRecord.setOiltemp(out_t.fOilTemp);
                dailyPowerRecord.setOiltemp1(out_t.fOilTemp1);
                dailyPowerRecord.setOiltemp2(out_t.fOilTemp2);
                dailyPowerRecord.setOiltemp3(out_t.fOilTemp3);
                dailyPowerRecord.setOiltemp4(out_t.fOilTemp4);
                dailyPowerRecord.setOiltemp5(out_t.fOilTemp5);
                dailyPowerRecord.setOilcubage(out_t.fOilCubage);
                dailyPowerRecord.setOilstandcubage(out_t.fOilStandCubage);
                dailyPowerRecord.setEmptycubage(out_t.fEmptyCubage);
                dailyPowerRecord.setWaterbulk(out_t.fWaterBulk);
                logger.info("dailyPowerRecord:" + dailyPowerRecord);
                dailyPowerService = Context.getInstance().getBean(DailyPowerService.class);
                if(dailyPowerService.selectByPrimaryKey(dailyPowerRecord)!=null){
                    logger.error("有重复的数据跳过");
                    continue;
                }
                dailyPowerService.insertSelective(dailyPowerRecord);
                logger.info("液位仪开关记录保存完成。。。。。");
            }catch(Exception e){
                logger.error(e);
                e.printStackTrace();
            }
        }
    }

    public Integer getPowerRecordThread(){
        Integer iRet=new Integer("1");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask<Integer> future =
                new FutureTask<Integer>(new Callable<Integer>() {//使用Callable接口作为构造参数
                    public Integer call() {
                        try
                        {
                            getPowerRecord();
                        }
                        catch(Exception e) {
                        }
                        return 0;
                    }});
        executor.execute(future);
        //在这里可以做别的任何事情
        try {
            iRet= future.get(25000, TimeUnit.MILLISECONDS); //取得结果，同时设置超时执行时间为5秒。同样可以用future.get()，不设置执行超时时间取得结果
        } catch (InterruptedException e) {
            future.cancel(true);
        } catch (ExecutionException e) {
            future.cancel(true);
        } catch (TimeoutException e) {
            future.cancel(true);
        }
        executor.shutdownNow();
        return 1;
    }
}
