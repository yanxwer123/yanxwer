package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_alarm_GaTContrast extends oss_alarm_GaTContrastKey {
    private Double fristmeasurestore;

    private Date secodemeasuretime;

    private Double secodemeasurestore;

    private Double intervalsales;

    private String intervaltime;

    private Double difference;

    private String transtatus;

    private String shift;

    private String oucode;

    public Double getFristmeasurestore() {
        return fristmeasurestore;
    }

    public void setFristmeasurestore(Double fristmeasurestore) {
        this.fristmeasurestore = fristmeasurestore;
    }

    public Date getSecodemeasuretime() {
        return secodemeasuretime;
    }

    public void setSecodemeasuretime(Date secodemeasuretime) {
        this.secodemeasuretime = secodemeasuretime;
    }

    public Double getSecodemeasurestore() {
        return secodemeasurestore;
    }

    public void setSecodemeasurestore(Double secodemeasurestore) {
        this.secodemeasurestore = secodemeasurestore;
    }

    public Double getIntervalsales() {
        return intervalsales;
    }

    public void setIntervalsales(Double intervalsales) {
        this.intervalsales = intervalsales;
    }

    public String getIntervaltime() {
        return intervaltime;
    }

    public void setIntervaltime(String intervaltime) {
        this.intervaltime = intervaltime == null ? null : intervaltime.trim();
    }

    public Double getDifference() {
        return difference;
    }

    public void setDifference(Double difference) {
        this.difference = difference;
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus == null ? null : transtatus.trim();
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
}