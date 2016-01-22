package com.kld.gsm.center.domain;

public class oss_sysmanage_timeSaleOutKey {
    private Integer oilcan;

    private Integer saletime;

    private String nodeno;

    public Integer getOilcan() {
        return oilcan;
    }

    public void setOilcan(Integer oilcan) {
        this.oilcan = oilcan;
    }

    public Integer getSaletime() {
        return saletime;
    }

    public void setSaletime(Integer saletime) {
        this.saletime = saletime;
    }

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }
}