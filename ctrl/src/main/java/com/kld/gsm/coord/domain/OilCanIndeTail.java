package com.kld.gsm.coord.domain;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2015/11/10 14:12
 * @Description:油罐进油明细表
 */
public class OilCanIndeTail {

    private String vouchno;//进油明细号

    private String oilno;//油品编号

    private Integer oilcanno;//油罐编号

    public Integer getOilcanno() {
        return oilcanno;
    }

    public void setOilcanno(Integer oilcanno) {
        this.oilcanno = oilcanno;
    }

    private String teamvouchno;//班次流水

    private Date ingoodsdate;//进货时间

    private String ingoodsperson;//进货人员

    private String goodsbillno;//提货单号

    private Double inbefofootage;//进油前尺数

    private Double inbefoliter;//进油前升数

    private Double inaftefootage;//进油后尺数

    private Double inafteliter;//进油后升数

    private Date accountdate;//结账日

    private String chkoprno;//复核人

    private Integer billstatus;//单据状态

    private String transflag;//传输标志

    private String dayflag;//日结标志

    public String getVouchno() {
        return vouchno;
    }

    public void setVouchno(String vouchno) {
        this.vouchno = vouchno;
    }

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno;
    }



    public String getTeamvouchno() {
        return teamvouchno;
    }

    public void setTeamvouchno(String teamvouchno) {
        this.teamvouchno = teamvouchno;
    }

    public Date getIngoodsdate() {
        return ingoodsdate;
    }

    public void setIngoodsdate(Date ingoodsdate) {
        this.ingoodsdate = ingoodsdate;
    }

    public String getIngoodsperson() {
        return ingoodsperson;
    }

    public void setIngoodsperson(String ingoodsperson) {
        this.ingoodsperson = ingoodsperson;
    }

    public String getGoodsbillno() {
        return goodsbillno;
    }

    public void setGoodsbillno(String goodsbillno) {
        this.goodsbillno = goodsbillno;
    }

    public Double getInbefofootage() {
        return inbefofootage;
    }

    public void setInbefofootage(Double inbefofootage) {
        this.inbefofootage = inbefofootage;
    }

    public Double getInbefoliter() {
        return inbefoliter;
    }

    public void setInbefoliter(Double inbefoliter) {
        this.inbefoliter = inbefoliter;
    }

    public Double getInaftefootage() {
        return inaftefootage;
    }

    public void setInaftefootage(Double inaftefootage) {
        this.inaftefootage = inaftefootage;
    }

    public Double getInafteliter() {
        return inafteliter;
    }

    public void setInafteliter(Double inafteliter) {
        this.inafteliter = inafteliter;
    }

    public Date getAccountdate() {
        return accountdate;
    }

    public void setAccountdate(Date accountdate) {
        this.accountdate = accountdate;
    }

    public String getChkoprno() {
        return chkoprno;
    }

    public void setChkoprno(String chkoprno) {
        this.chkoprno = chkoprno;
    }

    public Integer getBillstatus() {
        return billstatus;
    }

    public void setBillstatus(Integer billstatus) {
        this.billstatus = billstatus;
    }

    public String getTransflag() {
        return transflag;
    }

    public void setTransflag(String transflag) {
        this.transflag = transflag;
    }

    public String getDayflag() {
        return dayflag;
    }

    public void setDayflag(String dayflag) {
        this.dayflag = dayflag;
    }
}
