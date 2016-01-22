package com.kld.gsm.center.domain;

public class oss_sysmanage_OilGunInfoKey {
    private Integer oilgun;

    private String nodeno;

    public Integer getOilgun() {
        return oilgun;
    }

    public void setOilgun(Integer oilgun) {
        this.oilgun = oilgun;
    }

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }
}