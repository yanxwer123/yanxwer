package com.kld.gsm.center.domain;

public class oss_sysmanage_PaTRelationKey {
    private String probemodel;

    private Integer oilcan;

    private String nodeno;

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

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }
}