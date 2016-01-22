package com.kld.gsm.center.domain;

import java.math.BigDecimal;
import java.util.Date;

public class oss_daily_DailyBalance extends oss_daily_DailyBalanceKey {
    private BigDecimal darlyankstock;

    private String deliveryno;

    private BigDecimal receivel;

    private BigDecimal todayout;

    private BigDecimal todaystock;

    private BigDecimal realstock;

    private BigDecimal loss;

    private BigDecimal losssent;

    private Date createtime;

    private String transtatus;

    private String oucode;

    public BigDecimal getDarlyankstock() {
        return darlyankstock;
    }

    public void setDarlyankstock(BigDecimal darlyankstock) {
        this.darlyankstock = darlyankstock;
    }

    public String getDeliveryno() {
        return deliveryno;
    }

    public void setDeliveryno(String deliveryno) {
        this.deliveryno = deliveryno == null ? null : deliveryno.trim();
    }

    public BigDecimal getReceivel() {
        return receivel;
    }

    public void setReceivel(BigDecimal receivel) {
        this.receivel = receivel;
    }

    public BigDecimal getTodayout() {
        return todayout;
    }

    public void setTodayout(BigDecimal todayout) {
        this.todayout = todayout;
    }

    public BigDecimal getTodaystock() {
        return todaystock;
    }

    public void setTodaystock(BigDecimal todaystock) {
        this.todaystock = todaystock;
    }

    public BigDecimal getRealstock() {
        return realstock;
    }

    public void setRealstock(BigDecimal realstock) {
        this.realstock = realstock;
    }

    public BigDecimal getLoss() {
        return loss;
    }

    public void setLoss(BigDecimal loss) {
        this.loss = loss;
    }

    public BigDecimal getLosssent() {
        return losssent;
    }

    public void setLosssent(BigDecimal losssent) {
        this.losssent = losssent;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus == null ? null : transtatus.trim();
    }

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode == null ? null : oucode.trim();
    }
}