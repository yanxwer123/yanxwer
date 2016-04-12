package com.kld.gsm.syntocenter.timetask;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2016/4/11 11:46
 * @Description:
 */
public class TransCanAndGunStatusPolling extends Thread{
    Logger LOGGER = Logger.getLogger(this.getClass());
    DailyPollingByWhile dailyPollingByWhile;
    @Override
    public void run() {
        dailyPollingByWhile = new DailyPollingByWhile();
        while(true){
            try {
                sleep(5 * 1000);
                LOGGER.info("transCanAndGunStatus开始五秒上传");
                dailyPollingByWhile.transCanAndGunStatus();
                LOGGER.info("transCanAndGunStatus结束五秒上传");
            }catch (Exception e){
                e.printStackTrace();
                LOGGER.error(e.getMessage());
            }
        }
    }
}
