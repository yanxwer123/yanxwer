package com.kld.gsm.coord.timertask;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.servcie.IMonitorInventoryService;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by IntelliJ IDEA.
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/16 13:53
 * @Description: 整点库存
 */
public class TimeStockThread extends Thread {
    Logger logger = Logger.getLogger(TimeStockThread.class);
    boolean flag = true;
    @Override
    public void run(){
        RuntimeMXBean rt = ManagementFactory.getRuntimeMXBean();
        String pid = rt.getName();
        MDC.put("PID", pid);
        SimpleDateFormat sd = new SimpleDateFormat("mm");
        SimpleDateFormat sd2 = new SimpleDateFormat("ss");
        while(true) {
            try {
                logger.error("get in 整点库存..");
                //首次进入读取当前分，休眠直到下一个整点开始保存数据。以后每小时休眠
                if(flag) {
                    Date date = new Date();
                    int minute = Integer.parseInt(sd.format(date));
                    int second = Integer.parseInt(sd2.format(date));
                    logger.info("首次进入读取当前分，休眠直到下一个整点开始保存数据。以后每小时休眠minute:"+minute);
                    sleep(((60 - minute) * 60 - second) * 1000);
                    flag = false;
                }else{
                    Date date_minute = new Date();
                    int minute_difference = Integer.parseInt(sd.format(date_minute));
                    int second_difference = Integer.parseInt(sd2.format(date_minute));
                    sleep(((60-minute_difference) * 60 - second_difference) * 1000);
                }
            } catch (InterruptedException e) {
                logger.error("整点库存异常:"+e);
                e.printStackTrace();
            }
            try {
                IMonitorInventoryService monitorInventoryService = Context.getInstance().getBean(IMonitorInventoryService.class);
                logger.info("come into TimeStockThread...");
                int ret = monitorInventoryService.saveMessage();
                if (ret == 0) {
                    logger.error("monitorInventoryService.saveMessage() return is 0!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
