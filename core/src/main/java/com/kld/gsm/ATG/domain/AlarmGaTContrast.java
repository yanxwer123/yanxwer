package com.kld.gsm.ATG.domain;

import java.text.DecimalFormat;
import java.util.Date;

public class AlarmGaTContrast extends AlarmGaTContrastKey {
    private Double fristmeasurestore;

    private Date secodemeasuretime;

    private Double secodemeasurestore;

    private Double intervalsales;

    private String intervaltime;

    private Double difference;

    private String transtatus;

    private String shift;

    public Double getFristmeasurestore() {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (fristmeasurestore!=null){
            return Double.parseDouble(df.format(fristmeasurestore));
        }
        return fristmeasurestore;
    }

    public void setFristmeasurestore(Double fristmeasurestore) {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (fristmeasurestore!=null){
            fristmeasurestore=Double.parseDouble(df.format(fristmeasurestore));
        }
        this.fristmeasurestore = fristmeasurestore;
    }

    public Date getSecodemeasuretime() {
        return secodemeasuretime;
    }

    public void setSecodemeasuretime(Date secodemeasuretime) {
        this.secodemeasuretime = secodemeasuretime;
    }

    public Double getSecodemeasurestore() {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (secodemeasurestore!=null){
          return  Double.parseDouble(df.format(secodemeasurestore));
        }
        return secodemeasurestore;
    }

    public void setSecodemeasurestore(Double secodemeasurestore) {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (secodemeasurestore!=null){
            secodemeasurestore=Double.parseDouble(df.format(secodemeasurestore));
        }
        this.secodemeasurestore = secodemeasurestore;
    }

    public Double getIntervalsales() {
        return intervalsales;
    }

    public void setIntervalsales(Double intervalsales) {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (intervalsales!=null){
            intervalsales=Double.parseDouble(df.format(intervalsales));
        }
        this.intervalsales = intervalsales;
    }

    public String getIntervaltime() {
        return intervaltime;
    }

    public void setIntervaltime(String intervaltime) {
        this.intervaltime = intervaltime == null ? null : intervaltime.trim();
    }

    public Double getDifference() {
        return difference;
    }

    public void setDifference(Double difference) {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (difference!=null){
            difference=Double.parseDouble(df.format(difference));
        }
        this.difference = difference;
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus == null ? null : transtatus.trim();
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift == null ? null : shift.trim();
    }

    @Override
    public String toString() {
        return "AlarmGaTContrast{" +
                "fristmeasurestore=" + fristmeasurestore +
                ", secodemeasuretime=" + secodemeasuretime +
                ", secodemeasurestore=" + secodemeasurestore +
                ", intervalsales=" + intervalsales +
                ", intervaltime='" + intervaltime + '\'' +
                ", difference=" + difference +
                ", transtatus='" + transtatus + '\'' +
                ", shift='" + shift + '\'' +
                '}';
    }
}