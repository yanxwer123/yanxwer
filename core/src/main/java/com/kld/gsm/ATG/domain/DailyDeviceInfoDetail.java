package com.kld.gsm.ATG.domain;

public class DailyDeviceInfoDetail extends DailyDeviceInfoDetailKey {
    private String probemodel;//探棒型号

    public String getProbemodel() {
        return probemodel;
    }

    public void setProbemodel(String probemodel) {
        this.probemodel = probemodel == null ? null : probemodel.trim();
    }

    @Override
    public String toString() {
        return "DailyDeviceInfoDetail{" +
                "oilcanno=" + this.getOilcanno() +
                ",probeno='" + this.getProbeno() +
                ",probemodel='" + probemodel + '\'' +
                '}';
    }
}