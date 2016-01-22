package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_alarm_DailyLost extends oss_alarm_DailyLostKey {
    private Double darlyankstock;

    private String deliveryno;

    private Double receivel;

    private Double todayout;

    private Double todayendstock;

    private Double realstock;

    private Double cost;

    private Double costsent;

    private Date createtime;

    private String transtatus;

    private String oucode;

    public Double getDarlyankstock() {
        return darlyankstock;
    }

    public void setDarlyankstock(Double darlyankstock) {
        this.darlyankstock = darlyankstock;
    }

    public String getDeliveryno() {
        return deliveryno;
    }

    public void setDeliveryno(String deliveryno) {
        this.deliveryno = deliveryno == null ? null : deliveryno.trim();
    }

    public Double getReceivel() {
        return receivel;
    }

    public void setReceivel(Double receivel) {
        this.receivel = receivel;
    }

    public Double getTodayout() {
        return todayout;
    }

    public void setTodayout(Double todayout) {
        this.todayout = todayout;
    }

    public Double getTodayendstock() {
        return todayendstock;
    }

    public void setTodayendstock(Double todayendstock) {
        this.todayendstock = todayendstock;
    }

    public Double getRealstock() {
        return realstock;
    }

    public void setRealstock(Double realstock) {
        this.realstock = realstock;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getCostsent() {
        return costsent;
    }

    public void setCostsent(Double costsent) {
        this.costsent = costsent;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus == null ? null : transtatus.trim();
    }

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode == null ? null : oucode.trim();
    }
}