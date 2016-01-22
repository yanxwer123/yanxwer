package com.kld.gsm.ATG.domain;

import java.util.Date;

public class DailyOilDayBillKey {
    private Date accountdate;

    private Integer oilgun;

    public Date getAccountdate() {
        return accountdate;
    }

    public void setAccountdate(Date accountdate) {
        this.accountdate = accountdate;
    }

    public Integer getOilgun() {
        return oilgun;
    }

    public void setOilgun(Integer oilgun) {
        this.oilgun = oilgun;
    }
}