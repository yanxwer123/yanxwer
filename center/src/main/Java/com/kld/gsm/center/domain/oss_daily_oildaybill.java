package com.kld.gsm.center.domain;

public class oss_daily_oildaybill extends oss_daily_oildaybillKey {
    private String oilno;

    private String oilname;

    private Double topump;

    private Double hopump;

    private Double pumpnum;

    private String transtatus;

    private String oucode;

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno == null ? null : oilno.trim();
    }

    public String getOilname() {
        return oilname;
    }

    public void setOilname(String oilname) {
        this.oilname = oilname == null ? null : oilname.trim();
    }

    public Double getTopump() {
        return topump;
    }

    public void setTopump(Double topump) {
        this.topump = topump;
    }

    public Double getHopump() {
        return hopump;
    }

    public void setHopump(Double hopump) {
        this.hopump = hopump;
    }

    public Double getPumpnum() {
        return pumpnum;
    }

    public void setPumpnum(Double pumpnum) {
        this.pumpnum = pumpnum;
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