package com.kld.gsm.center.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class oss_alarm_SaleOut extends oss_alarm_SaleOutKey {

    private Double nowvolume;

    private Double cansalevolume;

    private Double dayaveragesales;

    private Double houraveragesales;

    private Double predictsales;

    private String transtatus;

    private Date startalarmtime;

    private Date endalarmtime;

    private String shift;

    private String oucode;
    @JsonProperty("predictHours")
    private Integer predicthours;


    public Double getNowvolume() {
        return nowvolume;
    }

    public void setNowvolume(Double nowvolume) {
        this.nowvolume = nowvolume;
    }

    public Double getCansalevolume() {
        return cansalevolume;
    }

    public void setCansalevolume(Double cansalevolume) {
        this.cansalevolume = cansalevolume;
    }

    public Double getDayaveragesales() {
        return dayaveragesales;
    }

    public void setDayaveragesales(Double dayaveragesales) {
        this.dayaveragesales = dayaveragesales;
    }

    public Double getHouraveragesales() {
        return houraveragesales;
    }

    public void setHouraveragesales(Double houraveragesales) {
        this.houraveragesales = houraveragesales;
    }

    public Double getPredictsales() {
        return predictsales;
    }

    public void setPredictsales(Double predictsales) {
        this.predictsales = predictsales;
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus == null ? null : transtatus.trim();
    }

    public Date getStartalarmtime() {
        return startalarmtime;
    }

    public void setStartalarmtime(Date startalarmtime) {
        this.startalarmtime = startalarmtime;
    }

    public Date getEndalarmtime() {
        return endalarmtime;
    }

    public void setEndalarmtime(Date endalarmtime) {
        this.endalarmtime = endalarmtime;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift == null ? null : shift.trim();
    }

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode == null ? null : oucode.trim();
    }

    public Integer getPredicthours(){return  predicthours;}

    public void setPredicthours(Integer predicthours){this.predicthours=predicthours;}

}