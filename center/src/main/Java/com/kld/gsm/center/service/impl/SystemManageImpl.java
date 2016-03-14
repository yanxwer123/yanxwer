package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.*;
import com.kld.gsm.center.domain.*;
import com.kld.gsm.center.domain.hn.HNRGB;
import com.kld.gsm.center.domain.hn.HNRGBMain;
import com.kld.gsm.center.service.SystemManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/*
Created BY niyang
Created Date 2015/11/19
*/
@Service
public class SystemManageImpl implements SystemManage {
    @Resource
    private oss_sysmanage_AlarmParameterMapper ossSysmanageAlarmParameterMapper;
    /*
    * 报警参数
    * */
    @Transactional(rollbackFor=Exception.class)
    public int addAlarmParameter(List<oss_sysmanage_AlarmParameter> ossSysmanageAlarmParameters) {
        for (oss_sysmanage_AlarmParameter item:ossSysmanageAlarmParameters){
            ossSysmanageAlarmParameterMapper.merge(item);
        }
        return 1;
    }

    @Resource
    private oss_sysmanage_cubageInfoMapper ossSysmanageCubageInfoMapper;
    @Transactional(rollbackFor=Exception.class)
    public int addCubInfo(List<oss_sysmanage_cubageInfo> ossSysmanageCubageInfos) {
        for (oss_sysmanage_cubageInfo item :ossSysmanageCubageInfos){
            ossSysmanageCubageInfoMapper.insert(item);
        }
        return 1;
    }
    @Resource
    private oss_sysmanage_cubageMapper ossSysmanageCubageMapper;

    @Transactional(rollbackFor=Exception.class)
    public int addCub(List<oss_sysmanage_cubage> ossSysmanageCubages) {
        for (oss_sysmanage_cubage item:ossSysmanageCubages){
            ossSysmanageCubageMapper.insert(item);
        }
        return 1;
    }

    @Resource
    private  oss_sysmanage_DataUploadListMapper ossSysmanageDataUploadListMapper;
    @Transactional(rollbackFor=Exception.class)
    public int addDataUploadList(List<oss_sysmanage_DataUploadList> ossSysmanageDataUploadLists) {
        for (oss_sysmanage_DataUploadList item:ossSysmanageDataUploadLists){
            ossSysmanageDataUploadListMapper.insert(item);
        }
        return 1;
    }

    @Resource
    private oss_sysmanage_departmentMapper ossSysmanageDepartmentMapper;

    @Transactional(rollbackFor=Exception.class)
    public int addDepet(List<oss_sysmanage_department> ossSysmanageDepartments) {
        for (oss_sysmanage_department item:ossSysmanageDepartments) {
            ossSysmanageDepartmentMapper.merge(item);
        }
        return 1;
    }

    @Resource
    private oss_sysmanage_iquidInstrumentMapper ossSysmanageIquidInstrumentMapper;
    @Transactional(rollbackFor=Exception.class)
    public int addiquidInstrument(List<oss_sysmanage_iquidInstrument> ossSysmanageIquidInstruments) {
        for (oss_sysmanage_iquidInstrument item:ossSysmanageIquidInstruments){
            ossSysmanageIquidInstrumentMapper.merge(item);
        }
        return 1;
    }
    @Resource
    private oss_sysmanage_OilGunInfoMapper ossSysmanageOilGunInfoMapper;
    @Transactional(rollbackFor=Exception.class)
    public int addOilGuninfo(List<oss_sysmanage_OilGunInfo> ossSysmanageOilGunInfos) {
       for (oss_sysmanage_OilGunInfo item: ossSysmanageOilGunInfos){
           ossSysmanageOilGunInfoMapper.merge(item);
       }
        return 1;
    }

    @Resource
    private oss_sysmanage_oilinMapper ossSysmanageOilinMapper;
    @Transactional(rollbackFor=Exception.class)
    public int addoilin(List<oss_sysmanage_oilin> ossSysmanageOilins) {
        for (oss_sysmanage_oilin item:ossSysmanageOilins){
            ossSysmanageOilinMapper.insert(item);
        }
        return 1;
    }

    @Resource
    private oss_sysmanage_OilMachineInfoMapper ossSysmanageOilMachineInfoMapper;
    @Transactional(rollbackFor=Exception.class)
    public int addOilMachineInfo(List<oss_sysmanage_OilMachineInfo> ossSysmanageOilMachineInfos) {
        for (oss_sysmanage_OilMachineInfo item:ossSysmanageOilMachineInfos){
            ossSysmanageOilMachineInfoMapper.merge(item);
        }
        return 1;
    }

    @Resource
    private oss_sysmanage_oilTypeMapper ossSysmanageOilTypeMapper;

    @Transactional(rollbackFor=Exception.class)
    public int addoilType(List<oss_sysmanage_oilType> ossSysmanageOilTypes) {
        for (oss_sysmanage_oilType item:ossSysmanageOilTypes){
            ossSysmanageOilTypeMapper.insert(item);
        }
        return 1;
    }

    @Resource
    private oss_sysmanage_PaTRelationMapper ossSysmanagePaTRelationMapper;
    @Transactional(rollbackFor=Exception.class)
    public int addPatRelation(List<oss_sysmanage_PaTRelation> ossSysmanagePaTRelations) {
        for (oss_sysmanage_PaTRelation item:ossSysmanagePaTRelations){
            ossSysmanagePaTRelationMapper.merge(item);
        }
        return 1;
    }

    @Resource
    private oss_sysmanage_powerRecordMapper ossSysmanagePowerRecordMapper;
    @Transactional(rollbackFor=Exception.class)
    public int addpowerRecord(List<oss_sysmanage_powerRecord> ossSysmanagePowerRecords) {
         for (oss_sysmanage_powerRecord item: ossSysmanagePowerRecords){
             ossSysmanagePowerRecordMapper.insert(item);
         }
        return 1;
    }

    @Resource
    private oss_sysmanage_probeParMapper    ossSysmanageProbeParMapper;
    @Transactional(rollbackFor=Exception.class)
    public int addprobebar(List<oss_sysmanage_probePar> ossSysmanageProbePars) {
        for (oss_sysmanage_probePar item: ossSysmanageProbePars){
            ossSysmanageProbeParMapper.insert(item);
        }
        return 1;
    }

    @Resource
    private oss_sysmanage_TankInfoMapper ossSysmanageTankInfoMapper;
    @Transactional(rollbackFor=Exception.class)
    public int addTankInof(List<oss_sysmanage_TankInfo> ossSysmanageTankInfos) {
        for (oss_sysmanage_TankInfo item:ossSysmanageTankInfos){
            ossSysmanageTankInfoMapper.merge(item);
        }
        return 1;
    }

    @Resource
    private oss_sysmanage_timeSaleOutMapper ossSysmanageTimeSaleOutMapper;
    @Transactional(rollbackFor=Exception.class)
    public int addTimeSaleOut(List<oss_sysmanage_timeSaleOut> ossSysmanageTimeSaleOuts) {
         for (oss_sysmanage_timeSaleOut item:ossSysmanageTimeSaleOuts){
             ossSysmanageTimeSaleOutMapper.insert(item);
         }
        return 1;
    }

    @Resource
    private oss_sysmanage_CityMapper ossSysmanageCityMapper;
    @Transactional(rollbackFor=Exception.class)
    public int addCity(List<oss_sysmanage_City> ossSysmanageCities) {
        for (oss_sysmanage_City item:ossSysmanageCities){
            ossSysmanageCityMapper.merge(item);
        }
        return 0;
    }

    @Resource
    private  oss_sysmanage_AreaMapper ossSysmanageAreaMapper;
    @Transactional(rollbackFor=Exception.class)
    public int addArea(List<oss_sysmanage_Area> ossSysmanageAreas) {
        for (oss_sysmanage_Area item:ossSysmanageAreas){
            ossSysmanageAreaMapper.merge(item);
        }
        return 0;
    }

    @Resource
    private  oss_sysmanage_StationMapper ossSysmanageStationMapper;
    @Transactional(rollbackFor=Exception.class)
    public int addStation(List<oss_sysmanage_Station> ossSysmanageStations) {
        for (oss_sysmanage_Station item:ossSysmanageStations){
            ossSysmanageStationMapper.merge(item);
        }
        return 0;
    }

    @Resource
    private oss_sysmanage_DepotMapper ossSysmanageDepotMapper;
    @Transactional(rollbackFor=Exception.class)
    public int addDepot(List<oss_sysmanage_Depot> ossSysmanageDepots) {
        for (oss_sysmanage_Depot item:ossSysmanageDepots){
            ossSysmanageDepotMapper.merge(item);
        }
        return 0;
    }

    @Transactional(rollbackFor=Exception.class)
    public oss_sysmanage_Station getstationbylsgskcdd(String lsgc, String kcdd) {
        return ossSysmanageStationMapper.selectByLsgcandKcdd(lsgc,kcdd);
    }

    @Transactional(rollbackFor=Exception.class)
    public int addRGBMainFromHN(List<oss_sysmanageCubgeMain> hnrgbMains) {
        //ossSysmanageCubageMapper
        //ossSysmanageCubageInfoMapper

        for (oss_sysmanageCubgeMain item:hnrgbMains){
            oss_sysmanage_cubage cubage=item.getCubage();
            // 更新历史数据的状态
            ossSysmanageCubageMapper.updateStatus(cubage.getNodeno(), cubage.getOilcan());
            // 新增数据
            ossSysmanageCubageMapper.insert(cubage);

            //插入明细表
            for (oss_sysmanage_cubageInfo info:item.getCubageInfos()){
                ossSysmanageCubageInfoMapper.insert(info);
            }
        }
        return 1;
    }

    @Transactional(rollbackFor=Exception.class)
    public List<oss_sysmanage_cubage> getcubgesByNodenoAndStatus(String nodeno, Integer status) {
       return ossSysmanageCubageMapper.selectByNodenoAndStatus(nodeno,status);
    }

    @Transactional(rollbackFor=Exception.class)
    public List<oss_sysmanage_cubageInfo> getcubgeInfosByNodenoAndVersion(String nodeno, String version) {
        return ossSysmanageCubageInfoMapper.selectByNodenoAndVersion(nodeno,version);
    }


    @Resource
    private oss_sysmanage_AlarmPlusMapper ossSysmanageAlarmPlusMapper;
    @Transactional(rollbackFor=Exception.class)
    public List<oss_sysmanage_AlarmParameter> getAlarmPars(String nodeno) {
        List<oss_sysmanage_AlarmParameter> sysmanage_alarmParameters=ossSysmanageAlarmParameterMapper.selectByNodeNo(nodeno);
        for (oss_sysmanage_AlarmParameter item:sysmanage_alarmParameters){
            oss_sysmanage_AlarmPlusKey key=new oss_sysmanage_AlarmPlusKey();
            key.setOilcanno(item.getOilcan().toString());
            key.setNodeno(item.getNodeno());
           oss_sysmanage_AlarmPlus plus= ossSysmanageAlarmPlusMapper.selectByPrimaryKey(key);
            if (plus!=null){
                item.setWaterhightalarm(plus.getWaterhightalarm());
                item.setStealoilalarm(plus.getStealoilalarm());
                item.setLeakageoilalarm(plus.getLeakageoilalarm());
                item.setLeakoilalarm(plus.getLeakoilalarm());
            }else {
                item.setWaterhightalarm(item.getWateralarm()+20);
                item.setStealoilalarm(300d);
                item.setLeakageoilalarm(0.8d);
                item.setLeakoilalarm(60d);
            }
        }
        for (oss_sysmanage_AlarmParameter item:sysmanage_alarmParameters){
            item.setTranstatus("1");
            ossSysmanageAlarmParameterMapper.merge(item);
        }
        return sysmanage_alarmParameters;
    }


    @Override
    public List<oss_sysmanage_City> getAllCitys() {
       return ossSysmanageCityMapper.getAll();
    }

    @Override
    public List<oss_sysmanage_Area> getAreasByCity(String cno) {
        return ossSysmanageAreaMapper.getAreasByCity(cno);
    }

    @Override
    public List<oss_sysmanage_Station> getStationsByArea(String Ano) {
        return ossSysmanageStationMapper.getStationsByArea(Ano);
    }

    /**
     * @param main
     * @decription 基础信息同步
     */
    @Transactional(rollbackFor = Exception.class)
    public int synMain(SysmanageSynMain main,String nodeno) {

        // dept
        main.getDepartment().setNodeno(nodeno);
        ossSysmanageDepartmentMapper.merge(main.getDepartment());

        // tankinfo
        for (oss_sysmanage_TankInfo item:main.getTankInfos()){
            item.setNodeno(nodeno);
            ossSysmanageTankInfoMapper.merge(item);
        }

        //guninfo
        for (oss_sysmanage_OilGunInfo item:main.getOilGunInfos()){
            item.setNodeno(nodeno);
            ossSysmanageOilGunInfoMapper.merge(item);
        }

        //oiltype
        /*for (oss_sysmanage_oilType item:main.getOilTypes()){
            item.setNodeno(nodeno);
            ossSysmanageOilTypeMapper.merge(item);
        }*/
        //macinfo
        for (oss_sysmanage_OilMachineInfo item:main.getOilmacs()){
            item.setNodeno(nodeno);
            ossSysmanageOilMachineInfoMapper.merge(item);
        }

        return 1;
    }

    /**
     * @description 查询在用油品
     */
    @Override
    public List<oss_sysmanage_oilType> selectuseoiltype() {
       return ossSysmanageOilTypeMapper.selectinuse();
    }


    @Override
    public List<oss_sysmanage_DataUploadList> getUplistByStatus() {

        return ossSysmanageDataUploadListMapper.selectbystatus("0");
    }

    @Override
    public int UpdateUpload(oss_sysmanage_DataUploadList record) {
        return ossSysmanageDataUploadListMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<oss_sysmanage_department> selectbyid(List<StationStatus> stationStatuses) {
        return ossSysmanageDepartmentMapper.selectById(stationStatuses);
    }

    @Override
    public List<oss_sysmanage_cubageInfo> getcubgeInfosByNodenoAndVersionandcanno(String nodeno, String version, String canno) {
        return ossSysmanageCubageInfoMapper.selectByNodenoAndVersionandcanno(nodeno,version,canno);
    }

    @Override
    public oss_sysmanage_cubage selectCubage(String nodeno, String oilcan, String version) {
        oss_sysmanage_cubageKey key=new oss_sysmanage_cubageKey();
        key.setNodeno(nodeno);
        key.setOilcan(oilcan);
        key.setVersion(version);
        return ossSysmanageCubageMapper.selectByPrimaryKey(key);
    }

    @Override
    public int updateCubage(oss_sysmanage_cubage record) {
      return ossSysmanageCubageMapper.updateByPrimaryKey(record);
    }
}
