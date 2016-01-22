package com.kld.gsm.coord.timertask;

import com.kld.gsm.ATG.service.SysManageDic;
import com.kld.gsm.coord.Context;
import org.apache.log4j.Logger;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/12/20 11:12
 * @Description:
 */
public class TimeTask {
    public static void start(){
        Logger logger = Logger.getLogger(TimeTask.class);
        try {
            SysManageDic sysManageDic = Context.getInstance().getBean(SysManageDic.class);
            //液位仪对时间隔
            TimeTaskPar.put("ywydsjg", Integer.parseInt(sysManageDic.GetByCode("ywydsjg").getValue()));
            //动态液位异常时间间隔
            TimeTaskPar.put("dtywycsjjg", Integer.parseInt(sysManageDic.GetByCode("dtywycsjjg").getValue()));
            //时点库存间隔时间
            TimeTaskPar.put("sdkjgsj", Integer.parseInt(sysManageDic.GetByCode("sdkjgsj").getValue()));
            //油罐报警间隔时间
            TimeTaskPar.put("ygbjjgsj", Integer.parseInt(sysManageDic.GetByCode("ygbjjgsj").getValue()));
            //设备故障间隔时间
            TimeTaskPar.put("sbgzjgsj", Integer.parseInt(sysManageDic.GetByCode("sbgzjgsj").getValue()));
            //脱销时间间隔值
            TimeTaskPar.put("txsjjgz", Integer.parseInt(sysManageDic.GetByCode("txsjjgz").getValue()));
            //通知交易数据时间间隔
            TimeTaskPar.put("tzjysjsjjg", Integer.parseInt(sysManageDic.GetByCode("tzjysjsjjg").getValue()));
            //设备基础信息时间间隔
            TimeTaskPar.put("sbjcxxsjjg", Integer.parseInt(sysManageDic.GetByCode("sbjcxxsjjg").getValue()));
            //枪出罐出对比时间间隔
            TimeTaskPar.put("qcgcdbsjjg", Integer.parseInt(sysManageDic.GetByCode("qcgcdbsjjg").getValue()));
            //液位仪开关记录时间间隔
            TimeTaskPar.put("ywykgjlsjjg", Integer.parseInt(sysManageDic.GetByCode("ywykgjlsjjg").getValue()));
            //平均时点销量时间间隔
            TimeTaskPar.put("pjsdxlsjjg", Integer.parseInt(sysManageDic.GetByCode("pjsdxlsjjg").getValue()));
            //时点库存轮训时间
            TimeTaskPar.put("sdkclxsj",Integer.parseInt(sysManageDic.GetByCode("sdkclxsj").getValue()));
            //实时库存间隔
            TimeTaskPar.put("rtstockjg",Integer.parseInt(sysManageDic.GetByCode("rtstockjg").getValue()));
            //实时库存超时
            TimeTaskPar.put("rtstockcs",Integer.parseInt(sysManageDic.GetByCode("rtstockcs").getValue()));

            logger.error("开始计划任务调度配置");
            logger.error("ywydsjg液位仪对时:" + TimeTaskPar.get("ywydsjg"));
            logger.error("dtywycsjjg动态液位异常:" + TimeTaskPar.get("dtywycsjjg"));
            logger.error("sdkjgsj时点库存:" + TimeTaskPar.get("sdkjgsj"));
            logger.error("ygbjjgsj油罐报警:" + TimeTaskPar.get("ygbjjgsj"));
            logger.error("sbgzjgsj设备故障:" + TimeTaskPar.get("sbgzjgsj"));
            logger.error("txsjjgz脱销时间间隔:" + TimeTaskPar.get("txsjjgz"));
            logger.error("tzjysjsjjg通知交易数据时间间隔:" + TimeTaskPar.get("tzjysjsjjg"));
            logger.error("sbjcxxsjjg设备基础信息获取间隔:" + TimeTaskPar.get("sbjcxxsjjg"));
            logger.error("qcgcdbsjjg枪出罐出对比时间间隔:" + TimeTaskPar.get("qcgcdbsjjg"));
            logger.error("ywykgjlsjjg液位仪开关记录:" + TimeTaskPar.get("ywykgjlsjjg"));
            logger.error("pjsdxlsjjg平均时点销量间隔:" + TimeTaskPar.get("pjsdxlsjjg"));
            logger.error("sdkclxsj时点库存轮训时间:" + TimeTaskPar.get("sdkclxsj"));
            logger.error("实时库存间隔:" + TimeTaskPar.get("rtstockjg"));
            logger.error("实时库存超时:" + TimeTaskPar.get("rtstockcs"));
            logger.error("~~~~~~~~~~~~~~~~~~end~~~~~~~~~~~~");
            //液位仪对时
            CheckTimePolling checkTimePolling = new CheckTimePolling();
            checkTimePolling.start();
            //交易流水
            DailyTradeAccountPolling dailyTradeAccountPolling = new DailyTradeAccountPolling();
            dailyTradeAccountPolling.start();
            //实时库存
            RTTimeStockThead rtTimeStockThead=new RTTimeStockThead();
            rtTimeStockThead.start();
            //设备基础信息
//            DeviceInfoPolling deviceInfoPolling = new DeviceInfoPolling();
//            deviceInfoPolling.start();
            //设备故障信息
            EquipmentPolling equipmentPolling = new EquipmentPolling();
            equipmentPolling.start();
            //获取液位仪容积表（暂时不启动）
//        GetIquidCubagePolling getIquidCubagePolling = new GetIquidCubagePolling();
//        getIquidCubagePolling.start();
            //油罐报警
            OilCanAlarmPolling oilCanAlarmPolling = new OilCanAlarmPolling();
            oilCanAlarmPolling.start();
            //枪出罐出对比
            OilMacStautsDataPolling oilMacStautsDataPolling = new OilMacStautsDataPolling();
            oilMacStautsDataPolling.start();
            //液位仪开关记录
            PowerRecordPolling powerRecordPolling = new PowerRecordPolling();
            powerRecordPolling.start();
            //脱销预警
            SaleOutAlarmPolling saleOutAlarmPolling = new SaleOutAlarmPolling();
            saleOutAlarmPolling.start();
            //时点库存
            StockThead stockThead = new StockThead();
            stockThead.start();
            //平均时点销量表
            TimeSaleOutPolling timeSaleOutPolling = new TimeSaleOutPolling();
            timeSaleOutPolling.start();
            //整点库存
            TimeStockThread timeStockThread = new TimeStockThread();
            timeStockThread.start();
            //文件库存
            FileStockThread fileStockThread=new FileStockThread();
            fileStockThread.start();
        }catch(Exception e){
            logger.error(e);
        }
    }
}
