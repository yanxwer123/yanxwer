package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_sysmanage_PaTRelation extends oss_sysmanage_PaTRelationKey {
    private String oilno;

    private Date lastadjusttime;

    private String transtatus;

    private String oucode;

    private String dreviceModel;//设备型号（新增）
    private Integer uProbePort;//探棒端口（新增）
    private String strOilType;//油品类型（新增）
    private String strOilName;//油品名称（新增）

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

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno == null ? null : oilno.trim();
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
        this.transtatus = transtatus == null ? null : transtatus.trim();
    }

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode == null ? null : oucode.trim();
    }
}