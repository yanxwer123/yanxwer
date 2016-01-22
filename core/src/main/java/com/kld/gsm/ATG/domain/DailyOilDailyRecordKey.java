package com.kld.gsm.ATG.domain;

import java.util.Date;

public class DailyOilDailyRecordKey {
    private Date accountdate;

    private String oilno;

    public Date getAccountdate() {
        return accountdate;
    }

    public void setAccountdate(Date accountdate) {
        this.accountdate = accountdate;
    }

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno == null ? null : oilno.trim();
    }
}