package com.kld.gsm.coord.timertask;

import com.kld.gsm.ATG.dao.SysManageCanInfoDao;
import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.ATG.service.SysmanageService;
import com.kld.gsm.ATG.utils.action;
import com.kld.gsm.ATG.utils.httpClient;
import com.kld.gsm.ATG.utils.param;
import com.kld.gsm.ATGDevice.ATGManager;
import com.kld.gsm.ATGDevice.atg_stock_data_out_t;
import com.kld.gsm.ATGDevice.atg_timestock_data_in_t;
import com.kld.gsm.MacLog.GunStatusEnum;
import com.kld.gsm.MacLog.MacLogInfo;
import com.kld.gsm.MacLog.util.EhCacheHelper;
import com.kld.gsm.SysConfig;
import com.kld.gsm.coord.Context;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by ny on 2016/4/17.
 */
public class synCanAndGunInfo extends Thread {
    Logger logger = Logger.getLogger(synCanAndGunInfo.class);

    @Autowired
    private SysmanageService sysmanageService;
    public String nodeno;
    public Map<String,String> canversion;
    public Map<String,Integer> oilcanmap;
    public String gunpath;
    public String canpath;

    public  List tankNo;


    @Override
    public void run() {
        init();

        while (true){
            try
            {
                sleep(200);
                logger.info("200ms wakeup");
                boolean res=syncanandgun();
                logger.info("syncanandgun end："+res+","+new Date().toString());
                if (res){
                    //执行成功睡
                    try {
                        //间隔时间
                        sleep(TimeTaskPar.get("rtstockjg") * 1000);
                        logger.info("wake up");
                    } catch (InterruptedException e) {
                        logger.error("实时罐存间隔Inter:" + e);
                    }catch (Exception e){
                        logger.error("实时罐存间隔:" + e);
                    }
                }
            }catch (Exception e){
                logger.error("获取实时罐存异常："+new Date().toString()+e.getStackTrace());
            }
        }
    }

    void init(){
        //todo nodeno
        try {
            logger.info("get nodeno begin");
            if (sysmanageService==null){
                logger.info("sysmanageService is null");
                sysmanageService= Context.getInstance().getBean(SysmanageService.class);
            }
            SysManageDepartment department=sysmanageService.getdeptinfo();
            nodeno=department.getSinopecnodeno();
            logger.info("get nodeno end");
        }catch (Exception e){
            logger.error("get nodeno failed"+e.getMessage());
        }
        //todo canverison
        try{
            if (sysmanageService==null){
                logger.info("sysmanageService is null");
                sysmanageService= Context.getInstance().getBean(SysmanageService.class);
            }
            logger.info("load can and version mapping begin");
            List<SysManageCubage> sysManageCubages=sysmanageService.selectCubageInused();
            if (canversion==null)canversion=new HashMap<String, String>();
            for (SysManageCubage item:sysManageCubages){
                canversion.put(item.getOilcan().toString(),item.getVersion());
                logger.info("load can and version mapping end");
            }
        }catch (Exception e){
            logger.error("load can and version failed");
            logger.error(e.getMessage());
        }
        //todo oilcanmap
        try{
            if (sysmanageService==null){
                logger.info("sysmanageService is null");
                sysmanageService= Context.getInstance().getBean(SysmanageService.class);
            }
            logger.info("load can and oilno mapping begin");
            List<SysManageCanInfo> canInfos= sysmanageService.getCaninfos();
            oilcanmap=new HashMap<String,Integer>();
            for (SysManageCanInfo item:canInfos){
                oilcanmap.put(item.getOilcan().toString(),Integer.parseInt(item.getOilno()));
            }
            logger.info("load can and oilno mapping end");
        }
        catch (Exception e){
            logger.error("load can and oilno mapping fail"+e.getMessage());
        }
        //todo path
        action ac=new action();
        gunpath =ac.getUri(SysConfig.getCenterIP(),"resource.services.TI.addQSSZT");
        canpath=ac.getUri(SysConfig.getCenterIP(), "resource.services.TI.addGSSZT");

        SysManageCanInfoDao sysManageCanInfodao = Context.getInstance().getBean(SysManageCanInfoDao.class);
        List<SysManageCanInfo> oilCanInforList = sysManageCanInfodao.selectAll();
        logger.info("get all can" + oilCanInforList);
        tankNo = new ArrayList();
        for (SysManageCanInfo oilcan : oilCanInforList) {
            tankNo.add(oilcan.getOilcan());
        }
        logger.info("can no：" + tankNo);
    }

    void synguninfo() {
        List<GunInfo> gunInfos = new ArrayList<GunInfo>();
        try{
            Map macLogMap = EhCacheHelper.getAllMacLog();
            if (macLogMap.size()<1){
                return;
            }
            logger.info("macLogMap.size():" + macLogMap.size());
            List<MacLogInfo> macLogInfos = new ArrayList<MacLogInfo>();
            Iterator<Map.Entry> it = macLogMap.entrySet().iterator();
            /*List list = new ArrayList();*/
            logger.info("macLogMap.entrySet().size():" + macLogMap.entrySet().size());
            while (it.hasNext()) {
                MacLogInfo macLogInfo = new MacLogInfo();
                try {
                    Map.Entry entry = it.next();
                    Element element = (Element) entry.getValue();
                    macLogInfo = (MacLogInfo) element.getObjectValue();
                    logger.info("list add cache："+macLogInfo.toString());
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
                macLogInfos.add(macLogInfo);
            }

        for (int i = 0; i < macLogInfos.size(); i++) {
            // region获取罐号
            GunInfo gunInfo = new GunInfo();
            MacLogInfo map = macLogInfos.get(i);
            logger.info("map:"+map.toString());
            //枪map
            gunInfo.setOilGun((int)map.getGunNum());
            if (map.getGunStatus()!= null) {
                gunInfo.setOilGunStatus(map.GunStatus.value());
            }
            gunInfo.setGetTime(new Date());
            gunInfo.setPumpUp(map.PumpReadout == null ? "" : map.PumpReadout.toString());
            gunInfo.setNodeno(nodeno);
            gunInfos.add(gunInfo);
        }
    }catch( Exception e)
    {
        logger.error("同步枪状态:" + e.getMessage(),e);
    }

    try{
        if (gunInfos==null||gunInfos.size()<1){
            logger.info("枪数据为空");
            return;
        }
        logger.info("枪地址："+gunpath);
        Map<String, String> hm = new HashMap<String, String>();
        hm.put("NodeNo",nodeno);
        //发送站级数据
        httpClient client = new httpClient();
        client.request(gunpath, gunInfos, hm);
        gunInfos.clear();
        client=null;
    } catch(Exception e){
        gunInfos.clear();
        logger.error("同步枪状态："+gunpath+e.getMessage());
    }
    }

    void syncaninfo(){
        //实时库存
        logger.info("同步罐状态 into");
        List<CanInfo> canInfos = new ArrayList<CanInfo>();
        List<atg_stock_data_out_t> list = ATGManager.getStock(tankNo);
        logger.info(list.toString());
        try {
            for (int m = 0; m < list.size(); m++) {
                CanInfo canInfo = new CanInfo();
                atg_stock_data_out_t map = list.get(m);
                canInfo.setNodeno(nodeno);
                canInfo.setOilcan(map.uOilCanNo);
                canInfo.setOill(map.fOilCubage);
                canInfo.setStandardl(map.fOilStandCubage);
                canInfo.setStoretime(new Date());
                canInfo.setTemperature(map.fOilTemp);
                canInfo.setTemp1(map.fOilTemp1);
                canInfo.setTemp2(map.fOilTemp2);
                canInfo.setTemp3(map.fOilTemp3);
                canInfo.setTemp4(map.fOilTemp4);
                canInfo.setTemp5(map.fOilTemp5);
                canInfo.setDensitiesstandard(map.fStandDensity);
                canInfo.setDensities(map.fApparentDensity);
                canInfo.setHeighttotal(map.fTotalHeight);
                canInfo.setHeightwater(map.fWaterHeight);
                canInfo.setWaterl(map.fWaterBulk);
                if (!oilcanmap.isEmpty() && oilcanmap.get(map.uOilCanNo) != null) {
                    canInfo.setOilno(oilcanmap.get(map.uOilCanNo));
                }
                canInfo.setVolumeempty(map.fEmptyCubage);
                if (!canversion.isEmpty() && canversion.get(map.uOilCanNo) != null) {
                    canInfo.setVersion(canversion.get(map.uOilCanNo));
                }
                canInfos.add(canInfo);
            }
        }catch (Exception e){
            logger.error("同步罐状态异常"+e.getMessage());
        }
        try {
            //获取action地址
            Map<String, String> hm = new HashMap<String, String>();
            hm.put("NodeNo",nodeno);
            //发送站级数据
            httpClient client = new httpClient();
            client.request(canpath, canInfos, hm);
            canInfos.clear();
            client=null;
        } catch (Exception e) {

            logger.error("同步罐状态："+canpath + e.getMessage());
            canInfos.clear();
        }
        logger.info("TankRealStatus end");

    }


    public boolean syncanandgun(){
        Integer result=0;
        Callable<Integer> call = new Callable<Integer>() {
            public Integer call() throws Exception {
                try {
                    logger.info("同步罐枪："+new Date().toString());
                    syncaninfo();
                    synguninfo();
                    return 1;
                }catch (Exception e){
                    return 0;
                }
            }
        };
        final ExecutorService exec = Executors.newFixedThreadPool(1);
        Future<Integer> future = exec.submit(call);
        logger.info("submit call");
        try {
            result = future.get(1000 * 20, TimeUnit.MILLISECONDS);
            logger.info("同步罐枪 value from call is :" + result);
        } catch (TimeoutException ex) {
            logger.error("===============同步罐枪 task time out===============\n" + new Date().toString());
            future.cancel(true);//
            logger.info("同步罐枪超时取消线程：" + future.isCancelled());
        } catch (Exception e) {
            logger.error("===============获取同步罐枪异常==============\n" + e.getMessage());
            future.cancel(true);
            logger.info("同步罐枪异常取消线程：" + future.isCancelled());
        }
        exec.shutdownNow();
        logger.info("同步罐枪线程状态：" + future.isCancelled());
        if (result==1){
            return true;
        }else {
            return false;
        }

    }
}
