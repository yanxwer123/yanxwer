package com.kld.gsm.ATG.domain;

public class MonitorOilgun extends MonitorOilgunKey {
    private Integer oilcan;

    private Double pumpup;

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