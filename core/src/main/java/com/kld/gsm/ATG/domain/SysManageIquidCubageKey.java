package com.kld.gsm.ATG.domain;

public class SysManageIquidCubageKey {
    private String version;

    private Integer oilcan;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public Integer getOilcan() {
        return oilcan;
    }

    public void setOilcan(Integer oilcan) {
        this.oilcan = oilcan;
    }
}