package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_sysmanage_timeSaleOut extends oss_sysmanage_timeSaleOutKey {
    private Double sales;

    private Date createtime;

    private String oucode;

    public Double getSales() {
        return sales;
    }

    public void setSales(Double sales) {
        this.sales = sales;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode == null ? null : oucode.trim();
    }
}