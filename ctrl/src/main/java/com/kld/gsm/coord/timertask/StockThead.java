package com.kld.gsm.coord.timertask;

import com.kld.gsm.ATG.dao.MonitorTimeInventoryDao;
import com.kld.gsm.ATG.dao.SysManageCanInfoDao;
import com.kld.gsm.ATG.domain.MonitorTimeInventory;
import com.kld.gsm.ATG.domain.SysManageCanInfo;
import com.kld.gsm.ATGDevice.ATGManager;
import com.kld.gsm.ATGDevice.atg_stock_data_out_t;
import com.kld.gsm.ATGDevice.atg_timestock_data_in_t;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.servcie.IMonitorInventoryService;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/16 10:42
 * @Description: 时点库存
 */
public class StockThead extends Thread {
    int count = 0;
    Logger logger = Logger.getLogger(StockThead.class);
    static List canList = new ArrayList();
    @Autowired
    private MonitorTimeInventoryDao monitortimeInventoryDao;
    @Autowired
    SysManageCanInfoDao sysManageCanInfodao;
    @Override
    public void run(){
        RuntimeMXBean rt = ManagementFactory.getRuntimeMXBean();
        String pid = rt.getName();
        MDC.put("PID", pid);
        sysManageCanInfodao = Context.getInstance().getBean(SysManageCanInfoDao.class);
        boolean firstflag = true;
        while(true){
            //启动先睡眠十秒钟
            try {
                if(firstflag) {
                    logger.info("时点库存第一次进入睡眠十秒开始");
                    sleep(10 * 1000);
                    logger.info("时点库存第一次进入睡眠十秒结束");
                    firstflag = false;
                }
            } catch (InterruptedException e) {
                logger.error("时点库存sleep:" + e);
                e.printStackTrace();
            }
            //region 时点库存
            try
            {
                    logger.error("get in 时点库存");
                    //初始化罐号
                   /* if (canList.size() == 0) {
                        logger.info("进入时点库存初始化罐号");
                        initTimeStockParameters();
                    }*/
                    //获取罐存值
                    Object obj = ATGManager.getStock(new ArrayList());//取得返回值
                    String error = ATGManager.checkReturnData(obj);//判断返回值类型
                    if (!this.StringIsNull(error)) {//如果返回值类型不是空则打印错误消息
                        //System.out.println("时点库存检查失败."+error);
                        logger.error(error);
                    }
                    List<atg_stock_data_out_t> stockList = (ArrayList) obj;//转为需要的格式

                    for (atg_stock_data_out_t stock : stockList) {
                        logger.info("count>");
                        //保存时点库存
                        logger.info("开始保存时点库存...");
                        MonitorTimeInventory monitortimeInventory = new MonitorTimeInventory();
                        stockData2MonitorTimeInventory(stock, monitortimeInventory);
                        monitortimeInventoryDao = Context.getInstance().getBean(MonitorTimeInventoryDao.class);
                        monitortimeInventoryDao.insertSelective(monitortimeInventory);
                        logger.info("结束保存时点库存...");
                        logger.info("end monitortimeInventoryDao.insertSelective...");
                    }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            try {
                sleep(TimeTaskPar.get("sdkjgsj") * 1000);
            } catch (InterruptedException e) {
                logger.error("时点库存sleep:" + e);
                e.printStackTrace();
            }
            //endregion
        }
    }
    /**
     * 初始化实时库存传入参数
     * @return
     */
    public List<atg_timestock_data_in_t> initTimeStockParameters() {
        //获取所有罐号
        List<SysManageCanInfo> oilCanInforList = sysManageCanInfodao.selectAll();
        List<atg_timestock_data_in_t> timeStockList = new ArrayList<atg_timestock_data_in_t>();
        logger.info("oilCanInforList.size:" + oilCanInforList.size());
        for (SysManageCanInfo o : oilCanInforList) {
            logger.info("初始化时点库存传入参数o.getOilcan():" + o.getOilcan());
            canList.add(o.getOilcan());
        }
        return canList;
    }
    /**
     * 液位仪赋值时点库存
     * @param stock
     */
    public void stockData2MonitorTimeInventory(atg_stock_data_out_t stock,MonitorTimeInventory monitorTimeInventory) throws Exception{
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        monitorTimeInventory.setOilcan(stock.uOilCanNo);
        monitorTimeInventory.setStoretime(sd.parse(stock.strDate + stock.strTime));
        monitorTimeInventory.setOill(stock.fOilCubage);
        monitorTimeInventory.setStandardl(stock.fOilStandCubage);
        monitorTimeInventory.setVolumeempty(stock.fEmptyCubage);
        monitorTimeInventory.setHeighttotal(stock.fTotalHeight);
        monitorTimeInventory.setHeightwater(stock.fWaterHeight);
        monitorTimeInventory.setTemperature(stock.fOilTemp);
        monitorTimeInventory.setTemp1(stock.fOilTemp1);
        monitorTimeInventory.setTemp2(stock.fOilTemp2);
        monitorTimeInventory.setTemp3(stock.fOilTemp3);
        monitorTimeInventory.setTemp4(stock.fOilTemp4);
        monitorTimeInventory.setTemp5(stock.fOilTemp5);
        monitorTimeInventory.setWaterl(stock.fWaterBulk);
        monitorTimeInventory.setDensities(stock.fApparentDensity);
        monitorTimeInventory.setDensitiesstandard(stock.fStandDensity);
        monitorTimeInventory.setCreatetime(new Date());
        monitorTimeInventory.setTranstatus("0");
        SysManageCanInfo sysManageCanInfo = sysManageCanInfodao.selectByPrimaryKey(stock.uOilCanNo);
        if(sysManageCanInfo!=null) {
            monitorTimeInventory.setOilno(sysManageCanInfo.getOilno());
        }
        logger.info("monitorTimeInventory:" + monitorTimeInventory);
    }

    public boolean StringIsNull(String s) {
        if (s != null && "".equals(s)) {
            return true;
        }
        return false;
    }
}