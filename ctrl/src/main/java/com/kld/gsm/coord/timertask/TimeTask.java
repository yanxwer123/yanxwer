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
    //液位仪对时
    static CheckTimePolling checkTimePolling;
    //交易流水
//    static DailyTradeAccountPolling dailyTradeAccountPolling;
    //实时库存
    static RTTimeStockThead rtTimeStockThead;
    //设备故障信息
    static EquipmentPolling equipmentPolling;
    //油罐报警
    static OilCanAlarmPolling oilCanAlarmPolling;
    //枪出罐出对比
    static OilMacStautsDataPolling oilMacStautsDataPolling;
    //液位仪开关记录
    static PowerRecordPolling powerRecordPolling;
    //脱销预警
    static SaleOutAlarmPolling saleOutAlarmPolling;
    //时点库存
    static StockThead stockThead;
    //平均时点销量表
    static TimeSaleOutPolling timeSaleOutPolling;
    //整点库存
    static TimeStockThread timeStockThread;
    //文件库存
    static FileStockThread fileStockThread;
    //班结日结同步
    static ShiftStockThread shiftStockThread;
    //罐枪同步
    static synCanAndGunInfo synCanAndGunInfoThread;
    //初始化液位仪
    static InitThread initThread;

    /**
     * 开始线程
     */
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
            //初始化液位仪
            TimeTaskPar.put("cshywysjjg",Integer.parseInt(sysManageDic.GetByCode("cshywysjjg").getValue()));
            //同步班结，日结信息
            if(null == sysManageDic.GetByCode("bjrjtb")){
                TimeTaskPar.put("bjrjtb",0);
            }else{
                TimeTaskPar.put("bjrjtb",Integer.parseInt(sysManageDic.GetByCode("bjrjtb").getValue()));
            }

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
            logger.error("同步班结日结:" + TimeTaskPar.get("bjrjtb"));
            logger.error("初始化液位仪:" + TimeTaskPar.get("cshywysjjg"));
            logger.error("~~~~~~~~~~~~~~~~~~end~~~~~~~~~~~~");
            //液位仪对时
            checkTimePolling = new CheckTimePolling();
            checkTimePolling.start();
            //交易流水
//            dailyTradeAccountPolling = new DailyTradeAccountPolling();
//            dailyTradeAccountPolling.start();
            //实时库存
            rtTimeStockThead=new RTTimeStockThead();
            rtTimeStockThead.start();
            //设备基础信息
//            DeviceInfoPolling deviceInfoPolling = new DeviceInfoPolling();
//            deviceInfoPolling.start();
            //设备故障信息
            equipmentPolling = new EquipmentPolling();
            equipmentPolling.start();
            //获取液位仪容积表（暂时不启动）
//        GetIquidCubagePolling getIquidCubagePolling = new GetIquidCubagePolling();
//        getIquidCubagePolling.start();
            //油罐报警
            oilCanAlarmPolling = new OilCanAlarmPolling();
            oilCanAlarmPolling.start();
            //枪出罐出对比
            oilMacStautsDataPolling = new OilMacStautsDataPolling();
            oilMacStautsDataPolling.start();
            //液位仪开关记录
            powerRecordPolling = new PowerRecordPolling();
            powerRecordPolling.start();
            //脱销预警
            saleOutAlarmPolling = new SaleOutAlarmPolling();
            saleOutAlarmPolling.start();
            //时点库存
            stockThead = new StockThead();
            stockThead.start();
            //平均时点销量表
            timeSaleOutPolling = new TimeSaleOutPolling();
            timeSaleOutPolling.start();
            //整点库存
            timeStockThread = new TimeStockThread();
            timeStockThread.start();
            //文件库存
            fileStockThread=new FileStockThread();
            fileStockThread.start();
            //班结日结同步
            shiftStockThread=new ShiftStockThread();
            shiftStockThread.start();
            //罐枪同步
            synCanAndGunInfoThread=new synCanAndGunInfo();
            synCanAndGunInfoThread.start();
            //初始化液位仪
            initThread=new InitThread();
            initThread.start();
        }catch(Exception e){
            logger.error(e.getMessage());
        }
    }

    /**
     * 结束线程
     */
    public static void stop(){
        //液位仪对时
        checkTimePolling.stop();
        //交易流水
//        dailyTradeAccountPolling.stop();
        //实时库存
        rtTimeStockThead.stop();
        //设备故障信息
        equipmentPolling.stop();
        //油罐报警
        oilCanAlarmPolling.stop();
        //枪出罐出对比
        oilMacStautsDataPolling.stop();
        //液位仪开关记录
        powerRecordPolling.stop();
        //脱销预警
        saleOutAlarmPolling.stop();
        //时点库存
        stockThead.stop();
        //平均时点销量表
        timeSaleOutPolling.stop();
        //整点库存
        timeStockThread.stop();
        //文件库存
        fileStockThread.stop();
        //班结日结同步
        shiftStockThread.stop();
    }
}
