package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_daily_oildaybillKey {
    private Date accountdate;

    private Integer oilgun;

    private String nodeno;

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

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }
}