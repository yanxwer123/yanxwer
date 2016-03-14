package com.kld.gsm.center.domain;

public class SysmanageRealgiveoil {
    private String ckdid;

    private String sjfyl;

    private Double wd;

    private Double md;

    public String getCkdid() {
        return ckdid;
    }

    public void setCkdid(String ckdid) {
        this.ckdid = ckdid;
    }

    public String getSjfyl() {
        return sjfyl;
    }

    public void setSjfyl(String sjfyl) {
        this.sjfyl = sjfyl;
    }

    public Double getWd() {
        return wd;
    }

    public void setWd(Double wd) {
        this.wd = wd;
    }

    public Double getMd() {
        return md;
    }

    public void setMd(Double md) {
        this.md = md;
    }

    @Override
    public String toString() {
        return "SysmanageRealgiveoil{" +
                "ckdid='" + ckdid + '\'' +
                ", sjfyl='" + sjfyl + '\'' +
                ", wd=" + wd +
                ", md=" + md +
                '}';
    }
}