package com.kld.gsm.ATG.domain;

import java.util.Date;

public class AlarmMeasureLeakKey {
    private Integer oilcanno;

    private Date startdate;

    public Integer getOilcanno() {
        return oilcanno;
    }

    public void setOilcanno(Integer oilcanno) {
        this.oilcanno = oilcanno;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }
}