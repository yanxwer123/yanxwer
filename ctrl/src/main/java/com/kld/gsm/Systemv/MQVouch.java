package com.kld.gsm.Systemv;

import com.kld.gsm.coord.server.handler.TransacHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;

/**
 * Created by luyan on 15/10/31.
 * 多线程监控消息队列交易数据
 */

public class MQVouch implements Runnable {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(MQVouch.class);

    @Override
    public void run() {
        logger.info("begin watch sysv  vouch data");

        while (true) {
            try {
                byte[] msg = SystemV.readMsg(107);
                logger.info("receive sysv vouch data");
                int macno = com.kld.gsm.util.ByteUtils.bytesToInt(msg, 0);
                int gunno = com.kld.gsm.util.ByteUtils.bytesToInt(msg, 4);
                int ttc = com.kld.gsm.util.ByteUtils.bytesToInt(msg, 8);
                String strmsg = com.kld.gsm.util.ByteUtils.getBytesString(msg, 12, 19);
                if (StringUtils.isEmpty(strmsg) || strmsg.equals("")) {
                    logger.info("sysv MQVouch data is empty");
                } else {
                    logger.info("begin  MQVouch  exec...");
                    logger.info("macno:" + macno);
                    logger.info("gunno:" + gunno);
                    logger.info("ttc:" + ttc);
                    logger.info("strmsg:" + strmsg);
                    //交易
                    //DateUtil.convertStringToDate(strmsg);
                    if(0==macno&&0==gunno&&0==ttc){
                        logger.error("MQVouch parameters are null");
                        continue;
                    }else {
                        TransacHandler.getInformation(macno, gunno, ttc, strmsg);
                    }
                }
                Thread.sleep(1);
            } catch (Exception e) {
                String msg = ExceptionUtils.getMessage(e);
                //logger.info("----failue to watch sysv vouch data!-----"+msg);
                logger.error("----failue to watch sysv vouch data!-----" + msg);
                // e.printStackTrace();
            }

        }
    }
}


