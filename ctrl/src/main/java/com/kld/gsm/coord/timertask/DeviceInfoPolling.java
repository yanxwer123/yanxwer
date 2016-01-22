package com.kld.gsm.coord.timertask;

import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.servcie.IDeviceInfoService;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/26 14:18
 * @Description: 设备基础信息保存
 */
public class DeviceInfoPolling extends Thread {
    Logger logger = Logger.getLogger(DeviceInfoPolling.class);
    @Override
    public void run() {
        while(true) {
            try {
                sleep(TimeTaskPar.get("sbjcxxsjjg")*1000);
            } catch (InterruptedException e) {
                logger.error("sleep:"+e);
                e.printStackTrace();
            }
            logger.error("DeviceInfoPolling start~");
            IDeviceInfoService deviceInfoService = Context.getInstance().getBean(IDeviceInfoService.class);
            try {
                deviceInfoService.DeviceInfoSave();
            } catch (Exception e) {
                logger.error("DeviceInfoPolling error" + e);
                e.printStackTrace();
            }

        }
    }
}
