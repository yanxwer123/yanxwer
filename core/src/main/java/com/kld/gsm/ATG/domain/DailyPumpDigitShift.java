package com.kld.gsm.ATG.domain;

import java.text.DecimalFormat;
import java.util.Date;

public class DailyPumpDigitShift extends DailyPumpDigitShiftKey {
    private Date takedate;

    private String oilno;

    private String oilname;

    private Double topump;

    private Double hopump;

    private Double pumpnum;

    private Date paydate;

    private String transtatus;

    public Date getTakedate() {
        return takedate;
    }

    public void setTakedate(Date takedate) {
        this.takedate = takedate;
    }

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
        DecimalFormat df = new DecimalFormat("######0.00");
        return Double.valueOf(df.format(topump));
    }

    public void setTopump(Double topump) {
        this.topump = topump;
    }

    public Double getHopump() {
        DecimalFormat df = new DecimalFormat("######0.00");
        return Double.valueOf(df.format(hopump));
    }

    public void setHopump(Double hopump) {
        this.hopump = hopump;
    }

    public Double getPumpnum() {
        DecimalFormat df = new DecimalFormat("######0.00");
        return Double.valueOf(df.format(pumpnum));
     }

    public void setPumpnum(Double pumpnum) {
        this.pumpnum = pumpnum;
    }

    public Date getPaydate() {
        return paydate;
    }

    public void setPaydate(Date paydate) {
        this.paydate = paydate;
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus == null ? "0" : transtatus.trim();
    }
}