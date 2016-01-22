package com.kld.gsm.center.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class oss_alarm_ShiftLost extends oss_alarm_ShiftLostKey {
    private String shift;

    private Integer oilcanno;

    private Double startoilheight;

    private Double startoill;

    private Double endoilheight;

    private Double endoill;

    private Double acutalendoill;

    private Double endwaterheight;

    private Double endwaterl;

    private Double endtemperature;

    private Double oildischarge;

    private Double sale;

    private Double inventory;

    private Double loss;

    private Integer state;

    private Date shifttime;

    private String transtatus;

    private String oucode;

    private String oilno;
    @JsonProperty("profitLossRatio")
    private Double profitlossratio;

    public String getShift() {
        return shift;
    }
    public  void setShift(String shift){this.shift=shift;}

    public Integer getOilcanno(){return oilcanno;}
    public void setOilcanno(Integer oilcanno){this.oilcanno=oilcanno;}

    public Double getStartoilheight() {
        return startoilheight;
    }

    public void setStartoilheight(Double startoilheight) {
        this.startoilheight = startoilheight;
    }

    public Double getStartoill() {
        return startoill;
    }

    public void setStartoill(Double startoill) {
        this.startoill = startoill;
    }

    public Double getEndoilheight() {
        return endoilheight;
    }

    public void setEndoilheight(Double endoilheight) {
        this.endoilheight = endoilheight;
    }

    public Double getEndoill() {
        return endoill;
    }

    public void setEndoill(Double endoill) {
        this.endoill = endoill;
    }

    public Double getAcutalendoill() {
        return acutalendoill;
    }

    public void setAcutalendoill(Double acutalendoill) {
        this.acutalendoill = acutalendoill;
    }

    public Double getEndwaterheight() {
        return endwaterheight;
    }

    public void setEndwaterheight(Double endwaterheight) {
        this.endwaterheight = endwaterheight;
    }

    public Double getEndwaterl() {
        return endwaterl;
    }

    public void setEndwaterl(Double endwaterl) {
        this.endwaterl = endwaterl;
    }

    public Double getEndtemperature() {
        return endtemperature;
    }

    public void setEndtemperature(Double endtemperature) {
        this.endtemperature = endtemperature;
    }

    public Double getOildischarge() {
        return oildischarge;
    }

    public void setOildischarge(Double oildischarge) {
        this.oildischarge = oildischarge;
    }

    public Double getSale() {
        return sale;
    }

    public void setSale(Double sale) {
        this.sale = sale;
    }

    public Double getInventory() {
        return inventory;
    }

    public void setInventory(Double inventory) {
        this.inventory = inventory;
    }

    public Double getLoss() {
        return loss;
    }

    public void setLoss(Double loss) {
        this.loss = loss;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getShifttime() {
        return shifttime;
    }

    public void setShifttime(Date shifttime) {
        this.shifttime = shifttime;
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
    public String getOilno(){return  oilno;}
    public void setOilno(String oilno){this.oilno=oilno;}
    public Double getProfitlossratio(){return profitlossratio;}
    public void setProfitlossratio(Double profitlossratio){this.profitlossratio=profitlossratio;}
}