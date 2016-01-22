package com.kld.gsm.center.domain;

public class oss_sysmanage_probeParKey {
    private String probemodel;

    private String nodeno;

    public String getProbemodel() {
        return probemodel;
    }

    public void setProbemodel(String probemodel) {
        this.probemodel = probemodel == null ? null : probemodel.trim();
    }

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }
}