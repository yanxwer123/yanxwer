package com.kld.gsm.syntocenter.timetask;

import com.kld.gsm.ATG.service.SysManageDic;
import com.kld.gsm.syntocenter.springContext.springFactory;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2016/4/12 12:56
 * @Description:
 */
public class ThirtyPolling extends Thread {

    private static final Logger LOG = Logger.getLogger("DailyPolling");
    DailyPollingByWhile dailyPollingByWhile;
    @Override
    public void run() {
        SysManageDic sysManageDic = springFactory.getInstance().getBean(SysManageDic.class);
        dailyPollingByWhile = new DailyPollingByWhile();
        while(true){
            try {
                int sleepTime = 1800;
                LOG.info("sleepTime:"+sleepTime);
                if(null!=sysManageDic.GetByCode("thirty")) {
                    sleepTime = Integer.parseInt(sysManageDic.GetByCode("thirty").getValue());
                    LOG.info("sleepTime:"+sleepTime);
                }
                LOG.info("sleepTime:"+sleepTime);
                sleep(sleepTime * 1000);
                LOG.info("开始执行30分钟一次的上传");
                dailyPollingByWhile.thirty();
                LOG.info("结束执行30分钟一次的上传");
            }catch(Exception e){
                e.printStackTrace();
                LOG.error(e.getMessage());
            }
        }
    }
}
