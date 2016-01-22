package com.kld.gsm.center.domain;

public class oss_sysmanage_AlarmPlusKey {
    private String nodeno;

    private String oilcanno;

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }

    public String getOilcanno() {
        return oilcanno;
    }

    public void setOilcanno(String oilcanno) {
        this.oilcanno = oilcanno == null ? null : oilcanno.trim();
    }
}