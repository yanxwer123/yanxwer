package com.kld.gsm.Systemv;

import com.kld.gsm.coord.server.handler.DailyInforHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by luyan on 15/11/4.
 */
public class MQDay implements Runnable {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(MQDay.class);

    @Override
    public void run() {
        logger.info("begin to read sysv day data");
        while (true) {
            try {
                byte[] msg = SystemV.readMsg(109);
                //日结
                logger.info("receive sysv day msg");
                String strmsg = com.kld.gsm.util.ByteUtils.getBytesString(msg, 0, 19);
                logger.info("success convert sysv day data:" + strmsg);
                if(StringUtils.isEmpty(strmsg)||strmsg.equals("")) {
                    logger.info("sysv MQDay data is empty");
                }else {
                    logger.info("begin  MQDay  exec...");
                    //更新灌抢同步数据
                     Date timeDate2 = Timestamp.valueOf(strmsg);
                    logger.info("Timestamp:"+timeDate2);
                    DailyInforHandler.getInformation(timeDate2);
                }
               Thread.sleep(1);
            } catch (Exception e) {
                String msg = ExceptionUtils.getMessage(e);
                //logger.info("----failue to read sysv day data!-----" + msg);
                logger.error("----failue to read sysv day data!--e---" + e.getStackTrace());
                logger.error("----failue to read sysv day data!-----" + msg);
                // e.printStackTrace();
            }
        }
    }
}
