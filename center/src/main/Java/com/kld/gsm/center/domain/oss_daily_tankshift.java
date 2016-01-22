package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_daily_tankshift extends oss_daily_tankshiftKey {
    private Date takedate;

    private String oilno;

    private String oilname;

    private Double tooilhigh;

    private Double tooill;

    private Double inoill;

    private Double hooilhigh;

    private Double hooill;

    private Double salel;

    private Date accountdate;

    private Double canreall;

    private Double heightwater;

    private Double waterl;

    private String transtatus;

    private String oucode;

    public Date getTakedate() {
        return takedate;
    }

    public void setTakedate(Date takedate) {
        this.takedate = takedate;
    }

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno == null ? null : oilno.trim();
    }

    public String getOilname() {
        return oilname;
    }

    public void setOilname(String oilname) {
        this.oilname = oilname == null ? null : oilname.trim();
    }

    public Double getTooilhigh() {
        return tooilhigh;
    }

    public void setTooilhigh(Double tooilhigh) {
        this.tooilhigh = tooilhigh;
    }

    public Double getTooill() {
        return tooill;
    }

    public void setTooill(Double tooill) {
        this.tooill = tooill;
    }

    public Double getInoill() {
        return inoill;
    }

    public void setInoill(Double inoill) {
        this.inoill = inoill;
    }

    public Double getHooilhigh() {
        return hooilhigh;
    }

    public void setHooilhigh(Double hooilhigh) {
        this.hooilhigh = hooilhigh;
    }

    public Double getHooill() {
        return hooill;
    }

    public void setHooill(Double hooill) {
        this.hooill = hooill;
    }

    public Double getSalel() {
        return salel;
    }

    public void setSalel(Double salel) {
        this.salel = salel;
    }

    public Date getAccountdate() {
        return accountdate;
    }

    public void setAccountdate(Date accountdate) {
        this.accountdate = accountdate;
    }

    public Double getCanreall() {
        return canreall;
    }

    public void setCanreall(Double canreall) {
        this.canreall = canreall;
    }

    public Double getHeightwater() {
        return heightwater;
    }

    public void setHeightwater(Double heightwater) {
        this.heightwater = heightwater;
    }

    public Double getWaterl() {
        return waterl;
    }

    public void setWaterl(Double waterl) {
        this.waterl = waterl;
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