package com.kld.gsm.center.domain;

public class oss_monitor_Summary_history extends oss_monitor_Summary_historyKey {
    private String oucode;

    private Double qysales;

    private Double cysales;

    private Double qystore;

    private Double cystore;

    private Double qyoilin;

    private Double cyoilin;

    private Integer tjcount;

    private Double ywyrate;

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode == null ? null : oucode.trim();
    }

    public Double getQysales() {
        return qysales;
    }

    public void setQysales(Double qysales) {
        this.qysales = qysales;
    }

    public Double getCysales() {
        return cysales;
    }

    public void setCysales(Double cysales) {
        this.cysales = cysales;
    }

    public Double getQystore() {
        return qystore;
    }

    public void setQystore(Double qystore) {
        this.qystore = qystore;
    }

    public Double getCystore() {
        return cystore;
    }

    public void setCystore(Double cystore) {
        this.cystore = cystore;
    }

    public Double getQyoilin() {
        return qyoilin;
    }

    public void setQyoilin(Double qyoilin) {
        this.qyoilin = qyoilin;
    }

    public Double getCyoilin() {
        return cyoilin;
    }

    public void setCyoilin(Double cyoilin) {
        this.cyoilin = cyoilin;
    }

    public Integer getTjcount() {
        return tjcount;
    }

    public void setTjcount(Integer tjcount) {
        this.tjcount = tjcount;
    }

    public Double getYwyrate() {
        return ywyrate;
    }

    public void setYwyrate(Double ywyrate) {
        this.ywyrate = ywyrate;
    }
}