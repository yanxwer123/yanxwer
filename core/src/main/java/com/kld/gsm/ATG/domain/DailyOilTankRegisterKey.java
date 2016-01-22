package com.kld.gsm.ATG.domain;

import java.util.Date;

public class DailyOilTankRegisterKey {
    private Date accountdate;

    private Integer oilcan;

    public Date getAccountdate() {
        return accountdate;
    }

    public void setAccountdate(Date accountdate) {
        this.accountdate = accountdate;
    }

    public Integer getOilcan() {
        return oilcan;
    }

    public void setOilcan(Integer oilcan) {
        this.oilcan = oilcan;
    }
}