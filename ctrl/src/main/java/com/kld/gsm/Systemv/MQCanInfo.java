package com.kld.gsm.Systemv;

import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.servcie.TankGunBasedDataService;
import com.kld.gsm.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 * Created by luyan on 15/11/5.
 */
public class MQCanInfo implements Runnable {
    private static Logger logger= org.slf4j.LoggerFactory.getLogger(MQCanInfo.class);

    @Override
    public void run() {
        logger.info("begin to read sysv caninfo data");
        while (true) {
            try {

                byte[] msg = SystemV.readMsg(110);
                //罐枪配置变更
                String strmsg=com.kld.gsm.util.ByteUtils.getBytesString(msg,0,19);
                logger.info("sysv caninfo data:" + strmsg);
                if(StringUtils.isEmpty(strmsg)||strmsg.equals("")) {
                    logger.info("sysv caninfo data is empty");
                }else {
                    logger.info("begin sync caninfo...");
                    //更新灌抢同步数据
                    DateUtil.convertStringToDate(strmsg);
                    String id = null;
                    TankGunBasedDataService tankGunBasedDataService = Context.getInstance().getBean(TankGunBasedDataService.class);
                    tankGunBasedDataService.saveTankGunBasedData(id);
                }
                Thread.sleep(1);
            } catch (Exception e) {
                String msg= ExceptionUtils.getMessage(e);
                logger.error("----failue to read sysv caninfo data!-----"+msg);
                // e.printStackTrace();
            }

        }
    }
}
