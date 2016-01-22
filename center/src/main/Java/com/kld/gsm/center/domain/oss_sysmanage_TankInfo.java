package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_sysmanage_TankInfo extends oss_sysmanage_TankInfoKey {
    private String oilcantype;//油罐出场类型

    private String outfactoryno;//出厂编号

    private String factoryname;//厂商名称

    private Date outfacorytime;//出场时间

    private Date installdate;//安装日期

    private Integer useyears;//使用年限

    private String oilno;//油品类型

    private Double cubage;//油罐容积

    private String oilcanstatus;//油罐使用状态

    private Double lederl;//账面罐存

    private Double canreall;//实测罐存

    private String transtatus;//传输状态

    private String oucode;//油站编号

    public String getOilcantype() {
        return oilcantype;
    }

    public void setOilcantype(String oilcantype) {
        this.oilcantype = oilcantype == null ? null : oilcantype.trim();
    }

    public String getOutfactoryno() {
        return outfactoryno;
    }

    public void setOutfactoryno(String outfactoryno) {
        this.outfactoryno = outfactoryno == null ? null : outfactoryno.trim();
    }

    public String getFactoryname() {
        return factoryname;
    }

    public void setFactoryname(String factoryname) {
        this.factoryname = factoryname == null ? null : factoryname.trim();
    }

    public Date getOutfacorytime() {
        return outfacorytime;
    }

    public void setOutfacorytime(Date outfacorytime) {
        this.outfacorytime = outfacorytime;
    }

    public Date getInstalldate() {
        return installdate;
    }

    public void setInstalldate(Date installdate) {
        this.installdate = installdate;
    }

    public Integer getUseyears() {
        return useyears;
    }

    public void setUseyears(Integer useyears) {
        this.useyears = useyears;
    }

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno == null ? null : oilno.trim();
    }

    public Double getCubage() {
        return cubage;
    }

    public void setCubage(Double cubage) {
        this.cubage = cubage;
    }

    public String getOilcanstatus() {
        return oilcanstatus;
    }

    public void setOilcanstatus(String oilcanstatus) {
        this.oilcanstatus = oilcanstatus == null ? null : oilcanstatus.trim();
    }

    public Double getLederl() {
        return lederl;
    }

    public void setLederl(Double lederl) {
        this.lederl = lederl;
    }

    public Double getCanreall() {
        return canreall;
    }

    public void setCanreall(Double canreall) {
        this.canreall = canreall;
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

    @Override
    public String toString() {
        return "oss_sysmanage_TankInfo{" +
                "oilcantype='" + oilcantype + '\'' +
                ", outfactoryno='" + outfactoryno + '\'' +
                ", factoryname='" + factoryname + '\'' +
                ", outfacorytime=" + outfacorytime +
                ", installdate=" + installdate +
                ", useyears=" + useyears +
                ", oilno='" + oilno + '\'' +
                ", cubage=" + cubage +
                ", oilcanstatus='" + oilcanstatus + '\'' +
                ", lederl=" + lederl +
                ", canreall=" + canreall +
                ", transtatus='" + transtatus + '\'' +
                ", oucode='" + oucode + '\'' +
                '}';
    }
}