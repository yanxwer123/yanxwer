package com.kld.gsm.center.domain;

public class oss_daily_opotDailyStatement extends oss_daily_opotDailyStatementKey {
    private String oilname;

    private Double liter;

    private Double price;

    private Double amount;

    private String transtatus;

    private String daystatus;

    private String oucode;

    public String getOilname() {
        return oilname;
    }

    public void setOilname(String oilname) {
        this.oilname = oilname == null ? null : oilname.trim();
    }

    public Double getLiter() {
        return liter;
    }

    public void setLiter(Double liter) {
        this.liter = liter;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus == null ? null : transtatus.trim();
    }

    public String getDaystatus() {
        return daystatus;
    }

    public void setDaystatus(String daystatus) {
        this.daystatus = daystatus == null ? null : daystatus.trim();
    }

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode == null ? null : oucode.trim();
    }
}