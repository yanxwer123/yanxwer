package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_daily_TradeAccountKey {
    private Integer macno;

    private Integer ttc;

    private Date takedate;

    private String oilgun;

    private String nodeno;

    public Integer getMacno() {
        return macno;
    }

    public void setMacno(Integer macno) {
        this.macno = macno;
    }

    public Integer getTtc() {
        return ttc;
    }

    public void setTtc(Integer ttc) {
        this.ttc = ttc;
    }

    public Date getTakedate() {
        return takedate;
    }

    public void setTakedate(Date takedate) {
        this.takedate = takedate;
    }

    public String getOilgun() {
        return oilgun;
    }

    public void setOilgun(String oilgun) {
        this.oilgun = oilgun == null ? null : oilgun.trim();
    }

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }
}