package com.kld.gsm.ATG.domain;

import java.text.DecimalFormat;

public class DailyOpotCount extends DailyOpotCountKey {
    private String oilname;

    private Double liter;

    private Double price;

    private Double amount;

    private String transtatus;

    public String getOilname() {
        return oilname;
    }

    public void setOilname(String oilname) {
        this.oilname = oilname == null ? null : oilname.trim();
    }

    public Double getLiter() {
        DecimalFormat df = new DecimalFormat("######0.00");
        return Double.valueOf(df.format(liter));
    }

    public void setLiter(Double liter) {
        this.liter = liter;
    }

    public Double getPrice() {
        DecimalFormat df = new DecimalFormat("######0.00");
        return Double.valueOf(df.format(price));
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        DecimalFormat df = new DecimalFormat("######0.00");
        return Double.valueOf(df.format(amount));
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus == null ? "0" : transtatus.trim();
    }
}