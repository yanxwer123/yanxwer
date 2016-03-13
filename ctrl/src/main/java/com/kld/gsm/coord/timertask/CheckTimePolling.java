package com.kld.gsm.coord.timertask;

import com.kld.gsm.ATGDevice.ATGManager;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/22 11:10
 * @Description:液位仪对时
 */
public class CheckTimePolling extends Thread{
    Logger logger = Logger.getLogger(CheckTimePolling.class);
    @Override
    public void run() {
        RuntimeMXBean rt = ManagementFactory.getRuntimeMXBean();
        String pid = rt.getName();
        MDC.put("PID", pid);
        while(true) {
            logger.error("come in to CheckTimePolling()液位仪对时...");
            try {
                Integer iRet=getCheckTime();
                if(iRet.equals(1))
                {
                    logger.error("CheckTimePolling Timeout");
                }
            } catch (Exception e) {
                logger.error("CheckTimeUtil s-->ATGManager.checkTime is error" + e);
                e.printStackTrace();
            }finally {
                try {
                    sleep(TimeTaskPar.get("ywydsjg") * 1000);
                } catch (InterruptedException e) {
                    logger.error("sleep:" + e);
                    e.printStackTrace();
                }
            }
        }
    }

    public Integer getCheckTime(){
        Integer iRet=new Integer("1");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask<Integer> future =
                new FutureTask<Integer>(new Callable<Integer>() {//使用Callable接口作为构造参数
                    public Integer call() {
                        try
                        {
                            Date date = new Date();
                            SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
                            ATGManager.checkTime(sd.format(date));
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
        } finally {
            executor.shutdown();
        }
        return 1;
    }
}
