package com.kld.gsm.center.domain;

public class oss_sysmanage_OilGunInfo extends oss_sysmanage_OilGunInfoKey {
    private Integer macno;

    private Integer oilcan;

    private String oilgunname;

    private Integer ctrlunitnum;

    private Integer linkadr;

    private String gunstatus;

    private Double initpump;

    private String transtatus;

    private String oucode;

    public Integer getMacno() {
        return macno;
    }

    public void setMacno(Integer macno) {
        this.macno = macno;
    }

    public Integer getOilcan() {
        return oilcan;
    }

    public void setOilcan(Integer oilcan) {
        this.oilcan = oilcan;
    }

    public String getOilgunname() {
        return oilgunname;
    }

    public void setOilgunname(String oilgunname) {
        this.oilgunname = oilgunname == null ? null : oilgunname.trim();
    }

    public Integer getCtrlunitnum() {
        return ctrlunitnum;
    }

    public void setCtrlunitnum(Integer ctrlunitnum) {
        this.ctrlunitnum = ctrlunitnum;
    }

    public Integer getLinkadr() {
        return linkadr;
    }

    public void setLinkadr(Integer linkadr) {
        this.linkadr = linkadr;
    }

    public String getGunstatus() {
        return gunstatus;
    }

    public void setGunstatus(String gunstatus) {
        this.gunstatus = gunstatus == null ? null : gunstatus.trim();
    }

    public Double getInitpump() {
        return initpump;
    }

    public void setInitpump(Double initpump) {
        this.initpump = initpump;
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus == null ? null : transtatus.trim();
    }

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode == null ? null : oucode.trim();
    }
}