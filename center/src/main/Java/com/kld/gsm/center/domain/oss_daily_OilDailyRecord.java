package com.kld.gsm.center.domain;

public class oss_daily_OilDailyRecord extends oss_daily_OilDailyRecordKey {
    private String oilname;

    private Double price;

    private Double daystartl;

    private Double dayendl;

    private String getbillno;

    private Double dayinoill;

    private Double dayincount;

    private Double daysalel;

    private Double daysalelsum;

    private Double daysalecountsum;

    private Double dayindel;

    private String transtatus;

    private String oucode;

    public String getOilname() {
        return oilname;
    }

    public void setOilname(String oilname) {
        this.oilname = oilname == null ? null : oilname.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDaystartl() {
        return daystartl;
    }

    public void setDaystartl(Double daystartl) {
        this.daystartl = daystartl;
    }

    public Double getDayendl() {
        return dayendl;
    }

    public void setDayendl(Double dayendl) {
        this.dayendl = dayendl;
    }

    public String getGetbillno() {
        return getbillno;
    }

    public void setGetbillno(String getbillno) {
        this.getbillno = getbillno == null ? null : getbillno.trim();
    }

    public Double getDayinoill() {
        return dayinoill;
    }

    public void setDayinoill(Double dayinoill) {
        this.dayinoill = dayinoill;
    }

    public Double getDayincount() {
        return dayincount;
    }

    public void setDayincount(Double dayincount) {
        this.dayincount = dayincount;
    }

    public Double getDaysalel() {
        return daysalel;
    }

    public void setDaysalel(Double daysalel) {
        this.daysalel = daysalel;
    }

    public Double getDaysalelsum() {
        return daysalelsum;
    }

    public void setDaysalelsum(Double daysalelsum) {
        this.daysalelsum = daysalelsum;
    }

    public Double getDaysalecountsum() {
        return daysalecountsum;
    }

    public void setDaysalecountsum(Double daysalecountsum) {
        this.daysalecountsum = daysalecountsum;
    }

    public Double getDayindel() {
        return dayindel;
    }

    public void setDayindel(Double dayindel) {
        this.dayindel = dayindel;
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