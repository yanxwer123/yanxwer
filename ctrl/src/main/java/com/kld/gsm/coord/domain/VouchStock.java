package com.kld.gsm.coord.domain;

import java.util.Date;

/**
 * Created by yinzhiguang on 2015/11/5.
 */
public class VouchStock {
    private int macno;
    private int ttc;
    private Date takedate;
    private String oilgunno;
    private int oilcanno;
    private String oilno;
    private Date opetime;
    private String stockdate;
    private String stocktime;
    private double oilcubage;
    private double oilstandcubage;
    private double emptycubage;
    private double totalheight;
    private double waterheight;
    private double oiltemp;
    private double waterbulk;
    private double apparentdensity;
    private double standdensity;
    private String teamvouchno;
    private String tranflag;
    private String remark;

    public int getMacno() {
        return macno;
    }

    public void setMacno(int macno) {
        this.macno = macno;
    }

    public int getTtc() {
        return ttc;
    }

    public void setTtc(int ttc) {
        this.ttc = ttc;
    }

    public Date getTakedate() {
        return takedate;
    }

    public void setTakedate(Date takedate) {
        this.takedate = takedate;
    }

    public String getOilgunno() {
        return oilgunno;
    }

    public void setOilgunno(String oilgunno) {
        this.oilgunno = oilgunno;
    }

    public int getOilcanno() {
        return oilcanno;
    }

    public void setOilcanno(int oilcanno) {
        this.oilcanno = oilcanno;
    }

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno;
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
        this.stockdate = stockdate;
    }

    public String getStocktime() {
        return stocktime;
    }

    public void setStocktime(String stocktime) {
        this.stocktime = stocktime;
    }

    public double getOilcubage() {
        return oilcubage;
    }

    public void setOilcubage(double oilcubage) {
        this.oilcubage = oilcubage;
    }

    public double getOilstandcubage() {
        return oilstandcubage;
    }

    public void setOilstandcubage(double oilstandcubage) {
        this.oilstandcubage = oilstandcubage;
    }

    public double getEmptycubage() {
        return emptycubage;
    }

    public void setEmptycubage(double emptycubage) {
        this.emptycubage = emptycubage;
    }

    public double getTotalheight() {
        return totalheight;
    }

    public void setTotalheight(double totalheight) {
        this.totalheight = totalheight;
    }

    public double getWaterheight() {
        return waterheight;
    }

    public void setWaterheight(double waterheight) {
        this.waterheight = waterheight;
    }

    public double getOiltemp() {
        return oiltemp;
    }

    public void setOiltemp(double oiltemp) {
        this.oiltemp = oiltemp;
    }

    public double getWaterbulk() {
        return waterbulk;
    }

    public void setWaterbulk(double waterbulk) {
        this.waterbulk = waterbulk;
    }

    public double getApparentdensity() {
        return apparentdensity;
    }

    public void setApparentdensity(double apparentdensity) {
        this.apparentdensity = apparentdensity;
    }

    public double getStanddensity() {
        return standdensity;
    }

    public void setStanddensity(double standdensity) {
        this.standdensity = standdensity;
    }

    public String getTeamvouchno() {
        return teamvouchno;
    }

    public void setTeamvouchno(String teamvouchno) {
        this.teamvouchno = teamvouchno;
    }

    public String getTranflag() {
        return tranflag;
    }

    public void setTranflag(String tranflag) {
        this.tranflag = tranflag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "VouchStock{" +
                "macno=" + macno +
                ", ttc=" + ttc +
                ", takedate=" + takedate +
                ", oilgunno='" + oilgunno + '\'' +
                ", oilcanno=" + oilcanno +
                ", oilno='" + oilno + '\'' +
                ", opetime=" + opetime +
                ", stockdate='" + stockdate + '\'' +
                ", stocktime='" + stocktime + '\'' +
                ", oilcubage=" + oilcubage +
                ", oilstandcubage=" + oilstandcubage +
                ", emptycubage=" + emptycubage +
                ", totalheight=" + totalheight +
                ", waterheight=" + waterheight +
                ", oiltemp=" + oiltemp +
                ", waterbulk=" + waterbulk +
                ", apparentdensity=" + apparentdensity +
                ", standdensity=" + standdensity +
                ", teamvouchno='" + teamvouchno + '\'' +
                ", tranflag='" + tranflag + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}