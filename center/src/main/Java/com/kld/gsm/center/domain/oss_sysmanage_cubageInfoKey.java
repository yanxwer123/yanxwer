package com.kld.gsm.center.domain;

public class oss_sysmanage_cubageInfoKey {
    private String version;

    private String oilcan;

    private String nodeno;
    private Double height;
    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getOilcan() {
        return oilcan;
    }

    public void setOilcan(String oilcan) {
        this.oilcan = oilcan;
    }

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }
}