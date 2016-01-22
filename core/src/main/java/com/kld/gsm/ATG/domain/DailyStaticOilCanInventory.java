package com.kld.gsm.ATG.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class DailyStaticOilCanInventory extends DailyStaticOilCanInventoryKey {
    private Date measuretime;

    private Double oil;

    private Double standardl;

    private Double heighttotal;

    private Double waterheight;

    private Double waterl;

    private Double temperature;

    private String transtatus;

    public Date getMeasuretime() {
        return measuretime;
    }

    public void setMeasuretime(Date measuretime) {
        this.measuretime = measuretime;
    }

    @JsonProperty("oill")
    public Double getOil() {
        return oil;
    }

    public void setOil(Double oil) {
        this.oil = oil;
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

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus;
    }

    @Override
    public String toString() {
        return "DailyStaticOilCanInventory{" +
                "measuretime=" + measuretime +
                ", oil=" + oil +
                ", standardl=" + standardl +
                ", heighttotal=" + heighttotal +
                ", waterheight=" + waterheight +
                ", waterl=" + waterl +
                ", temperature=" + temperature +
                ", transtatus='" + transtatus + '\'' +
                '}';
    }
}