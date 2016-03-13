package com.kld.gsm.coord.timertask;

import com.kld.gsm.ATGDevice.ATGManager;
import com.kld.gsm.ATGDevice.atg_stock_data_out_t;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.servcie.ISaleOutAlarmService;
import org.apache.avalon.framework.ExceptionUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import javax.annotation.Resource;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/22 11:39
 * @Description: 脱销报警保存
 * 1、10分钟查一次实时库存
 * 2、根据平均时点销量判断实时库存的预计销售时间
 * 3、如果预计销售时间小于设置的脱销报警时间则保存一条脱销报警记录（一直在报警状态则不新增新的脱硝报警，直到报警结束）
 * 4、如果预计销售时间大于设置的脱销报警时间则更新脱硝报警记录的结束时间为当前时间。
 */
public class SaleOutAlarmPolling extends Thread {
    Logger logger = Logger.getLogger(SaleOutAlarmPolling.class);
    @Resource
    ISaleOutAlarmService saleOutAlarmService;
    List<Integer> inList = null;

    @Override
    public void run() {
        RuntimeMXBean rt = ManagementFactory.getRuntimeMXBean();
        String pid = rt.getName();
        MDC.put("PID", pid);
        saleOutAlarmService = Context.getInstance().getBean(ISaleOutAlarmService.class);
        while(true) {
            try {
                sleep(TimeTaskPar.get("txsjjgz")*1000);
            } catch (InterruptedException e) {
                logger.error("脱销预警获取休眠参数异常:" + e);
                e.printStackTrace();

            }
            try {
                logger.error("come into SaleOutAlarmPolling 脱销预警...");
                /*if (inList == null || inList.size()==0) {
                    //查询到所有油罐编号
                    inList = saleOutAlarmService.selectAllOilCanNos();
                }*/
                //logger.info("ATGManager.getStock(inList)...inList.size():" + inList.size());
                List<atg_stock_data_out_t> outList = ATGManager.getStock(new ArrayList<Integer>());
                //logger.info("List<atg_stock_data_out_t> outList.size:" + outList.size());
                //logger.info("outList:" + outList);
                saleOutAlarmService.saleOutAlarmSave(outList);
                logger.info("脱销预警保存完成。。。");
            } catch (Exception e) {
                logger.error("脱销预警异常:"+e,e);

            }

        }
    }
}
