package com.kld.gsm.center.domain;

public class oss_sysmanage_TankInfoKey {
    private Integer oilcan; //油罐编号

    private String nodeno;//网点编号

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