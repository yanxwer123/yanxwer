package com.kld.gsm.coord.timertask;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.servcie.IMonitorInventoryService;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
        while(true) {
            try {
                logger.error("get in 整点库存..");
                SimpleDateFormat sd = new SimpleDateFormat("mm");
                Date date = new Date();
                int minute = Integer.parseInt(sd.format(date));
                logger.info("首次进入读取当前分，休眠直到下一个整点开始保存数据。以后每小时休眠minute:"+minute);
                //首次进入读取当前分，休眠直到下一个整点开始保存数据。以后每小时休眠
                if(flag) {
                    sleep((60 - minute) * 60 * 1000);
                    flag = false;
                }else{
                    sleep(60 * 60 * 1000);
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
