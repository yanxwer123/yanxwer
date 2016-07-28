package com.kld.gsm.syntocenter.service.impl;

import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.syntocenter.service.synDailyRunning;
import com.kld.gsm.syntocenter.util.action;
import com.kld.gsm.syntocenter.util.httpClient;
import com.kld.gsm.syntocenter.util.param;
import com.kld.gsm.util.JsonMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.awt.datatransfer.DataTransferer;

import java.util.List;
import java.util.Map;

/*
Created BY niyang
Created Date 2015/11/19
*/
@SuppressWarnings("all")
@Service
public class synDailyRunningImpl implements synDailyRunning {
    private static final Logger LOG = Logger.getLogger("synDailyRunning");

    @Autowired
    private com.kld.gsm.ATG.dao.DailyTradeAccountDao dailyTradeAccountDao;
    @Autowired
    private com.kld.gsm.ATG.dao.DailyTradeInventoryDao dailyTradeInventoryDao;
    @Autowired
    private com.kld.gsm.ATG.dao.DailyOilDailyRecordDao dailyOilDailyRecordDao;
    @Autowired
    private com.kld.gsm.ATG.dao.DailyShiftStockDao dailyShiftStockDao;
    @Autowired
    private com.kld.gsm.ATG.dao.DailyStationShiftInfoDao dailyStationShiftInfoDao;
    @Autowired
    private com.kld.gsm.ATG.dao.DailyOpotCountDao dailyOpotCountDao;
    @Autowired
    private com.kld.gsm.ATG.dao.DailyPumpDigitShiftDao dailyPumpDigitShiftDao;
    @Autowired
    private com.kld.gsm.ATG.dao.DailyTankShiftDao dailyTankShiftDao;
    @Autowired
    private com.kld.gsm.ATG.dao.DailyOpoCountDao dailyOpoCountDao;
    @Autowired
    private com.kld.gsm.ATG.dao.SysManageDepartmentDao sysManageDepartmentDao;
    @Override
    public int synTradeAccountLost() {
        action ac=new action();
        String path=ac.getUri("resource.services.RCYX.AddTradeAccount");
        //获取站级数据
        List<DailyTradeAccount> DailyTradeAccounts= dailyTradeAccountDao.selectByTrans("0");
        if (DailyTradeAccounts==null||DailyTradeAccounts.size()==0){
            return 0;
        }
        List<SysManageDepartment> sysManageDepartments=sysManageDepartmentDao.selectfirst();
        Map<String,String> hm=new com.kld.gsm.ATG.utils.param().getparam();
        if (sysManageDepartments.size()>0){
            hm.put("NodeNo",sysManageDepartments.get(0).getSinopecnodeno());
        }
        //发送站级数据
        httpClient client=new httpClient();
        Result result;
        try {
            String js=client.request(path,DailyTradeAccounts,hm);
            LOG.info(js);
            result=new JsonMapper().fromJson(js,Result.class);
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e.getMessage());
            return 0;
        }
        if (result!=null&&result.isResult()){
            for (DailyTradeAccount item:DailyTradeAccounts){
                item.setTransFlag("1");
                dailyTradeAccountDao.updateByPrimaryKey(item);
            }
        }
        return 1;
    }

    @Override
    public int TradeInventoryLost() {
        action ac=new action();
        String path=ac.getUri("resource.services.RCYX.AddTradeInventory");
        //获取站级数据
        List<DailyTradeInventory> DailyTradeInventorys= dailyTradeInventoryDao.selectByTrans("0");
        if (DailyTradeInventorys==null||DailyTradeInventorys.size()==0){
            return 0;
        }
        List<SysManageDepartment> sysManageDepartments=sysManageDepartmentDao.selectfirst();
        Map<String,String> hm=new com.kld.gsm.ATG.utils.param().getparam();
        if (sysManageDepartments.size()>0){
            hm.put("NodeNo",sysManageDepartments.get(0).getSinopecnodeno());
        }
        //发送站级数据
        httpClient client=new httpClient();
        Result result;
        try {
            String js=client.request(path,DailyTradeInventorys,hm);
            LOG.info(js);
            result=new JsonMapper().fromJson(js,Result.class);
            LOG.info(result);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        if (result!=null&&result.isResult())
        {
            for (DailyTradeInventory item:DailyTradeInventorys){
                item.setTranstatus("1");
                dailyTradeInventoryDao.updateByPrimaryKey(item);
            }
        }

        return 1;
    }

    @Override
    public int AfterTradeInventoryLost() {
        action ac=new action();
        LOG.info("进入预期1");
        String path=ac.getUri("resource.services.RCYX.UpdateTradeInventory");
        //获取站级数据
        List<DailyTradeInventory> DailyTradeInventorys= dailyTradeInventoryDao.selectByTrans1("1");
        if (DailyTradeInventorys==null||DailyTradeInventorys.size()==0){
            LOG.info("进入预期111");

            return 0;
        }
        LOG.info("进入预期2");
        List<SysManageDepartment> sysManageDepartments=sysManageDepartmentDao.selectfirst();
        Map<String,String> hm=new com.kld.gsm.ATG.utils.param().getparam();
        if (sysManageDepartments.size()>0){
            hm.put("NodeNo",sysManageDepartments.get(0).getSinopecnodeno());
        }
        //发送站级数据
        httpClient client=new httpClient();
        Result result;
        try {
            String js=client.request(path,DailyTradeInventorys,hm);
            LOG.info(js);
            result=new JsonMapper().fromJson(js,Result.class);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;        }
        if (result!=null&&result.isResult())
        {
            for (DailyTradeInventory item:DailyTradeInventorys){
                item.setTranstatus("2");
                dailyTradeInventoryDao.updateByPrimaryKey(item);
            }
        }

        return 1;
    }


    @Override
    public int OilDailyRecordLost() {
        action ac=new action();
        String path=ac.getUri("resource.services.RCYX.AddOilDailyRecord");
        //获取站级数据
        List<DailyOilDailyRecord> DailyOilDailyRecords= dailyOilDailyRecordDao.selectByTrans("0");
        List<SysManageDepartment> sysManageDepartments=sysManageDepartmentDao.selectfirst();
        Map<String,String> hm=new com.kld.gsm.ATG.utils.param().getparam();
        if (sysManageDepartments.size()>0){
            hm.put("NodeNo",sysManageDepartments.get(0).getSinopecnodeno());
        }
        //发送站级数据
        httpClient client=new httpClient();
        Result result;
        try {
            String js=client.request(path,DailyOilDailyRecords,hm);
            LOG.info(js);
            result=new JsonMapper().fromJson(js,Result.class);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        if (result!=null&&result.isResult()){
            for (DailyOilDailyRecord item:DailyOilDailyRecords){
                item.setTranstatus("1");
                dailyOilDailyRecordDao.updateByPrimaryKey(item);
            }
        }
        return 1;
    }

    @Override
    public int ShiftStockLost() {
        action ac=new action();
        String path=ac.getUri("resource.services.RCYX.AddShiftStock");
        //获取站级数据
        List<DailyShiftStock> DailyShiftStocks= dailyShiftStockDao.selectByTrans("0");
        List<SysManageDepartment> sysManageDepartments=sysManageDepartmentDao.selectfirst();
        Map<String,String> hm=new com.kld.gsm.ATG.utils.param().getparam();
        if (sysManageDepartments.size()>0){
            hm.put("NodeNo",sysManageDepartments.get(0).getSinopecnodeno());
        }
        //发送站级数据
        httpClient client=new httpClient();
        Result result;
        try {
            String js=client.request(path,DailyShiftStocks,hm);
            LOG.info(js);
            result=new JsonMapper().fromJson(js,Result.class);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return 0;
        }
        if (result!=null&&result.isResult()){
            for (DailyShiftStock item:DailyShiftStocks){
                item.setTranflag("1");
                dailyShiftStockDao.updateByPrimaryKey(item);
            }
        }
        return 1;
    }


    public  int AddClassKnotData(){

        action ac=new action();
        String ClassKnotDataPath=ac.getUri("resource.services.RCYX.AddClassKnotData");
       /* String StationShiftInfoPath=ac.getUri("resource.services.RCYX.AddStationShiftInfo");
        String opotCountpath=ac.getUri("resource.services.RCYX.AddopotCount");
        String pumpdigitshiftPath=ac.getUri("resource.services.RCYX.AddpumpDigitShift");
        String tankshiftPath=ac.getUri("resource.services.RCYX.Addtankshift");
        String opoCountPath=ac.getUri("resource.services.RCYX.AddopoCount");*/
        //获取站级数据  油站班报表名oss_daily_stationshiftinfo
        List<DailyStationShiftInfo> DailyStationShiftInfos= dailyStationShiftInfoDao.selectByTrans("0");
        //获取站级数据 付油量分类统计表名oss_daily_opotcount
        List<DailyOpotCount> DailyOpotCounts= dailyOpotCountDao.selectByTrans("0");
        //泵码交接表名oss_daily_pumpdigitshift
        List<DailyPumpDigitShift> DailyPumpDigitShifts= dailyPumpDigitShiftDao.selectByTrans("0");
        //获取站级数据 油罐交接表oss_daily_tankshift  test成功
        List<DailyTankShift> DailyTankShifts= dailyTankShiftDao.selectByTrans("0");
        //获取站级数据 付油数量统计交接表oss_daily_opocount test成功
        List<DailyOpoCount> DailyOpoCounts= dailyOpoCountDao.selectByTrans("0");

        DailyMain dailyMain=new DailyMain();
        dailyMain.setOpoCountLost(DailyOpoCounts);
        dailyMain.setOpotCountLst(DailyOpotCounts);
        dailyMain.setPumpDigitShiftLost(DailyPumpDigitShifts);
        dailyMain.setStationShiftInfoLst(DailyStationShiftInfos);
        dailyMain.setTankShiftLost(DailyTankShifts);
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        Result result;
        try {
            String js=client.request(ClassKnotDataPath, dailyMain, hm);
            LOG.info(js);
            result=new JsonMapper().fromJson(js,Result.class);
        } catch (Exception e) {
           LOG.error(e.getMessage());
            return 0;
        }

        if (result!=null&&result.isResult()){
            for (DailyStationShiftInfo item:DailyStationShiftInfos){
                item.setTranstatus("1");
                dailyStationShiftInfoDao.updateByPrimaryKey(item);
            }
            for (DailyOpotCount item:DailyOpotCounts){
                item.setTranstatus("1");
                dailyOpotCountDao.updateByPrimaryKey(item);
            }
            for (DailyPumpDigitShift item:DailyPumpDigitShifts){
                item.setTranstatus("1");
                dailyPumpDigitShiftDao.updateByPrimaryKey(item);
            }
            for (DailyTankShift item:DailyTankShifts){
                item.setTranstatus("1");
                dailyTankShiftDao.updateByPrimaryKey(item);
            }
            for (DailyOpoCount item:DailyOpoCounts){
                item.setTranstatus("1");
                dailyOpoCountDao.updateByPrimaryKey(item);
            }
        }
        return 1;
    }
}
