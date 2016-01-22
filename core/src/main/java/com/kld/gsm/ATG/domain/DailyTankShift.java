package com.kld.gsm.ATG.domain;

import java.text.DecimalFormat;
import java.util.Date;

public class DailyTankShift extends DailyTankShiftKey {
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
    DecimalFormat df = new DecimalFormat("######0.00");

    public Date getTakedate() {
        return takedate;
    }

    public String getOilno() {
        return oilno;
    }

    public String getOilname() {
        return oilname;
    }

    public Double getTooilhigh() {
        return Double.valueOf(df.format(tooilhigh));
    }

    public Double getTooill() {
        return Double.valueOf(df.format(tooill));
     }

    public Double getInoill() {
        return Double.valueOf(df.format(inoill));
    }

    public Double getHooilhigh() {
        return Double.valueOf(df.format(hooilhigh));
    }

    public Double getHooill() {
        return Double.valueOf(df.format(hooill));
    }

    public Double getSalel() {
        return Double.valueOf(df.format(salel));
    }

    public Date getAccountdate() {
        return accountdate;
    }

    public Double getCanreall() {
        return Double.valueOf(df.format(canreall));
    }

    public Double getHeightwater() {
        return Double.valueOf(df.format(heightwater));
     }

    public Double getWaterl() {
        return Double.valueOf(df.format(waterl));
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTakedate(Date takedate) {
        this.takedate = takedate;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno;
    }

    public void setOilname(String oilname) {
        this.oilname = oilname;
    }

    public void setTooilhigh(Double tooilhigh) {
        this.tooilhigh = tooilhigh;
    }

    public void setTooill(Double tooill) {
        this.tooill = tooill;
    }

    public void setInoill(Double inoill) {
        this.inoill = inoill;
    }

    public void setHooilhigh(Double hooilhigh) {
        this.hooilhigh = hooilhigh;
    }

    public void setHooill(Double hooill) {
        this.hooill = hooill;
    }

    public void setSalel(Double salel) {
        this.salel = salel;
    }

    public void setAccountdate(Date accountdate) {
        this.accountdate = accountdate;
    }

    public void setCanreall(Double canreall) {
        this.canreall = canreall;
    }

    public void setHeightwater(Double heightwater) {
        this.heightwater = heightwater;
    }

    public void setWaterl(Double waterl) {
        this.waterl = waterl;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus == null ? "0" : transtatus.trim();;
    }

    @Override
    public String toString() {
        return "DailyTankShift{" +
                "takedate=" + takedate +
                ", oilno='" + oilno + '\'' +
                ", oilname='" + oilname + '\'' +
                ", tooilhigh=" + tooilhigh +
                ", tooill=" + tooill +
                ", inoill=" + inoill +
                ", hooilhigh=" + hooilhigh +
                ", hooill=" + hooill +
                ", salel=" + salel +
                ", accountdate=" + accountdate +
                ", canreall=" + canreall +
                ", heightwater=" + heightwater +
                ", waterl=" + waterl +
                ", transtatus='" + transtatus + '\'' +
                ", df=" + df +
                '}';
    }
}