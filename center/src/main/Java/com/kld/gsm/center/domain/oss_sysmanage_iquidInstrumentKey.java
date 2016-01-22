package com.kld.gsm.center.domain;

public class oss_sysmanage_iquidInstrumentKey {
    private Integer id;

    private String nodeno;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }
}