package com.kld.gsm.center.domain;

public class oss_monitor_oilgun extends oss_monitor_oilgunKey {
    private String oucode;

    private Integer oilcan;

    private Double pumpup;

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode == null ? null : oucode.trim();
    }

    public Integer getOilcan() {
        return oilcan;
    }

    public void setOilcan(Integer oilcan) {
        this.oilcan = oilcan;
    }

    public Double getPumpup() {
        return pumpup;
    }

    public void setPumpup(Double pumpup) {
        this.pumpup = pumpup;
    }
}