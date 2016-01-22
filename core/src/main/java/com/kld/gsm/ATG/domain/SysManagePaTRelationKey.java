package com.kld.gsm.ATG.domain;

public class SysManagePaTRelationKey {
        private String probemodel;//探棒编号

    private Integer oilcan;//油罐编号

    public String getProbemodel() {
        return probemodel;
    }

    public void setProbemodel(String probemodel) {
        this.probemodel = probemodel == null ? null : probemodel.trim();
    }

    public Integer getOilcan() {
        return oilcan;
    }

    public void setOilcan(Integer oilcan) {
        this.oilcan = oilcan;
    }
}