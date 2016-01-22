package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_sysmanage_iquidInstrument extends oss_sysmanage_iquidInstrumentKey {
    private String factory;

    private String mactype;

    private String worktype;

    private String commtype;

    private String ipaddress;

    private String ipport;

    private String serialport;

    private String baudrate1;

    private String baudrate2;

    private String stopsite;

    private String checksite;

    private String datasite;

    private Date lastadjusttime;

    private String transtatus;

    private Date createtime;

    private String oucode;

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory == null ? null : factory.trim();
    }

    public String getMactype() {
        return mactype;
    }

    public void setMactype(String mactype) {
        this.mactype = mactype == null ? null : mactype.trim();
    }

    public String getWorktype() {
        return worktype;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype == null ? null : worktype.trim();
    }

    public String getCommtype() {
        return commtype;
    }

    public void setCommtype(String commtype) {
        this.commtype = commtype == null ? null : commtype.trim();
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress == null ? null : ipaddress.trim();
    }

    public String getIpport() {
        return ipport;
    }

    public void setIpport(String ipport) {
        this.ipport = ipport == null ? null : ipport.trim();
    }

    public String getSerialport() {
        return serialport;
    }

    public void setSerialport(String serialport) {
        this.serialport = serialport == null ? null : serialport.trim();
    }

    public String getBaudrate1() {
        return baudrate1;
    }

    public void setBaudrate1(String baudrate1) {
        this.baudrate1 = baudrate1 == null ? null : baudrate1.trim();
    }

    public String getBaudrate2() {
        return baudrate2;
    }

    public void setBaudrate2(String baudrate2) {
        this.baudrate2 = baudrate2 == null ? null : baudrate2.trim();
    }

    public String getStopsite() {
        return stopsite;
    }

    public void setStopsite(String stopsite) {
        this.stopsite = stopsite == null ? null : stopsite.trim();
    }

    public String getChecksite() {
        return checksite;
    }

    public void setChecksite(String checksite) {
        this.checksite = checksite == null ? null : checksite.trim();
    }

    public String getDatasite() {
        return datasite;
    }

    public void setDatasite(String datasite) {
        this.datasite = datasite == null ? null : datasite.trim();
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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode == null ? null : oucode.trim();
    }
}