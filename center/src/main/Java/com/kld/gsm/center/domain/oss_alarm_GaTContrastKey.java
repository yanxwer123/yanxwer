package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_alarm_GaTContrastKey {
    private Integer oilcan;

    private Date fristmeasuretime;

    private String nodeno;

    public Integer getOilcan() {
        return oilcan;
    }

    public void setOilcan(Integer oilcan) {
        this.oilcan = oilcan;
    }

    public Date getFristmeasuretime() {
        return fristmeasuretime;
    }

    public void setFristmeasuretime(Date fristmeasuretime) {
        this.fristmeasuretime = fristmeasuretime;
    }

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }
}