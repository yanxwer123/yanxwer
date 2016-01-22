package com.kld.gsm.center.domain;

public class oss_sysmanage_Station {
    private String stationCode;

    private String stationName;

    private String shortName;

    private String dsgs;

    private String lspq;

    private String lsgc;

    private String kcdd;

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode == null ? null : stationCode.trim();
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName == null ? null : stationName.trim();
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public String getDsgs() {
        return dsgs;
    }

    public void setDsgs(String dsgs) {
        this.dsgs = dsgs == null ? null : dsgs.trim();
    }

    public String getLspq() {
        return lspq;
    }

    public void setLspq(String lspq) {
        this.lspq = lspq == null ? null : lspq.trim();
    }

    public String getLsgc() {
        return lsgc;
    }

    public void setLsgc(String lsgc) {
        this.lsgc = lsgc == null ? null : lsgc.trim();
    }

    public String getKcdd() {
        return kcdd;
    }

    public void setKcdd(String kcdd) {
        this.kcdd = kcdd == null ? null : kcdd.trim();
    }
}