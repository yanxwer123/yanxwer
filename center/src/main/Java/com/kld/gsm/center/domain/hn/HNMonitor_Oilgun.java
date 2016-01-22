package com.kld.gsm.center.domain.hn;


import com.fasterxml.jackson.annotation.JsonIgnore;

/*
Created BY niyang
Created Date 2015/12/11
*/
public class HNMonitor_Oilgun {
    private String id;

    private Integer gunno;

    private Integer oilcan;

    private Double pumpup;

    @JsonIgnore
    public Integer getOilcan() {
        return oilcan;
    }

    public void setOilcan(Integer oilcan) {
        this.oilcan = oilcan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getGunno() {
        return gunno;
    }

    public void setGunno(Integer gunno) {
        this.gunno = gunno;
    }

    public Double getPumpup() {
        return pumpup;
    }

    public void setPumpup(Double pumpup) {
        this.pumpup = pumpup;
    }
}
