package com.kld.gsm.center.domain;

public class oss_sysmanage_AlarmPlus extends oss_sysmanage_AlarmPlusKey {
    private Double waterhightalarm;

    private Double stealoilalarm;

    private Double leakoilalarm;

    private Double leakageoilalarm;

    private String version;

    public Double getWaterhightalarm() {
        return waterhightalarm;
    }

    public void setWaterhightalarm(Double waterhightalarm) {
        this.waterhightalarm = waterhightalarm;
    }

    public Double getStealoilalarm() {
        return stealoilalarm;
    }

    public void setStealoilalarm(Double stealoilalarm) {
        this.stealoilalarm = stealoilalarm;
    }

    public Double getLeakoilalarm() {
        return leakoilalarm;
    }

    public void setLeakoilalarm(Double leakoilalarm) {
        this.leakoilalarm = leakoilalarm;
    }

    public Double getLeakageoilalarm() {
        return leakageoilalarm;
    }

    public void setLeakageoilalarm(Double leakageoilalarm) {
        this.leakageoilalarm = leakageoilalarm;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }
}