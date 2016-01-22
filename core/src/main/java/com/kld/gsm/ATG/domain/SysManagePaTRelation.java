package com.kld.gsm.ATG.domain;

import java.util.Date;

public class SysManagePaTRelation extends SysManagePaTRelationKey {
    private String oilno; //油品编码
    private String dreviceModel;//设备型号（新增）
    private Integer uProbePort;//探棒端口（新增）
    private String strOilType;//油品类型（新增）
    private String strOilName;//油品名称（新增）
    private Date lastadjusttime;

    private String transtatus;

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno;
    }

    public String getDreviceModel() {
        return dreviceModel;
    }

    public void setDreviceModel(String dreviceModel) {
        this.dreviceModel = dreviceModel;
    }

    public Integer getuProbePort() {
        return uProbePort;
    }

    public void setuProbePort(Integer uProbePort) {
        this.uProbePort = uProbePort;
    }

    public String getStrOilType() {
        return strOilType;
    }

    public void setStrOilType(String strOilType) {
        this.strOilType = strOilType;
    }


    public String getStrOilName() {
        return strOilName;
    }

    public void setStrOilName(String strOilName) {
        this.strOilName = strOilName;
    }

    public Date getLastadjusttime() {
        return lastadjusttime;
    }

    public void setLastadjusttime(Date lastadjusttime) {
        this.lastadjusttime = lastadjusttime;
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus;
    }

    @Override
    public String toString() {
        return "SysManagePaTRelation{" +
                "oilno='" + oilno + '\'' +
                ", dreviceModel='" + dreviceModel + '\'' +
                ", uProbePort=" + uProbePort +
                ", strOilType='" + strOilType + '\'' +
                ", strOilName='" + strOilName + '\'' +
                ", lastadjusttime=" + lastadjusttime +
                ", transtatus='" + transtatus + '\'' +
                '}';
    }
}