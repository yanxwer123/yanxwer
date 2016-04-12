package com.kld.gsm.ATG.service.imp;

import com.kld.gsm.ATG.dao.DailyStaticOilCanInventoryDao;
import com.kld.gsm.ATG.dao.DailyStaticOilGunInverntoryDao;
import com.kld.gsm.ATG.dao.MonitorOilgunMapper;
import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.ATG.service.DailyRunning;
import com.kld.gsm.ATG.utils.action;
import com.kld.gsm.ATG.utils.httpClient;
import com.kld.gsm.ATG.utils.param;
import com.kld.gsm.ATGDevice.atg_stock_data_out_t;
import com.kld.gsm.MacLog.MacLogInfo;
import com.kld.gsm.util.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * Created by fangzhun on 2015/11/21.
 */
@Service
@SuppressWarnings("all")
public class DailyRunningImpl implements  DailyRunning {
    private static final Logger log = LoggerFactory.getLogger(DailyRunningImpl.class);
    @Autowired
    private com.kld.gsm.ATG.dao.DailyOpotCountDao dailyOpotCountDao;
    @Autowired
    private com.kld.gsm.ATG.dao.DailyOpoCountDao dailyOpoCountDao;
    @Autowired
    private com.kld.gsm.ATG.dao.DailyOpotDailyStatementDao dailyOpotDailyStatementDao;
    @Autowired
    private com.kld.gsm.ATG.dao.DailyOilTankRegisterDao dailyOilTankRegisterDao;
    @Autowired
    private com.kld.gsm.ATG.dao.DailyDailyBalanceDao dailyDailyBalanceDao;
    @Autowired
    private com.kld.gsm.ATG.dao.DailyTankShiftDao dailyTankShiftDao;
    @Autowired
    private com.kld.gsm.ATG.dao.DailyStationShiftInfoDao dailyStationShiftInfoDao;
    @Autowired
    private com.kld.gsm.ATG.dao.DailyPumpDigitShiftDao dailyPumpDigitShiftDao;
    @Autowired
    private com.kld.gsm.ATG.dao.DailyOilDayBillDao dailyOilDayBillDao;
    @Autowired
    private com.kld.gsm.ATG.dao.MonitorTankOilMapper monitorTankOilMapper;
    @Autowired
    private MonitorOilgunMapper monitorOilgunMapper;
    @Autowired
    private com.kld.gsm.ATG.dao.SysManageDepartmentDao sysManageDepartmentDao;

    public int synOpotCountLost(String host) {
        action ac=new action();
        String path=ac.getUri(host,"resource.services.RCYX.AddopotCount");
        //获取站级数据
        List<DailyOpotCount> DailyOpotCounts= dailyOpotCountDao.selectByTrans("0");
        List<SysManageDepartment> sysManageDepartments=sysManageDepartmentDao.selectfirst();
        Map<String,String> hm=new param().getparam();
        if (sysManageDepartments.size()>0){
            hm.put("NodeNo",sysManageDepartments.get(0).getSinopecnodeno());
        }
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,DailyOpotCounts,hm);
            for (DailyOpotCount item:DailyOpotCounts){
                item.setTranstatus("1");
                dailyOpotCountDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    public int opoCountLost(String host) {
        action ac=new action();
        String path=ac.getUri(host,"resource.services.RCYX.AddopoCount");
        //获取站级数据
        List<DailyOpoCount> DailyOpoCounts= dailyOpoCountDao.selectByTrans("0");
        List<SysManageDepartment> sysManageDepartments=sysManageDepartmentDao.selectfirst();
        Map<String,String> hm=new param().getparam();
        if (sysManageDepartments.size()>0){
            hm.put("NodeNo",sysManageDepartments.get(0).getSinopecnodeno());
        }
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,DailyOpoCounts,hm);
            for (DailyOpoCount item:DailyOpoCounts){
                item.setTranstatus("1");
                dailyOpoCountDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    public int opotDailyStatementLost(String host) {
        action ac=new action();
        String path=ac.getUri(host,"resource.services.RCYX.AddopotDailyStatement");
        //获取站级数据
        List<DailyOpotDailyStatement> DailyOpotDailyStatements= dailyOpotDailyStatementDao.selectByTrans("0");
        List<SysManageDepartment> sysManageDepartments=sysManageDepartmentDao.selectfirst();
        Map<String,String> hm=new param().getparam();
        if (sysManageDepartments.size()>0){
            hm.put("NodeNo",sysManageDepartments.get(0).getSinopecnodeno());
        }
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,DailyOpotDailyStatements,hm);
            for (DailyOpotDailyStatement item:DailyOpotDailyStatements){
                item.setTranstatus("1");
                dailyOpotDailyStatementDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    public int OilTankRegisterLost(String host) {
        action ac=new action();
        String path=ac.getUri(host,"resource.services.RCYX.AddOilTankRegister");
        //获取站级数据
        List<DailyOilTankRegister> DailyOilTankRegisters= dailyOilTankRegisterDao.selectByTrans("0");
        List<SysManageDepartment> sysManageDepartments=sysManageDepartmentDao.selectfirst();
        Map<String,String> hm=new param().getparam();
        if (sysManageDepartments.size()>0){
            hm.put("NodeNo",sysManageDepartments.get(0).getSinopecnodeno());
        }
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,DailyOilTankRegisters,hm);
            for (DailyOilTankRegister item:DailyOilTankRegisters){
                item.setTranstatus("1");
                dailyOilTankRegisterDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    public int DailyBalanceLost(String host) {
        log.info(host);
        action ac=new action();
        String path=ac.getUri(host,"resource.services.RCYX.AddDailyBalance");
        //获取站级数据
        List<DailyDailyBalance> DailyDailyBalances= dailyDailyBalanceDao.selectByTrans("0");
        if (DailyDailyBalances!=null) {
            log.info("DailyDailyBalances:" + DailyDailyBalances.size());
            log.info(DailyDailyBalances.toString());
        }
        List<SysManageDepartment> sysManageDepartments=sysManageDepartmentDao.selectfirst();
        Map<String,String> hm=new param().getparam();
        if (sysManageDepartments.size()>0){
            hm.put("NodeNo",sysManageDepartments.get(0).getSinopecnodeno());
        }
        //发送站级数据
        httpClient client=new httpClient();
        Result result=new Result();
        try {
           String js= client.request(path,DailyDailyBalances,hm);
            result=new JsonMapper().fromJson(js,Result.class);
            if (result!=null&&result.isResult()) {
                for (DailyDailyBalance item : DailyDailyBalances) {
                    item.setTranstatus("1");
                    dailyDailyBalanceDao.updateByPrimaryKey(item);
                }
            }
        } catch (Exception e) {
            log.error("DailyBalanceLost:" + e.getMessage(),e);
            return 0;
        }
        return 1;
    }

    @Override
    public int StationShiftInfoLost(String host) {
        action ac=new action();
        String path=ac.getUri(host,"resource.services.RCYX.AddStationShiftInfo");
        //获取站级数据
        List<DailyStationShiftInfo> DailyStationShiftInfos= dailyStationShiftInfoDao.selectByTrans("0");
        List<SysManageDepartment> sysManageDepartments=sysManageDepartmentDao.selectfirst();
        Map<String,String> hm=new param().getparam();
        if (sysManageDepartments.size()>0){
            hm.put("NodeNo",sysManageDepartments.get(0).getSinopecnodeno());
        }
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,DailyStationShiftInfos,hm);
            for (DailyStationShiftInfo item:DailyStationShiftInfos){
                item.setTranstatus("1");
                dailyStationShiftInfoDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    public int tankshiftLost(String host) {
        action ac=new action();
        String path=ac.getUri(host,"resource.services.RCYX.Addtankshift");
        //获取站级数据
        List<DailyTankShift> DailyTankShifts= dailyTankShiftDao.selectByTrans("0");
        List<SysManageDepartment> sysManageDepartments=sysManageDepartmentDao.selectfirst();
        Map<String,String> hm=new param().getparam();
        if (sysManageDepartments.size()>0){
            hm.put("NodeNo",sysManageDepartments.get(0).getSinopecnodeno());
        }
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,DailyTankShifts,hm);
            for (DailyTankShift item:DailyTankShifts){
                item.setTranstatus("1");
                dailyTankShiftDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    public int pumpDigitShiftLost(String host) {
        action ac=new action();
        String path=ac.getUri(host,"resource.services.RCYX.AddpumpDigitShift");
        //获取站级数据
        List<DailyPumpDigitShift> DailyPumpDigitShifts= dailyPumpDigitShiftDao.selectByTrans("0");
        List<SysManageDepartment> sysManageDepartments=sysManageDepartmentDao.selectfirst();
        Map<String,String> hm=new param().getparam();
        if (sysManageDepartments.size()>0){
            hm.put("NodeNo",sysManageDepartments.get(0).getSinopecnodeno());
        }
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,DailyPumpDigitShifts,hm);
            for (DailyPumpDigitShift item:DailyPumpDigitShifts){
                item.setTranstatus("1");
                dailyPumpDigitShiftDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    public int oildaybillLost(String host) {
        action ac=new action();
        String path=ac.getUri(host,"resource.services.RCYX.Addoildaybill");
        //获取站级数据
        List<DailyOilDayBill> DailyOilDayBills= dailyOilDayBillDao.selectByTrans("0");
        List<SysManageDepartment> sysManageDepartments=sysManageDepartmentDao.selectfirst();
        Map<String,String> hm=new param().getparam();
        if (sysManageDepartments.size()>0){
            hm.put("NodeNo",sysManageDepartments.get(0).getSinopecnodeno());
        }
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,DailyOilDayBills,hm);
            for (DailyOilDayBill item:DailyOilDayBills){
                item.setTranstatus("1");
                dailyOilDayBillDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Autowired
    private DailyStaticOilCanInventoryDao dailyStaticOilCanInventoryDao;
    @Autowired
    private DailyStaticOilGunInverntoryDao dailyStaticOilGunInverntoryDao;
    @Override
    public int tankoilLost(String host) {
        action ac=new action();
        String path=ac.getUri(host, "resource.services.RCYX.AddTankoil");
        List<JTGC> Tankoilguns=new ArrayList<JTGC>();
        List<DailyStaticOilCanInventory> MonitorTankOils=dailyStaticOilCanInventoryDao.selectBytrans("0");
        for (DailyStaticOilCanInventory item:MonitorTankOils){
            JTGC jtgc=new JTGC();
            jtgc.setTankoil(item);
            List<DailyStaticOilGunInverntory> guninfos=dailyStaticOilGunInverntoryDao.selectByid(item.getId());
            jtgc.setOilgunLst(guninfos);
            Tankoilguns.add(jtgc);
        }
       // List<Tankoilgun> Tankoilguns=new ArrayList<Tankoilgun>();
        //获取站级数据
       /* List<MonitorTankOil> MonitorTankOils= monitorTankOilMapper.selectByTrans("0");
        for(MonitorTankOil item:MonitorTankOils)
        {
            Tankoilgun tankoilgun=new Tankoilgun();
            tankoilgun.setMonitorTankOil(item);
            List<MonitorOilgun> MonitorOilguns= monitorOilgunMapper.selectByID(item.getId());
            tankoilgun.setMonitorOilgun(MonitorOilguns);
            Tankoilguns.add(tankoilgun);
        }*/
        List<SysManageDepartment> sysManageDepartments=sysManageDepartmentDao.selectfirst();
        Map<String,String> hm=new param().getparam();
        if (sysManageDepartments.size()>0){
            hm.put("NodeNo",sysManageDepartments.get(0).getSinopecnodeno());
        }
        //发送站级数据
        httpClient client=new httpClient();
        try {
            String js=client.request(path,Tankoilguns,hm);
            for (JTGC item:Tankoilguns){
                item.getTankoil().setTranstatus("1");
                dailyStaticOilCanInventoryDao.updateByPrimaryKey(item.getTankoil());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void AddClassKnotData(String host) {
        action ac=new action();
        String ClassKnotDataPath=ac.getUri(host,"resource.services.RCYX.AddClassKnotData");
        //获取站级数据  油站班报表名oss_daily_stationshiftinfo
        List<DailyStationShiftInfo> DailyStationShiftInfos= dailyStationShiftInfoDao.selectByTrans("0");
        log.info("StationInfo数量："+DailyStationShiftInfos.size());
        //获取站级数据 付油量分类统计表名oss_daily_opotcount
        List<DailyOpotCount> DailyOpotCounts= dailyOpotCountDao.selectByTrans("0");
        //泵码交接表名oss_daily_pumpdigitshift
        List<DailyPumpDigitShift> DailyPumpDigitShifts= dailyPumpDigitShiftDao.selectByTrans("0");
        //获取站级数据 油罐交接表oss_daily_tankshift test成功
        List<DailyTankShift> DailyTankShifts= dailyTankShiftDao.selectByTrans("0");
        //获取站级数据 付油数量统计交接表oss_daily_opocount  test成功
        List<DailyOpoCount> DailyOpoCounts= dailyOpoCountDao.selectByTrans("0");
        List<SysManageDepartment> sysManageDepartments=sysManageDepartmentDao.selectfirst();
        DailyMain dailyMain=new DailyMain();
        dailyMain.setOpoCountLost(DailyOpoCounts);
        dailyMain.setOpotCountLst(DailyOpotCounts);
        dailyMain.setPumpDigitShiftLost(DailyPumpDigitShifts);
        dailyMain.setStationShiftInfoLst(DailyStationShiftInfos);
        dailyMain.setTankShiftLost(DailyTankShifts);
        Map<String,String> hm=new param().getparam();
        if (sysManageDepartments.size()>0){
            hm.put("NodeNo",sysManageDepartments.get(0).getSinopecnodeno());
        }
       // hm.put("NodeNo","124");
        //发送站级数据
        httpClient client=new httpClient();
        Result result=new Result();
        try {
            String sRet=client.request(ClassKnotDataPath, dailyMain, hm);
            log.info(sRet);
            result=new JsonMapper().fromJson(sRet,Result.class);
            if (result!=null&&result.isResult()) {
                for (DailyStationShiftInfo item : DailyStationShiftInfos) {
                    item.setTranstatus("1");
                    dailyStationShiftInfoDao.updateByPrimaryKey(item);
                }

                for (DailyOpotCount item : DailyOpotCounts) {
                    item.setTranstatus("1");
                    dailyOpotCountDao.updateByPrimaryKey(item);
                }

                for (DailyPumpDigitShift item : DailyPumpDigitShifts) {
                    item.setTranstatus("1");
                    dailyPumpDigitShiftDao.updateByPrimaryKey(item);
                }

                for (DailyTankShift item : DailyTankShifts) {
                    item.setTranstatus("1");
                    dailyTankShiftDao.updateByPrimaryKey(item);
                }

                for (DailyOpoCount item : DailyOpoCounts) {
                    item.setTranstatus("1");
                    dailyOpoCountDao.updateByPrimaryKey(item);
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage(),e);
          //  return 0;
        }
       // return 1;
    }

    //MacLogInfo(油枪实时状态对象)
    @Override
    public int addMacLogInfo(String host,List<MacLogInfo> MacLogInfoLst) {
        action ac=new action();
        String path=ac.getUri(host, "resource.services.TI.addQSSZT");
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,MacLogInfoLst,hm);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    //MonitorTimeInventory(油罐实时状态对象)
    @Override
    public  int addMonitorTimeInventoryInfo(String host,List<atg_stock_data_out_t>  stockdataLst){
        action ac=new action();
        String path=ac.getUri(host, "resource.services.TI.addGSSZT");
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,stockdataLst,hm);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
}
