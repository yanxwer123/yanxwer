package com.kld.gsm.coord.timertask;

import com.kld.gsm.ATGDevice.ATGManager;
import com.kld.gsm.coord.server.ApplicationMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2016/6/28 10:52
 * @Description:
 */
public class InitThread extends Thread {

    private static final Logger log = LoggerFactory.getLogger(InitThread.class);
    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(InitThread.class);

    @Override
    public void run() {
        while (true){
            try {
                int iSleep=TimeTaskPar.get("cshywysjjg");
                if(iSleep>1){
                    sleep(TimeTaskPar.get("cshywysjjg")*1000);
                }else{
                    sleep(15*60*1000);
                }
                try {
                    if(ATGManager.is_init==1){
                        log.info("start reload...");
                        ApplicationMain.init();
                        log.info("end reload...");
                    }
                }
                catch (Exception e){
                    log.error("reload error:"+e.getMessage());
                }
            } catch (Exception e) {
                logger.error("sleep:" + e);
                e.printStackTrace();
            }
        }

    }


}
