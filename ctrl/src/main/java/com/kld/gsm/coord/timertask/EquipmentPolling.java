package com.kld.gsm.coord.timertask;

import com.kld.gsm.ATGDevice.ATGManager;
import com.kld.gsm.ATGDevice.atg_device_out_t;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.servcie.IEquipmentService;
import com.kld.gsm.coord.servcie.impl.EquipmentNewServiceImpl;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/26 10:52
 * @Description:采集设备故障信息存入设备故障表。
 */
public class EquipmentPolling extends Thread {
    Logger logger = Logger.getLogger(EquipmentPolling.class);
    IEquipmentService equipmentService=null;

    @Override
    public void run(){
        RuntimeMXBean rt = ManagementFactory.getRuntimeMXBean();
        String pid = rt.getName();
        MDC.put("PID", pid);
        equipmentService = Context.getInstance().getBean(EquipmentNewServiceImpl.class);
        while(true) {
            try {
                sleep(TimeTaskPar.get("sbgzjgsj")*1000);
            } catch (InterruptedException e) {
                logger.info("sleep:" + e);
                e.printStackTrace();
            }
            try {
                logger.error("come into EquipmentPolling设备故障信息...");
                //IEquipmentService equipmentService = Context.getInstance().getBean(IEquipmentService.class);
                //equipmentService.EquipmentSave();
                Integer iRet= getEquipment();
                if(iRet.equals(1))
                {
                    logger.info("EquipmentPolling Timeout");
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("EquipmentPolling Error" + e);
            }


        }
    }


    public Integer getEquipment(){
        Integer iRet=new Integer("1");
        //region oldcode
        /*ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask<Integer> future =
                new FutureTask<Integer>(new Callable<Integer>() {//使用Callable接口作为构造参数
                    public Integer call() {
                        try
                        {
                            equipmentService.EquipmentSave();
                            logger.info("equipmentService.EquipmentSave()");
                        }
                        catch(Exception e) {
                        }
                        return 0;
                    }});
        executor.execute(future);*/
        //endregion
        final ExecutorService exec = Executors.newFixedThreadPool(1);
        Callable<Integer> call = new Callable<Integer>() {
            public Integer call() throws Exception {
                try {
                    logger.info("====================equipmentService.EquipmentSave start:" + new Date().toString() + "=================");
                    equipmentService.EquipmentSave();
                    return 1;
                }catch (Exception e){
                    return 0;
                }
            }
        };
        //在这里可以做别的任何事情
        logger.info("do other things");
        Future<Integer> future = exec.submit(call);
        try {
            iRet= future.get(1000*(TimeTaskPar.get("sbgzjgsj")-1), TimeUnit.MILLISECONDS); //取得结果，同时设置超时执行时间为5秒。同样可以用future.get()，不设置执行超时时间取得结果
            logger.info("取设备故障结束");
        }catch (TimeoutException ex){
            logger.info("取设备故障超时");
            future.cancel(true);
        }
        catch (Exception e) {
            logger.info("取设备故障异常");
            future.cancel(true);
        }
        exec.shutdownNow();
        logger.info("return");
        return iRet;
    }
}
