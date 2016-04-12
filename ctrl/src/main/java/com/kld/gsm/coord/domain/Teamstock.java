package com.kld.gsm.coord.domain;

import java.util.Date;

/**
 * Created by yinzhiguang on 2015/11/9.
 */
public class Teamstock extends AbsValueBean{
    private int oilcanno;
    private String teamvouchno;
    private Date opetime;
    private String stockdate;
    private String stocktime;
    private String oilno;
    private double oilcubage;
    private double oilstandcubage;
    private double emptycubage;
    private double totalheight;
    private double waterheight;
    private double oiltemp;
    private double waterbulk;
    private double apparentdensity;
    private double standdensity;
    private String oilinflag;
    private String tranflag;
    private String remark;

    public int getOilcanno() {
        return oilcanno;
    }

    public void setOilcanno(int oilcanno) {
        this.oilcanno = oilcanno;
    }

    public String getTeamvouchno() {
        return teamvouchno;
    }

    public void setTeamvouchno(String teamvouchno) {
        this.teamvouchno = teamvouchno;
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

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno;
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

    public String getOilinflag() {
        return oilinflag;
    }

    public void setOilinflag(String oilinflag) {
        this.oilinflag = oilinflag;
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
        return "Teamstock{" +
                "oilcanno=" + oilcanno +
                ", teamvouchno='" + teamvouchno + '\'' +
                ", opetime=" + opetime +
                ", stockdate='" + stockdate + '\'' +
                ", stocktime='" + stocktime + '\'' +
                ", oilno='" + oilno + '\'' +
                ", oilcubage=" + oilcubage +
                ", oilstandcubage=" + oilstandcubage +
                ", emptycubage=" + emptycubage +
                ", totalheight=" + totalheight +
                ", waterheight=" + waterheight +
                ", oiltemp=" + oiltemp +
                ", waterbulk=" + waterbulk +
                ", apparentdensity=" + apparentdensity +
                ", standdensity=" + standdensity +
                ", oilinflag='" + oilinflag + '\'' +
                ", tranflag='" + tranflag + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
