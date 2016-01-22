package com.kld.gsm.center.domain;

import java.util.List;


/*
Created BY niyang
Created Date 2015/11/22
*/
public class oss_sysmanageCubgeMain {

    private oss_sysmanage_cubage  cubage;

    private List<oss_sysmanage_cubageInfo> cubageInfos;

    public oss_sysmanage_cubage getCubage() {
        return cubage;
    }

    public void setCubage(oss_sysmanage_cubage cubage) {
        this.cubage = cubage;
    }

    public List<oss_sysmanage_cubageInfo> getCubageInfos() {
        return cubageInfos;
    }

    public void setCubageInfos(List<oss_sysmanage_cubageInfo> cubageInfos) {
        this.cubageInfos = cubageInfos;
    }
}
