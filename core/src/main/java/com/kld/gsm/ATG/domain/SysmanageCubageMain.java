package com.kld.gsm.ATG.domain;


import java.util.List;

/*
Created BY niyang
Created Date 2015/11/22
*/
public class SysmanageCubageMain {
    private SysManageCubage cubage;

    private List<SysManageCubageInfo> cubageInfos;

    public SysManageCubage getCubage() {
        return cubage;
    }

    public void setCubage(SysManageCubage cubage) {
        this.cubage = cubage;
    }

    public List<SysManageCubageInfo> getCubageInfos() {
        return cubageInfos;
    }

    public void setCubageInfos(List<SysManageCubageInfo> cubageInfos) {
        this.cubageInfos = cubageInfos;
    }
}
