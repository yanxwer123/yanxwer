package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.dao.MonitorInventoryDao;
import com.kld.gsm.ATG.dao.MonitorTimeInventoryDao;
import com.kld.gsm.ATG.dao.SysManageCanInfoDao;
import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.ATGDevice.*;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.servcie.IMonitorInventoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/16 17:21
 * @Description:
 */
@Service
@SuppressWarnings("all")
public class MonitorInventoryServiceImpl implements IMonitorInventoryService {
    Logger logger = Logger.getLogger(MonitorInventoryServiceImpl.class);

    @Autowired
    private MonitorInventoryDao monitorInventoryDao;
    @Autowired
    SysManageCanInfoDao sysManageCanInfodao;
    static List canList = new ArrayList();
    @Override
    public int saveMessage() throws Exception{
        int ret1=0;
        logger.info("come into saveMessage()...");
        if(canList.size()==0) {
            logger.info("整点库存初始化参数、、");
            initStockParameters();
        }
        Object obj = ATGManager.getStock(canList);//取得返回值
        logger.info("canList.size():"+canList.size());
        String error = ATGManager.checkReturnData(obj);//判断返回值类型
        if (!this.StringIsNull(error)) {//如果返回值类型不是空则打印错误消息
            logger.error(error);
        }
        List<atg_stock_data_out_t> stockOutList = (ArrayList) obj;//转为需要的格式
        logger.info("stockOutList.size():" + stockOutList.size());
        for(atg_stock_data_out_t stock : stockOutList){//保存整点库存
            //开始存数据
            logger.info("保存整点库存开始、、、");
            MonitorInventory monitorInventory = new MonitorInventory();
            stockData2MonitorInventory(stock, monitorInventory);
            ret1 = monitorInventoryDao.insert(monitorInventory);
        }
        logger.info("end saveMessage()..");
        return ret1;
    }

    /**
     * 液位仪赋值整点库存
     *
     * @param stock
     * @param monitorInventory
     */
    public void stockData2MonitorInventory(atg_stock_data_out_t stock, MonitorInventory monitorInventory) throws Exception {
        SimpleDateFormat sd2 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sd3 = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        monitorInventory.setOilcanno(stock.uOilCanNo);
        monitorInventory.setDate(sd2.format(date));
        monitorInventory.setTime(sd3.format(date));
        monitorInventory.setOilcubage(stock.fOilCubage);
        monitorInventory.setStandcubage(stock.fOilStandCubage);
        monitorInventory.setEmptycubage(stock.fEmptyCubage);
        monitorInventory.setTotalheight(stock.fTotalHeight);
        monitorInventory.setWaterheight(stock.fWaterHeight);
        monitorInventory.setTemp(stock.fOilTemp);
        monitorInventory.setTemp1(stock.fOilTemp1);
        monitorInventory.setTemp2(stock.fOilTemp2);
        monitorInventory.setTemp3(stock.fOilTemp3);
        monitorInventory.setTemp4(stock.fOilTemp4);
        monitorInventory.setTemp5(stock.fOilTemp5);
        monitorInventory.setWaterbulk(stock.fWaterBulk);
        monitorInventory.setApparentdensity(stock.fApparentDensity);
        monitorInventory.setStanddensity(stock.fStandDensity);
        monitorInventory.setTranstatus("0");
        sysManageCanInfodao = Context.getInstance().getBean(SysManageCanInfoDao.class);
        SysManageCanInfo sysManageCanInfo = sysManageCanInfodao.selectByPrimaryKey(stock.uOilCanNo);
        if (sysManageCanInfo != null) {
            monitorInventory.setOilno(sysManageCanInfo.getOilno());
        }
        logger.info("monitorInventory:" + monitorInventory);
    }
    public boolean StringIsNull(String s){
        if(s!=null&&"".equals(s)){
            return true;
        }
        return false;
    }
    public List<atg_timestock_data_in_t> initStockParameters(){
        //获取所有罐号
        List<SysManageCanInfo> oilCanInforList = sysManageCanInfodao.selectAll();
        List<atg_timestock_data_in_t> timeStockList = new ArrayList<atg_timestock_data_in_t>();
        logger.info("oilCanInforList.size:"+oilCanInforList.size());
        for (SysManageCanInfo o : oilCanInforList) {
            logger.info("初始化整点库存传入参数o.getOilcan():"+o.getOilcan());
            canList.add(o.getOilcan());
        }
        return canList;
    }
}
