package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_monitor_tankoil extends oss_monitor_tankoilKey {
    private String oucode;

    private Integer oilcan;

    private Date measuretime;

    private Double oill;

    private Double standardl;

    private Double heighttotal;

    private Double waterheight;

    private Double waterl;

    private Double temperature;

    private  String transtatus;

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus;
    }
    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode == null ? null : oucode.trim();
    }

    public Integer getOilcan() {
        return oilcan;
    }

    public void setOilcan(Integer oilcan) {
        this.oilcan = oilcan;
    }

    public Date getMeasuretime() {
        return measuretime;
    }

    public void setMeasuretime(Date measuretime) {
        this.measuretime = measuretime;
    }

    public Double getOill() {
        return oill;
    }

    public void setOill(Double oill) {
        this.oill = oill;
    }

    public Double getStandardl() {
        return standardl;
    }

    public void setStandardl(Double standardl) {
        this.standardl = standardl;
    }

    public Double getHeighttotal() {
        return heighttotal;
    }

    public void setHeighttotal(Double heighttotal) {
        this.heighttotal = heighttotal;
    }

    public Double getWaterheight() {
        return waterheight;
    }

    public void setWaterheight(Double waterheight) {
        this.waterheight = waterheight;
    }

    public Double getWaterl() {
        return waterl;
    }

    public void setWaterl(Double waterl) {
        this.waterl = waterl;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}