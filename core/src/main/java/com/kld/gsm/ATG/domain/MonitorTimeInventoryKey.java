package com.kld.gsm.ATG.domain;

import java.util.Date;

public class MonitorTimeInventoryKey {
    private Integer oilcan;

    private Date storetime;

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
}