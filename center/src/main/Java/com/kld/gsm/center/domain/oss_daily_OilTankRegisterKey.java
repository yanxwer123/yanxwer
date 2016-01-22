package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_daily_OilTankRegisterKey {
    private Date accountdate;

    private Integer oilcan;

    private String nodeno;

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

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }
}