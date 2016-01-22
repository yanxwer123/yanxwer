package com.kld.gsm.ATG.domain;


import java.util.List;

/**
@Created BY niyang
@Created Date 2015/11/27
@Description 同步基础信息表
 */
public class SysmanageSynMain  {

    /**
    * 油机信息表
    * */
    private List<SysManageOilMachineInfo> oilmacs;

    //油枪
    private List<SysManageOilGunInfo> oilGunInfos;

    //单位信息
    private SysManageDepartment department;

    //机走油品编码表
    private List<SysManageOilType> oilTypes;

    //油罐信息表
    private List<SysManageCanInfo> tankInfos;

    public List<SysManageOilMachineInfo> getOilmacs() {
        return oilmacs;
    }

    public void setOilmacs(List<SysManageOilMachineInfo> oilmacs) {
        this.oilmacs = oilmacs;
    }

    public List<SysManageOilGunInfo> getOilGunInfos() {
        return oilGunInfos;
    }

    public void setOilGunInfos(List<SysManageOilGunInfo> oilGunInfos) {
        this.oilGunInfos = oilGunInfos;
    }

    public SysManageDepartment getDepartment() {
        return department;
    }

    public void setDepartment(SysManageDepartment department) {
        this.department = department;
    }

    public List<SysManageOilType> getOilTypes() {
        return oilTypes;
    }

    public void setOilTypes(List<SysManageOilType> oilTypes) {
        this.oilTypes = oilTypes;
    }

    public List<SysManageCanInfo> getTankInfos() {
        return tankInfos;
    }

    public void setTankInfos(List<SysManageCanInfo> tankInfos) {
        this.tankInfos = tankInfos;
    }
}
