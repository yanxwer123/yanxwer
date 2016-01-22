package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_daily_ShiftStock extends oss_daily_ShiftStockKey {
    private Date opetime;

    private String stockdate;

    private String stocktime;

    private String oilno;

    private Double oilcubage;

    private Double oilstandcubage;

    private Double emptycubage;

    private Double totalheight;

    private Double waterheight;

    private Double oiltemp;

    private Double waterbulk;

    private Double apparentdensity;

    private Double standdensity;

    private String oilinflag;

    private String tranflag;

    private String remark;

    private String oucode;

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

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno == null ? null : oilno.trim();
    }

    public Double getOilcubage() {
        return oilcubage;
    }

    public void setOilcubage(Double oilcubage) {
        this.oilcubage = oilcubage;
    }

    public Double getOilstandcubage() {
        return oilstandcubage;
    }

    public void setOilstandcubage(Double oilstandcubage) {
        this.oilstandcubage = oilstandcubage;
    }

    public Double getEmptycubage() {
        return emptycubage;
    }

    public void setEmptycubage(Double emptycubage) {
        this.emptycubage = emptycubage;
    }

    public Double getTotalheight() {
        return totalheight;
    }

    public void setTotalheight(Double totalheight) {
        this.totalheight = totalheight;
    }

    public Double getWaterheight() {
        return waterheight;
    }

    public void setWaterheight(Double waterheight) {
        this.waterheight = waterheight;
    }

    public Double getOiltemp() {
        return oiltemp;
    }

    public void setOiltemp(Double oiltemp) {
        this.oiltemp = oiltemp;
    }

    public Double getWaterbulk() {
        return waterbulk;
    }

    public void setWaterbulk(Double waterbulk) {
        this.waterbulk = waterbulk;
    }

    public Double getApparentdensity() {
        return apparentdensity;
    }

    public void setApparentdensity(Double apparentdensity) {
        this.apparentdensity = apparentdensity;
    }

    public Double getStanddensity() {
        return standdensity;
    }

    public void setStanddensity(Double standdensity) {
        this.standdensity = standdensity;
    }

    public String getOilinflag() {
        return oilinflag;
    }

    public void setOilinflag(String oilinflag) {
        this.oilinflag = oilinflag == null ? null : oilinflag.trim();
    }

    public String getTranflag() {
        return tranflag;
    }

    public void setTranflag(String tranflag) {
        this.tranflag = tranflag == null ? null : tranflag.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode == null ? null : oucode.trim();
    }
}