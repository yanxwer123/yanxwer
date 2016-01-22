package com.kld.gsm.ATG.domain;

public class MonitorOilgunKey {
    private String id;

    private Integer gunno;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getGunno() {
        return gunno;
    }

    public void setGunno(Integer gunno) {
        this.gunno = gunno;
    }
}