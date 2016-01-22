package com.kld.gsm.coord.domain;

import java.util.Date;

/**
 * Created by yinzhiguang on 2015/11/8.
 */
public class Oilhotobill {
    private int vouchno;
    private String teamvouchno;
    private String takedate;
    private String oilno;
    private String oilname;
    private int oilgunno;
    private double topump;
    private double hopump;
    private double passnum;
    private Date accountdate;
    private String billstatus;
    private String hotoflag;
    private String transflag;
    private String transfer;

    public int getVouchno() {
        return vouchno;
    }

    public void setVouchno(int vouchno) {
        this.vouchno = vouchno;
    }

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

    public int getOilgunno() {
        return oilgunno;
    }

    public void setOilgunno(int oilgunno) {
        this.oilgunno = oilgunno;
    }

    public double getTopump() {
        return topump;
    }

    public void setTopump(double topump) {
        this.topump = topump;
    }

    public double getHopump() {
        return hopump;
    }

    public void setHopump(double hopump) {
        this.hopump = hopump;
    }

    public double getPassnum() {
        return passnum;
    }

    public void setPassnum(double passnum) {
        this.passnum = passnum;
    }

    public Date getAccountdate() {
        return accountdate;
    }

    public void setAccountdate(Date accountdate) {
        this.accountdate = accountdate;
    }

    public String getBillstatus() {
        return billstatus;
    }

    public void setBillstatus(String billstatus) {
        this.billstatus = billstatus;
    }

    public String getHotoflag() {
        return hotoflag;
    }

    public void setHotoflag(String hotoflag) {
        this.hotoflag = hotoflag;
    }

    public String getTransflag() {
        return transflag;
    }

    public void setTransflag(String transflag) {
        this.transflag = transflag;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }
}
