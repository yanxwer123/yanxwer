package com.kld.gsm.center.domain;


import java.util.List;

/**
@Created BY niyang
@Created Date 2015/11/27
@Description 同步基础信息表
 */
public class SysmanageSynMain {

    /**
    * 油机信息表
    * */
    private List<oss_sysmanage_OilMachineInfo> oilmacs;

    //油枪
    private List<oss_sysmanage_OilGunInfo> oilGunInfos;

    //单位信息
    private oss_sysmanage_department department;

    //机走油品编码表
    private List<oss_sysmanage_oilType> oilTypes;

    //油罐信息表
    private List<oss_sysmanage_TankInfo> tankInfos;

    public List<oss_sysmanage_OilMachineInfo> getOilmacs() {
        return oilmacs;
    }

    public void setOilmacs(List<oss_sysmanage_OilMachineInfo> oilmacs) {
        this.oilmacs = oilmacs;
    }

    public List<oss_sysmanage_OilGunInfo> getOilGunInfos() {
        return oilGunInfos;
    }

    public void setOilGunInfos(List<oss_sysmanage_OilGunInfo> oilGunInfos) {
        this.oilGunInfos = oilGunInfos;
    }

    public oss_sysmanage_department getDepartment() {
        return department;
    }

    public void setDepartment(oss_sysmanage_department department) {
        this.department = department;
    }

    public List<oss_sysmanage_oilType> getOilTypes() {
        return oilTypes;
    }

    public void setOilTypes(List<oss_sysmanage_oilType> oilTypes) {
        this.oilTypes = oilTypes;
    }

    public List<oss_sysmanage_TankInfo> getTankInfos() {
        return tankInfos;
    }

    public void setTankInfos(List<oss_sysmanage_TankInfo> tankInfos) {
        this.tankInfos = tankInfos;
    }
}
