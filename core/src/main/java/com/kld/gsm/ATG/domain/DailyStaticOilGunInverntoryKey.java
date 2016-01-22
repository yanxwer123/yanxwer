package com.kld.gsm.ATG.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DailyStaticOilGunInverntoryKey {

    private Integer oilcan;

    private String id;


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
        this.id = id == null ? null : id.trim();
    }

    @Override
    public String toString() {
        return "DailyStaticOilGunInverntoryKey{" +
                "oilcan=" + oilcan +
                ", id='" + id + '\'' +
                '}';
    }
}