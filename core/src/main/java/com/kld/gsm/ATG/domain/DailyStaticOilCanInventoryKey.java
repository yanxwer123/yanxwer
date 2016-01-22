package com.kld.gsm.ATG.domain;

public class DailyStaticOilCanInventoryKey {
    private String id;

    private Integer oilcan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getOilcan() {
        return oilcan;
    }

    public void setOilcan(Integer oilcan) {
        this.oilcan = oilcan;
    }
}