package com.kld.gsm.ATG.domain;

import java.util.Date;

public class oss_monitor_Pump {
    private Integer gunno;

    private Double pump;

    private Date modifydate;

    public Integer getGunno() {
        return gunno;
    }

    public void setGunno(Integer gunno) {
        this.gunno = gunno;
    }

    public Double getPump() {
        return pump;
    }

    public void setPump(Double pump) {
        this.pump = pump;
    }

    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }
}