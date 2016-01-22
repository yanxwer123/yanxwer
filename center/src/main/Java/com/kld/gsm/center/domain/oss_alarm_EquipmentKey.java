package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_alarm_EquipmentKey {
    private Integer oilcan;

    private Date startalarmtime;

    private String nodeno;

    public Integer getOilcan() {
        return oilcan;
    }

    public void setOilcan(Integer oilcan) {
        this.oilcan = oilcan;
    }

    public Date getStartalarmtime() {
        return startalarmtime;
    }

    public void setStartalarmtime(Date startalarmtime) {
        this.startalarmtime = startalarmtime;
    }

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }
}