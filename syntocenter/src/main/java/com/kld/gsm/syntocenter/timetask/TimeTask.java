package com.kld.gsm.syntocenter.timetask;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2016/4/11 11:21
 * @Description:
 */
public class TimeTask {
    DailyPollingByWhile dailyPollingByWhile;
   // TransCanAndGunStatusPolling transCanAndGunStatusPolling;
    ThirtyPolling thirtyPolling;
    public void start(){
        //开始执行上传
        dailyPollingByWhile = new DailyPollingByWhile();
        dailyPollingByWhile.start();
      /*  transCanAndGunStatusPolling = new TransCanAndGunStatusPolling();
        transCanAndGunStatusPolling.start();*/
        thirtyPolling = new ThirtyPolling();
        thirtyPolling.start();
    }
}
