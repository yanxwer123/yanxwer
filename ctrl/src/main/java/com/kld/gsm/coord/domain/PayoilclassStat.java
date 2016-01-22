package com.kld.gsm.coord.domain;

import java.util.Date;

/**
 * Created by yinzhiguang on 2015/11/8.
 */
public class PayoilclassStat {
    private String teamvouchno;
    private String takedate;
    private String oilno;
    private String oilname;
    private String payoiltype;
    private double oilamount;
    private double unitprice;
    private double amount;
    private String transflag;
    private String hotoflag;
    private String dayflag;
    private Date accountdate;
    private String transfer;

    public String getTeamvouchno() {
        return teamvouchno;
    }

    public void setTeamvouchno(String teamvouchno) {
        this.teamvouchno = teamvouchno;
    }

    public String getTakedate() {
        return takedate;
    }

    public void setTakedate(String takedate) {
        this.takedate = takedate;
    }

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno;
    }

    public String getOilname() {
        return oilname;
    }

    public void setOilname(String oilname) {
        this.oilname = oilname;
    }

    public String getPayoiltype() {
        return payoiltype;
    }

    public void setPayoiltype(String payoiltype) {
        this.payoiltype = payoiltype;
    }

    public double getOilamount() {
        return oilamount;
    }

    public void setOilamount(double oilamount) {
        this.oilamount = oilamount;
    }

    public double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransflag() {
        return transflag;
    }

    public void setTransflag(String transflag) {
        this.transflag = transflag;
    }

    public String getHotoflag() {
        return hotoflag;
    }

    public void setHotoflag(String hotoflag) {
        this.hotoflag = hotoflag;
    }

    public String getDayflag() {
        return dayflag;
    }

    public void setDayflag(String dayflag) {
        this.dayflag = dayflag;
    }

    public Date getAccountdate() {
        return accountdate;
    }

    public void setAccountdate(Date accountdate) {
        this.accountdate = accountdate;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }
}
