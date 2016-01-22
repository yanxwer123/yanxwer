package com.kld.gsm.Systemv;

import com.kld.gsm.coord.server.handler.ShiftHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;

/**
 * Created by luyan on 15/11/4.
 * 班结线程
 */
public class MQShift  implements Runnable {
    private static Logger logger= org.slf4j.LoggerFactory.getLogger(MQShift.class);

    @Override
    public void run() {
        //System.out.println("开始监控班结消息队列");
        ShiftHandler shiftHandler = new ShiftHandler();
        while (true) {
            try {

                logger.info("start byte[] msg = SystemV.readMsg(108);");
                byte[] msg = SystemV.readMsg(108);
                //班结
                String strmsg=com.kld.gsm.util.ByteUtils.getBytesString(msg,0,10);
                if(StringUtils.isEmpty(strmsg)||strmsg.equals("")) {
                    logger.info("strmsg is null");
                }else {
                    logger.info("MQShift strmsg is :" + strmsg);
                    //保存班结信息
                    //DateUtil.convertStringToDate(strmsg);
                    shiftHandler.saveShiftInfo(strmsg);
                }
                Thread.sleep(1);
            } catch (Exception e) {
                String msg= ExceptionUtils.getMessage(e);
                logger.error("----监控班结消息队列失败!-----"+msg);
                logger.error("----监控班结消息队列失败!-----" +e.getStackTrace());
                e.printStackTrace();
            }
        }
    }
}
