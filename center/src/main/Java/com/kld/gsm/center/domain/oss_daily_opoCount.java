package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_daily_opoCount extends oss_daily_opoCountKey {
    private Date takedate;

    private String oilname;

    private Double backcanl;

    private String oilcan;

    private Double pumpnum;

    private Double oiltotal;

    private Double amount;

    private Date accountdate;

    private String transtatus;

    private String oucode;

    public Date getTakedate() {
        return takedate;
    }

    public void setTakedate(Date takedate) {
        this.takedate = takedate;
    }

    public String getOilname() {
        return oilname;
    }

    public void setOilname(String oilname) {
        this.oilname = oilname == null ? null : oilname.trim();
    }

    public Double getBackcanl() {
        return backcanl;
    }

    public void setBackcanl(Double backcanl) {
        this.backcanl = backcanl;
    }

    public String getOilcan() {
        return oilcan;
    }

    public void setOilcan(String oilcan) {
        this.oilcan = oilcan == null ? null : oilcan.trim();
    }

    public Double getPumpnum() {
        return pumpnum;
    }

    public void setPumpnum(Double pumpnum) {
        this.pumpnum = pumpnum;
    }

    public Double getOiltotal() {
        return oiltotal;
    }

    public void setOiltotal(Double oiltotal) {
        this.oiltotal = oiltotal;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getAccountdate() {
        return accountdate;
    }

    public void setAccountdate(Date accountdate) {
        this.accountdate = accountdate;
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