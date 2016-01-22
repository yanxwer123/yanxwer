package com.kld.gsm.ATG.domain;

import java.util.Date;

public class DailyTradeInventoryKey {
    private Integer macno;

    private Integer ttc;

    private Date takedate;

    private String oilgun;

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
}