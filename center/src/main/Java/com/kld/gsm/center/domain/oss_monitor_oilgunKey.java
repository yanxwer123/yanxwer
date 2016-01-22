package com.kld.gsm.center.domain;

public class oss_monitor_oilgunKey {
    private String id;

    private Integer gunno;

    private String nodeno;

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

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }
}