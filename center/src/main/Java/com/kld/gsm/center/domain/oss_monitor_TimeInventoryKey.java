package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_monitor_TimeInventoryKey {
    private Integer oilcan;

    private Date storetime;

    private String nodeno;

    public Integer getOilcan() {
        return oilcan;
    }

    public void setOilcan(Integer oilcan) {
        this.oilcan = oilcan;
    }

    public Date getStoretime() {
        return storetime;
    }

    public void setStoretime(Date storetime) {
        this.storetime = storetime;
    }

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }
}