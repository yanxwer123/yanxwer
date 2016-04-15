package com.kld.gsm.syntocenter.timetask;

import com.kld.gsm.ATG.domain.AcceptanceDeliveryBills;
import com.kld.gsm.ATG.domain.SysManageCanInfo;
import com.kld.gsm.ATG.domain.SysManageCubage;
import com.kld.gsm.ATG.domain.SysManageDepartment;
import com.kld.gsm.ATG.service.*;
import com.kld.gsm.syntocenter.server.ApplicationMain;
import com.kld.gsm.syntocenter.server.CanAndGunStatus;
import com.kld.gsm.syntocenter.service.*;
import com.kld.gsm.syntocenter.springContext.springFactory;
import com.kld.gsm.syntocenter.util.action;
import com.kld.gsm.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/*
Created BY niyang
Created Date 2015/11/19
*/

public class DailyPollingByWhile extends Thread {


    @Autowired
    private synMonitor sys;

    @Autowired
    private AcceptSevices acceptSevices;

    @Autowired
    private SysmanageService sysmanageService;

    @Autowired
    private synSysManage synsysmanage;

    @Autowired
    private synAlarm synalarm;

    @Autowired
    private StationRegServices stationRegServices;

    @Autowired
    private synDailyRunning syndailyrunning;

    @Autowired
    private DailyRunning dailyRunning;

    @Autowired
    private SysManageDic sysManageDic;

    @Autowired
    private synMaclog sml;

    private static final Logger LOG = Logger.getLogger("DailyPolling");

    @Override
    public void run() {
        SysManageDic sysManageDic = springFactory.getInstance().getBean(SysManageDic.class);
        while(true){
            try {
                int sleepTime = 600;
                LOG.info("sleepTime:"+sleepTime);
                if(null!=sysManageDic.GetByCode("thirty")) {
                    sleepTime = Integer.parseInt(sysManageDic.GetByCode("ten").getValue());
                    LOG.info("sleepTime:"+sleepTime);
                }
                LOG.info("sleepTime:"+sleepTime);
                LOG.info("每10分钟执行一次，开始睡眠十分钟");
                sleep(sleepTime  * 1000);
                LOG.info("开始执行10分钟一次的上传");
                ten();
                LOG.info("结束执行10分钟一次的上传");
            }catch(Exception e){
                e.printStackTrace();
                LOG.error(e.getMessage());
            }
        }
    }

    public void ten(){
        LOG.info("ten start:" + DateUtil.getDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        getnodeno();
        getcanversion();
        //gethost();
        getoilcanmap();
        if (ApplicationMain.NodeNo==null||"".equals(ApplicationMain.NodeNo)||ApplicationMain.canversion==null
                ||ApplicationMain.canversion.size()==0
                ||ApplicationMain.oilcanmap==null||ApplicationMain.oilcanmap.size()==0) return;
       //时点库存
       timeInvo();
        //整点库存
        zdtimeInvo();
       //库存报警
        InvoAlram();
        //设备报警
        EquipmentAlram();
        //脱销报警
        SaleOutAlarm();
        //枪出罐出比
        GaT();
        //进油损耗
        OilInContrast();
        //班结损益
        shift();
        //日结损益
        DailyLoss();
        //测漏
        measureLeak();
        //交易明细
        TradeAccount();
        //交易库存
        tradInvo();
        //ftp上传
        ftpupload();


        //出库单同步
        deliveybill();
        //卸油登记
        delivery();

        LOG.info("ten end"+ DateUtil.getDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
    }

    public void thirty(){
        getnodeno();
        getcanversion();
        //gethost();
        getoilcanmap();
        if (ApplicationMain.NodeNo==null||"".equals(ApplicationMain.NodeNo)||ApplicationMain.canversion==null
                ||ApplicationMain.canversion.size()==0||ApplicationMain.oilcanmap==null||ApplicationMain.oilcanmap.size()==0) return;
        //班报数据
        shiftData();
        //日平衡
        DayBalance();

        //容积表下载
        cuage();
        //报警参数下载
        iqalarm();
        //油站注册
        Reg();
        //静态罐存
        staticTankandGun();

        //字典同步
        Dict();
    }

    /*
    * 油库实发文档
    * */
    public void getYKSF(){
        try{
            LOG.info("yksf begin");
            if (acceptSevices!=null){
                acceptSevices=springFactory.getInstance().getBean(AcceptSevices.class);
            }
            List<AcceptanceDeliveryBills> bills= acceptSevices.getUncompletebills();
            for (AcceptanceDeliveryBills item:bills){
                acceptSevices.getsjfyl(ApplicationMain.Host,item.getDeliveryno());
            }
            LOG.info("yksf end");
        }catch (Exception e){
            LOG.error("yksf fail"+e.getMessage());
        }
    }

    /*
    * 时点库存
    * */
    public void timeInvo(){
        try {
            LOG.info("timeInvo begin");
            /*if (sys==null) {
                sys = springFactory.getInstance().getBean(synMonitor.class);
            }*/
            sys.synTimeInventory();
            LOG.info("timeInvo end");
        }
        catch (Exception ex)
        {
            LOG.error("timeInvo fail"+ex.getMessage());
        }
    }

    /**
     * 整点库存
     * */
    public void zdtimeInvo(){
        try
        {
            LOG.info("zdtimeInvo begin");
           /* if (sys==null) {
                sys = springFactory.getInstance().getBean(synMonitor.class);
            }*/
            sys=springFactory.getInstance().getBean(synMonitor.class);
            sys.synInventory();
            LOG.info("zdtimeInvo end");
        }
        catch (Exception ex)
        {
            LOG.error("zdtimeInvo fail"+ex.getMessage());
        }
    }

    /*
    * 交易库存
    * */
    public void tradInvo(){
        //todo
        try{
            LOG.info("tradInvo begin");
            synDailyRunning syn=springFactory.getInstance().getBean(synDailyRunning.class);
            syn.TradeInventoryLost();
            LOG.info("tradInvo end");
        }
        catch (Exception ex)
        {
            LOG.error("tradInvo failed" + ex.getMessage());
        }
    }

    /*
    * 库存预警
    * */
    public void InvoAlram(){
        //todo
        try{
            LOG.info("InvoAlram begin");
            synAlarm syn= springFactory.getInstance().getBean(synAlarm.class);
            syn.synInventory();
            LOG.info("InvoAlram end");
        }
        catch (Exception ex)
        {
            LOG.error("InvoAlram failed!" + ex.getMessage());
        }

    }

    /*
    * 设备故障报警
    * */
    public void EquipmentAlram(){
        //todo
        try{
            LOG.info("EquipmentAlram begin");
            synAlarm syn= springFactory.getInstance().getBean(synAlarm.class);
            syn.synEquipment();
            LOG.info("EquipmentAlram end");
        }
        catch (Exception ex)
        {
            LOG.info("EquipmentAlram failed"+ex.getMessage());
        }

    }

    public void Reg(){
        try{
            LOG.info("Reg begin");
            StationRegServices syn= springFactory.getInstance().getBean(StationRegServices.class);
            syn.synsys(ApplicationMain.Host);
            LOG.info("Reg end");
        }catch (Exception e){
            LOG.error(e.getMessage());
            LOG.error("Reg failed");
        }
    }

    /*
    * 脱销预警
    * */
    public void SaleOutAlarm(){
        //todo
        try{
            LOG.info("SaleOutAlarm begin");
            synAlarm syn= springFactory.getInstance().getBean(synAlarm.class);
            syn.synSaleOut();
            LOG.info("SaleOutAlarm end");
        }
        catch (Exception ex)
        {
            LOG.error("SaleOutAlarm failed"+ex.getMessage());
        }

    }

    /*
    * 枪出罐出对比
    * */
    public void GaT(){
        //todo
        try{
            LOG.info("GaT begin");
            synAlarm syn= springFactory.getInstance().getBean(synAlarm.class);
            syn.synGaTContrast();
            LOG.info("GaT end");
        }
        catch (Exception ex)
        {
            LOG.info("GaT failed"+ex.getMessage());
        }

    }


    /*
    * 进油损耗预警
    * */
    public void OilInContrast(){
        //todo
        try{
            LOG.info("OilInContrast begin");
            synAlarm syn= springFactory.getInstance().getBean(synAlarm.class);
            syn.synOilInContrast();
            LOG.info("OilInContrast end");
        }
        catch (Exception ex)
        {
            LOG.info("OilInContrast failed"+ex.getMessage());
        }

    }

    /*交接班损溢预警*/
    public void shift(){
        //todo
        try{
            LOG.info("shift begin");
            synAlarm syn= springFactory.getInstance().getBean(synAlarm.class);
            syn.synShiftLost();
            LOG.info("shift end");
        }
        catch (Exception ex)
        {
            LOG.error("shift failed" + ex.getMessage());
        }

    }

    /*日结损益预警*/
    public void DailyLoss(){
        try {
            LOG.info("DailyLoss begin");
            synAlarm syn = springFactory.getInstance().getBean(synAlarm.class);
            syn.synDailyLost();
            LOG.info("DailyLoss end");
        }catch (Exception ex){
            LOG.info("DailyLoss failed"+ex.getMessage());
        }
    }

   /* 测漏报告？？没有使用*/
   public void measureLeak(){
       try {
           LOG.info("measureLeak begin");
           synAlarm syn = springFactory.getInstance().getBean(synAlarm.class);
           syn.synMeasureLeak();
           LOG.info("measureLeak end");
       }
       catch (Exception ex){
           LOG.info("measureLeak failed"+ex.getMessage());
       }
   }

    /*
    * 加油交易明细
    * */
    public void TradeAccount(){
        //todo
        try
        {
            LOG.info("TradeAccount begin");
            synDailyRunning sd= springFactory.getInstance().getBean(synDailyRunning.class);
            sd.synTradeAccountLost();
            LOG.info("TradeAccount end");
        }
        catch (Exception ex)
        {
            LOG.info("TradeAccount failed"+ex.getMessage());
        }
    }


    /**
     * 班结数据
     * */
    public void shiftData(){
        try {
            LOG.info("shiftData begin");
            DailyRunning dailyRunning = springFactory.getInstance().getBean(DailyRunning.class);
            dailyRunning.AddClassKnotData(ApplicationMain.Host);
            LOG.info("shiftData end");
        }
        catch (Exception ex){
            LOG.error("shiftData failed"+ex.getMessage());
        }
    }


    /**
     * 日平衡
     * */
    public void DayBalance(){
        try {
            LOG.info("DayBalance begin");
            DailyRunning dailyRunning = springFactory.getInstance().getBean(DailyRunning.class);
            dailyRunning.DailyBalanceLost(ApplicationMain.Host);
            LOG.info("DayBalance end");
        }
        catch (Exception ex)
        {
            LOG.error("DayBalance failed"+ ex.getMessage());
        }

    }

    /**
     * 探棒油罐关系表
     * */
    public void synpatrel(){
        try {
            LOG.info("parT begin");
           /* synSysManage synm= springFactory.getInstance().getBean(synSysManage.class);*/
            synsysmanage.synpatrel();
            LOG.info("parT end");
        }
        catch (Exception ex)
        {
            LOG.error("parT failed"+ ex.getMessage());
        }
    }

    /**
     * 交易库存
     * */
  /*  public void SaleInvo(){
        try {
            LOG.info("交易库存开始");
            synDailyRunning sd = springFactory.getInstance().getBean(synDailyRunning.class);
            sd.TradeInventoryLost();
            LOG.info("交易库存结束");
        }
        catch (Exception ex)
        {}
    }*/

    /**
     * 油罐基础信息
     * */
    public void tank(){
        try
        {
            LOG.info(" tank begin");
            /*synSysManage syn=springFactory.getInstance().getBean(synSysManage.class);*/
            synsysmanage.syntankinfo();
            LOG.info("tank end");
        }
        catch (Exception ex)
        {
            LOG.error("tank failed" + ex.getMessage());
        }
    }

    /**
     *
     * */
    public void gun(){
        try
        {
            LOG.info("gun begin");
            /*synSysManage syn=springFactory.getInstance().getBean(synSysManage.class);*/
            synsysmanage.synoilgun();
            LOG.info("gun end");
        }
        catch (Exception ex)
        {
            LOG.error("gun failed" +ex.getMessage());
        }
    }

    public void mac(){
        try{
            LOG.info("mac being");
           /* synSysManage syn=springFactory.getInstance().getBean(synSysManage.class);*/
            synsysmanage.synoilmac();
            LOG.info("mac end ");
        }catch (Exception e){
            LOG.error("mac failed"+e.getMessage());
        }
    }

    public void  oiltype(){
        try{
            LOG.info("oiltype being");
            /*synSysManage syn=springFactory.getInstance().getBean(synSysManage.class);*/
            synsysmanage.synoiltype();
            LOG.info("oiltype end ");
        }catch (Exception e){
            LOG.error("oiltype failed" +e.getMessage());
        }
    }
    /**
     * 容积表获取
     * */
    public void cuage(){
        try{
            LOG.info("cuage begin");
           SysmanageService sysmanageService=springFactory.getInstance().getBean(SysmanageService.class);
            sysmanageService.GetCubgeByNodeNobackInt(ApplicationMain.Host, ApplicationMain.NodeNo);
            LOG.info("cuage end");
        }catch (Exception e){
            LOG.error("cuage failed"+e.getMessage());
        }
    }

    /**
     * 卸油登记
     * */
    public void delivery(){
        try
        {
            LOG.info("delivery begin");
            AcceptSevices syn=springFactory.getInstance().getBean(AcceptSevices.class);
            syn.sendOdreg(ApplicationMain.Host, ApplicationMain.NodeNo);
            LOG.info("delivery end");
        }
        catch (Exception ex)
        {
            LOG.error("delivery failed"+ex.getMessage());
        }
    }

    /**
     *下载出库单
     * */
    public void deliveybill(){
        try{
            LOG.info("deliverybill begin");
            AcceptSevices syn=springFactory.getInstance().getBean(AcceptSevices.class);
            syn.getbillsfromcenter(ApplicationMain.Host, ApplicationMain.NodeNo);
            LOG.info("deliverybill end");

        }catch (Exception e){
            LOG.error("deliverybill failed"+e.getMessage());
        }
    }

    /**
     * 静态罐存
     * */
    public void staticTankandGun(){
        try{
            LOG.info("staticTankandGun begin");
            DailyRunning dailyRunning=springFactory.getInstance().getBean(DailyRunning.class);
            dailyRunning.tankoilLost(ApplicationMain.Host);
            LOG.info("staticTankandGun end");
        }catch (Exception e){
            LOG.error("staticTankandGun failed"+e.getMessage());
        }
    }

    /**
     *液位仪设置
     * */
    public void iqalarm(){
        try{
            LOG.info("iqalarm begin");
            SysmanageService sysmanageService=springFactory.getInstance().getBean(SysmanageService.class);
            sysmanageService.GetAlarmPar(ApplicationMain.Host, ApplicationMain.NodeNo);
            LOG.info("iqalarm end");
        }catch (Exception e){
            LOG.error("iqalarm failed"+e.getMessage());
        }
    }


    /**
     * 罐枪实时状态
     * */
    public void transCanAndGunStatus(){
        getnodeno();
        getcanversion();
        //gethost();
        getoilcanmap();
        if (!ApplicationMain.IsOpenrt) return;
        if (ApplicationMain.NodeNo==null||"".equals(ApplicationMain.NodeNo)||ApplicationMain.canversion==null
                ||ApplicationMain.canversion.size()==0||ApplicationMain.Host==null||"".equals(ApplicationMain.Host)
                ||ApplicationMain.oilcanmap==null||ApplicationMain.oilcanmap.size()==0) return;
        try {
            if (ApplicationMain.canAndGunStatus == null||ApplicationMain.CC==null||ApplicationMain.count>=20) {
                LOG.info("count:"+ApplicationMain.count);
                ApplicationMain.count=0;
                ApplicationMain.canAndGunStatus = new CanAndGunStatus();
                ApplicationMain.canAndGunStatus.reg();
            }
            if (!"".equals(ApplicationMain.NodeNo) && ApplicationMain.canversion != null && ApplicationMain.oilcanmap != null) {
                LOG.info("send begin");
                ApplicationMain.canAndGunStatus.send();
            }
            LOG.info("TankandGunRealStatus begin");
        }
        catch (Exception ex)
        {
            LOG.error("TankandGunRealStatus:"+ex.getMessage());
        }
    }

    public void Dict(){
        try{
            LOG.info("dict begin");
            SysManageDic sysManageDic=springFactory.getInstance().getBean(SysManageDic.class);
            sysManageDic.synDicFromCenter(ApplicationMain.Host);
            LOG.info("dict end");
        }catch (Exception e){
            LOG.error("dict failed"+e.getMessage());
        }
    }

    /**
     * 实时库存
     * */
    public void ftpupload(){
        try {
            LOG.info("-------------FTP begin-------------");
            if (new Date().getDay()!=ApplicationMain.UpLoadDate.getDay()){
                String dstring= DateUtil.getDate();
                LOG.info(dstring);
                dstring=dstring+" 00:15:00";
                Date date= new Date();
                date.setHours(0);
                date.setMinutes(15);
                date.setSeconds(0);

                int max=82800000;
                int min=1;
                Random random = new Random();

                int s = random.nextInt(max)%(max-min+1) + min;
                LOG.info(s);
                ApplicationMain.UpLoadDate=new Date(date.getTime()+s);
            }
            if (new Date().getTime() > ApplicationMain.UpLoadDate.getTime()) {
                synMaclog sml = springFactory.getInstance().getBean(synMaclog.class);
                sml.synMacLogAuto();
            }
            LOG.info("-------------FTP end-------------");
            synSysManage sm=springFactory.getInstance().getBean(synSysManage.class);
            LOG.info("list begin");
            sm.synuplist();
            LOG.info("list end");
        }
        catch (Exception ex)
        {
            LOG.error("ftp:"+ex.getMessage());
        }
    }

    /**
     * nodeno
     * */
    public void getnodeno(){
        if (ApplicationMain.NodeNo==null||"".equals(ApplicationMain.NodeNo)){
        try {
            SysmanageService sysmanageService=springFactory.getInstance().getBean(SysmanageService.class);
            LOG.info("get nodeno begin");
            SysManageDepartment department=sysmanageService.getdeptinfo();
            ApplicationMain.NodeNo=department.getSinopecnodeno();
            LOG.info("get nodeno end");
        }catch (Exception e){
            LOG.error("get nodeno failed"+e.getMessage());
        }
        }
    }

    /**
     * host
     * */
    public void gethost(){
        if (ApplicationMain.Host==null||"".equals(ApplicationMain.Host)){
            ApplicationMain.Host=new action().getHost();
        }
    }
    /**
     * getoilcanmap
     * */
    public void getoilcanmap(){
        if (ApplicationMain.oilcanmap==null||ApplicationMain.oilcanmap.size()==0){
            //region load oilcan and oilno to map
            synSysManage sys=springFactory.getInstance().getBean(synSysManage.class);
            try{
                LOG.info("load can and oilno mapping begin");
                List<SysManageCanInfo> canInfos=sys.getCaninfos();
                if (ApplicationMain.oilcanmap==null)ApplicationMain.oilcanmap=new HashMap<String,Integer>();

                for (SysManageCanInfo item:canInfos){
                    ApplicationMain.oilcanmap.put(item.getOilcan().toString(),Integer.parseInt(item.getOilno()));
                }
                LOG.info("load can and oilno mapping end");
            }
            catch (Exception e){
                LOG.error("load can and oilno mapping fail"+e.getMessage());
            }
            //endregion
        }

    }
    /*
    *
    * */
    public void getcanversion(){
        if (ApplicationMain.canversion==null||ApplicationMain.canversion.size()==0){
            //region load can and version to map
            SysmanageService sysmanageService=springFactory.getInstance().getBean(SysmanageService.class);
            try{
                LOG.info("load can and version mapping begin");
                List<SysManageCubage> sysManageCubages=sysmanageService.selectCubageInused();
                if (ApplicationMain.canversion==null)ApplicationMain.canversion=new HashMap<String, String>();
                for (SysManageCubage item:sysManageCubages){
                    ApplicationMain.canversion.put(item.getOilcan().toString(),item.getVersion());
                    LOG.info("load can and version mapping end");
                }
            }catch (Exception e){
                LOG.error("load can and version failed");
                LOG.error(e.getMessage());
            }
            //endregion
        }
    }
}
