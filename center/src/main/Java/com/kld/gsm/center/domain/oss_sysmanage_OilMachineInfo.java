package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_sysmanage_OilMachineInfo extends oss_sysmanage_OilMachineInfoKey {
    private String macmodel;

    private String outfactoryno;

    private String factoryname;

    private Date outfactorytime;

    private Date installdate;

    private Integer useyears;

    private Integer oilgunnum;

    private Integer oilunitnum;

    private String oilmachinestatus;

    private String mactype;

    private String transtatus;

    private String oucode;

    public String getMacmodel() {
        return macmodel;
    }

    public void setMacmodel(String macmodel) {
        this.macmodel = macmodel == null ? null : macmodel.trim();
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

    public Date getOutfactorytime() {
        return outfactorytime;
    }

    public void setOutfactorytime(Date outfactorytime) {
        this.outfactorytime = outfactorytime;
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

    public Integer getOilgunnum() {
        return oilgunnum;
    }

    public void setOilgunnum(Integer oilgunnum) {
        this.oilgunnum = oilgunnum;
    }

    public Integer getOilunitnum() {
        return oilunitnum;
    }

    public void setOilunitnum(Integer oilunitnum) {
        this.oilunitnum = oilunitnum;
    }

    public String getOilmachinestatus() {
        return oilmachinestatus;
    }

    public void setOilmachinestatus(String oilmachinestatus) {
        this.oilmachinestatus = oilmachinestatus == null ? null : oilmachinestatus.trim();
    }

    public String getMactype() {
        return mactype;
    }

    public void setMactype(String mactype) {
        this.mactype = mactype == null ? null : mactype.trim();
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