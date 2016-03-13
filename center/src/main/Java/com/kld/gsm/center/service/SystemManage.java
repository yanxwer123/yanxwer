package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.*;
import com.kld.gsm.center.domain.hn.HNRGB;
import com.kld.gsm.center.domain.hn.HNRGBMain;

import java.util.List;

/*
Created BY niyang
Created Date 2015/11/19
*/
public interface SystemManage {
    int addAlarmParameter(List<oss_sysmanage_AlarmParameter> ossSysmanageAlarmParameters);

    int addCubInfo(List<oss_sysmanage_cubageInfo> ossSysmanageCubageInfos);

    int addCub(List<oss_sysmanage_cubage> ossSysmanageCubages);

    int addDataUploadList(List<oss_sysmanage_DataUploadList> ossSysmanageDataUploadLists);

    int addDepet(List<oss_sysmanage_department> ossSysmanageDepartments);

    int addiquidInstrument(List<oss_sysmanage_iquidInstrument> ossSysmanageIquidInstruments);

    int addOilGuninfo(List<oss_sysmanage_OilGunInfo> ossSysmanageOilGunInfos);

    int addoilin (List<oss_sysmanage_oilin> ossSysmanageOilins);

    int addOilMachineInfo(List<oss_sysmanage_OilMachineInfo> ossSysmanageOilMachineInfos);

    int addoilType(List<oss_sysmanage_oilType> ossSysmanageOilTypes);

    int addPatRelation(List<oss_sysmanage_PaTRelation> ossSysmanagePaTRelations);

    int addpowerRecord(List<oss_sysmanage_powerRecord> ossSysmanagePowerRecords);

    int addprobebar(List<oss_sysmanage_probePar> ossSysmanageProbePars);

    int addTankInof(List<oss_sysmanage_TankInfo> ossSysmanageTankInfos);

    int addTimeSaleOut(List<oss_sysmanage_timeSaleOut> ossSysmanageTimeSaleOuts);

    int addCity(List<oss_sysmanage_City> ossSysmanageCities);

    int addArea(List<oss_sysmanage_Area> ossSysmanageAreas);

    int addStation(List<oss_sysmanage_Station> ossSysmanageStations);

    int addDepot(List<oss_sysmanage_Depot> ossSysmanageDepots);

    oss_sysmanage_Station getstationbylsgskcdd(String lsgc,String kcdd);

    int addRGBMainFromHN(List<oss_sysmanageCubgeMain> hnrgbMains);

    List<oss_sysmanage_cubage> getcubgesByNodenoAndStatus(String nodeno,Integer status);

    List<oss_sysmanage_cubageInfo> getcubgeInfosByNodenoAndVersion(String nodeno,String version);

    List<oss_sysmanage_cubageInfo> getcubgeInfosByNodenoAndVersionandcanno(String nodeno,String version,String canno);

    List<oss_sysmanage_AlarmParameter> getAlarmPars(String nodeno);

    List<oss_sysmanage_City> getAllCitys();

    List<oss_sysmanage_Area> getAreasByCity(String cno);

    List<oss_sysmanage_Station> getStationsByArea(String Ano);

    List<oss_sysmanage_DataUploadList> getUplistByStatus();

    /**
     * @decription 基础信息同步
     * */
    int synMain(SysmanageSynMain main,String nodeno);
    /**
     * @description 查询在用油品
     * */
    List<oss_sysmanage_oilType> selectuseoiltype();


    int UpdateUpload(oss_sysmanage_DataUploadList record);

    List<oss_sysmanage_department> selectbyid(List<StationStatus> stationStatuses);

    //String getOucode(oss_sysmanage_department department);

    /**
    * @description 根据nodeno，罐号，容积表version，查询容积表主记录
    * */
    oss_sysmanage_cubage selectCubage(String nodeno,String oilcan,String version);

    /**
     * 更新容积表
     * */
    int updateCubage(oss_sysmanage_cubage record);

}
