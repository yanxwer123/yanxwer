package com.kld.gsm.ATG.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class DailyTradeInventory extends DailyTradeInventoryKey {
    private Integer oilcan;

    private String oilno;

    private String cardno;

    private Date opetime;

    private String stockdate;

    private String stocktime;

    private Double oill;

    private Double standardl;

    private Double emptyl;

    private Double heighttotal;

    private Double heightwater;

    private Double oiltemp;

    private Double waterl;

    private Double density;

    private Double densitystandard;

    private String shift;

    private String remark;

    private String transtatus;

    private String backmatchflag;

    private Double liter;
    private Double pumpno;

    public Double getLiter() {
        return liter;
    }

    public void setLiter(Double liter) {
        this.liter = liter;
    }

    public Double getPumpno() {
        return pumpno;
    }

    public void setPumpno(Double pumpno) {
        this.pumpno = pumpno;
    }
    public Integer getOilcan() {
        return oilcan;
    }

    public void setOilcan(Integer oilcan) {
        this.oilcan = oilcan;
    }

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno == null ? null : oilno.trim();
    }

    public Date getOpetime() {
        return opetime;
    }

    public void setOpetime(Date opetime) {
        this.opetime = opetime;
    }

    public String getStockdate() {
        return stockdate;
    }

    public void setStockdate(String stockdate) {
        this.stockdate = stockdate == null ? null : stockdate.trim();
    }

    public String getStocktime() {
        return stocktime;
    }

    public void setStocktime(String stocktime) {
        this.stocktime = stocktime == null ? null : stocktime.trim();
    }

    public Double getOill() {
        return oill;
    }

    public void setOill(Double oill) {
        this.oill = oill;
    }

    public Double getStandardl() {
        return standardl;
    }

    public void setStandardl(Double standardl) {
        this.standardl = standardl;
    }

    public Double getEmptyl() {
        return emptyl;
    }

    public void setEmptyl(Double emptyl) {
        this.emptyl = emptyl;
    }

    public Double getHeighttotal() {
        return heighttotal;
    }

    public void setHeighttotal(Double heighttotal) {
        this.heighttotal = heighttotal;
    }

    public Double getHeightwater() {
        return heightwater;
    }

    public void setHeightwater(Double heightwater) {
        this.heightwater = heightwater;
    }

    public Double getOiltemp() {
        return oiltemp;
    }

    public void setOiltemp(Double oiltemp) {
        this.oiltemp = oiltemp;
    }

    public Double getWaterl() {
        return waterl;
    }

    public void setWaterl(Double waterl) {
        this.waterl = waterl;
    }

    public Double getDensity() {
        return density;
    }

    public void setDensity(Double density) {
        this.density = density;
    }

    public Double getDensitystandard() {
        return densitystandard;
    }

    public void setDensitystandard(Double densitystandard) {
        this.densitystandard = densitystandard;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift == null ? null : shift.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus == null ? null : transtatus.trim();
    }
    public String getCardno() {return cardno;}

    public void setCardno(String cardno) {this.cardno = cardno;}

    @JsonProperty("checkmode")
    public String getBackcanflag() {
        return backmatchflag;
    }

    public void setBackcanflag(String backmatchflag) {
        this.backmatchflag = backmatchflag;
    }

    @Override
    public String toString() {
        return "DailyTradeInventory{" +
                "oilcan=" + oilcan +
                ", oilno='" + oilno + '\'' +
                ", cardno='" + cardno + '\'' +
                ", opetime=" + opetime +
                ", stockdate='" + stockdate + '\'' +
                ", stocktime='" + stocktime + '\'' +
                ", oill=" + oill +
                ", standardl=" + standardl +
                ", emptyl=" + emptyl +
                ", heighttotal=" + heighttotal +
                ", heightwater=" + heightwater +
                ", oiltemp=" + oiltemp +
                ", waterl=" + waterl +
                ", density=" + density +
                ", densitystandard=" + densitystandard +
                ", shift='" + shift + '\'' +
                ", remark='" + remark + '\'' +
                ", transtatus='" + transtatus + '\'' +
                ", backmatchflag='" + backmatchflag + '\'' +
                ", liter=" + liter +
                ", pumpno=" + pumpno +
                '}';
    }
}