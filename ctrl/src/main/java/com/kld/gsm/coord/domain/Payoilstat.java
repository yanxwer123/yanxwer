package com.kld.gsm.coord.domain;

import java.util.Date;

/**
 * Created by yinzhiguang on 2015/11/8.
 */
public class Payoilstat {
    private String Teamvouchno;
    private String Takedate;
    private String Oilno;
    private String Oilname;
    private double Backcanliter;
    private String Oilcanno;
    private double Passnum;
    private double Oiltotal;
    private double Moneytotal;
    private Date Accountdate;
    private String Hotoflag;
    private String Transflag;
    private String Transfer;

    public String getTeamvouchno() {
        return Teamvouchno;
    }

    public void setTeamvouchno(String teamvouchno) {
        Teamvouchno = teamvouchno;
    }

    public String getTakedate() {
        return Takedate;
    }

    public void setTakedate(String takedate) {
        Takedate = takedate;
    }

    public String getOilno() {
        return Oilno;
    }

    public void setOilno(String oilno) {
        Oilno = oilno;
    }

    public String getOilname() {
        return Oilname;
    }

    public void setOilname(String oilname) {
        Oilname = oilname;
    }

    public double getBackcanliter() {
        return Backcanliter;
    }

    public void setBackcanliter(double backcanliter) {
        Backcanliter = backcanliter;
    }

    public String getOilcanno() {
        return Oilcanno;
    }

    public void setOilcanno(String oilcanno) {
        Oilcanno = oilcanno;
    }

    public double getPassnum() {
        return Passnum;
    }

    public void setPassnum(double passnum) {
        Passnum = passnum;
    }

    public double getOiltotal() {
        return Oiltotal;
    }

    public void setOiltotal(double oiltotal) {
        Oiltotal = oiltotal;
    }

    public double getMoneytotal() {
        return Moneytotal;
    }

    public void setMoneytotal(double moneytotal) {
        Moneytotal = moneytotal;
    }

    public Date getAccountdate() {
        return Accountdate;
    }

    public void setAccountdate(Date accountdate) {
        Accountdate = accountdate;
    }

    public String getHotoflag() {
        return Hotoflag;
    }

    public void setHotoflag(String hotoflag) {
        Hotoflag = hotoflag;
    }

    public String getTransflag() {
        return Transflag;
    }

    public void setTransflag(String transflag) {
        Transflag = transflag;
    }

    public String getTransfer() {
        return Transfer;
    }

    public void setTransfer(String transfer) {
        Transfer = transfer;
    }
}
