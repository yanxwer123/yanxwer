package com.kld.gsm.ATG.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kld.gsm.Socket.uitls.NumberUtils;
import com.mchange.lang.DoubleUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

public class AlarmSaleOut extends AlarmSaleOutKey {
    private Double nowvolume;

    private Double cansalevolume;

    private Double dayaveragesales;

    private Double houraveragesales;

    private Double predictsales;

    private String transtatus;

    private Date startalarmtime;

    private Date endalarmtime;

    private String shift;

    private double PredictHours;

    public double getPredictHours() {
        return PredictHours;
    }

    public void setPredictHours(double predictHours) {

        BigDecimal b   =   new   BigDecimal(predictHours);
        double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
        PredictHours =  f1;

               // predictHours;
    }

    public Double getNowvolume() {

        return nowvolume;
    }

    public void setNowvolume(Double nowvolume) {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (nowvolume!=null){
            nowvolume=Double.parseDouble(df.format(nowvolume));
        }
        this.nowvolume = nowvolume;
    }

    public Double getCansalevolume() {
        return cansalevolume;
    }

    public void setCansalevolume(Double cansalevolume) {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (cansalevolume!=null){
            cansalevolume=Double.parseDouble(df.format(cansalevolume));
        }
        this.cansalevolume = cansalevolume;
    }

    public Double getDayaveragesales() {
        return dayaveragesales;
    }

    public void setDayaveragesales(Double dayaveragesales) {
        this.dayaveragesales = dayaveragesales;
    }

    public Double getHouraveragesales() {
        return houraveragesales;
    }

    public void setHouraveragesales(Double houraveragesales) {
        this.houraveragesales = houraveragesales;
    }

    public Double getPredictsales() {
        return predictsales;
    }

    public void setPredictsales(Double predictsales) {
        this.predictsales = predictsales;
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus == null ? null : transtatus.trim();
    }

    public Date getStartalarmtime() {
        return startalarmtime;
    }

    public void setStartalarmtime(Date startalarmtime) {
        this.startalarmtime = startalarmtime;
    }

    public Date getEndalarmtime() {
        return endalarmtime;
    }

    public void setEndalarmtime(Date endalarmtime) {
        this.endalarmtime = endalarmtime;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift == null ? null : shift.trim();
    }

    @Override
    public String toString() {
        return "AlarmSaleOut{" +
                "nowvolume=" + nowvolume +
                ", cansalevolume=" + cansalevolume +
                ", dayaveragesales=" + dayaveragesales +
                ", houraveragesales=" + houraveragesales +
                ", predictsales=" + predictsales +
                ", transtatus='" + transtatus + '\'' +
                ", startalarmtime=" + startalarmtime +
                ", endalarmtime=" + endalarmtime +
                ", shift='" + shift + '\'' +
                ", PredictHours=" + PredictHours +
                '}';
    }
}