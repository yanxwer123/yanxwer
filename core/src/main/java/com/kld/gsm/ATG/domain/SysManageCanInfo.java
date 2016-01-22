package com.kld.gsm.ATG.domain;

import java.util.Date;

public class SysManageCanInfo {
    private Integer oilcan;

    private String oilcantype;

    private String outfactoryno;

    private String factoryname;

    private Date outfacorytime;

    @Override
    public String toString() {
        return "SysManageCanInfo{" +
                "oilcan=" + oilcan +
                ", oilcantype='" + oilcantype + '\'' +
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
                '}';
    }

    private Date installdate;

    private Integer useyears;

    private String oilno;

    private Double cubage;

    private String oilcanstatus;

    private Double lederl;

    private Double canreall;

    private String transtatus;

    public Integer getOilcan() {
        return oilcan;
    }

    public void setOilcan(Integer oilcan) {
        this.oilcan = oilcan;
    }

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
}