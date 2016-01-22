package com.kld.gsm.ATG.domain;

import java.math.BigDecimal;
import java.util.Date;

public class DailyDailyBalance extends DailyDailyBalanceKey {
	//期初罐存（账存）
    private BigDecimal darlyankstock;

    //期间进货_出库单号
    private String deliveryno;

    //期间进货_进货数量L
    private BigDecimal receivel;

    //期间付出
    private BigDecimal todayout;

    //期末库存
    private BigDecimal todaystock;

    //实测库存
    private BigDecimal realstock;

    //损耗量
    private BigDecimal loss;

    //损耗率
    private BigDecimal losssent;

    //生成时间
    private Date createtime;

    //传输状态
    @Override
    public String toString() {
        return "DailyDailyBalance{" +
                "darlyankstock=" + darlyankstock +
                ", deliveryno='" + deliveryno + '\'' +
                ", receivel=" + receivel +
                ", todayout=" + todayout +
                ", todaystock=" + todaystock +
                ", realstock=" + realstock +
                ", loss=" + loss +
                ", losssent=" + losssent +
                ", createtime=" + createtime +
                ", transtatus='" + transtatus + '\'' +
                ", oilno='" + this.getOilno() + '\'' +
                '}';
    }private String transtatus;

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
}