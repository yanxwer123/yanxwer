package com.kld.gsm.center.domain;

public class oss_sysmanage_OilMachineInfoKey {
    private Integer macno;

    private String nodeno;

    public Integer getMacno() {
        return macno;
    }

    public void setMacno(Integer macno) {
        this.macno = macno;
    }

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }
}