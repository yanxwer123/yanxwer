package com.kld.gsm.ATG.domain;

import org.junit.Test;

import java.text.DecimalFormat;
import java.util.Date;

public class MonitorTimeInventory extends MonitorTimeInventoryKey {
    private String oilno;

    private Double standardl;

    private Double heighttotal;

    private Double oill;

    private Double heightwater;

    private Double waterl;

    private Double temperature;

    private Double volumeempty;

    private Double temp1;

    private Double temp2;

    private Double temp3;

    private Double temp4;

    private Double temp5;

    private Double densities;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    private Double densitiesstandard;

    private String transtatus;

    private Date createtime;

    private String shift;

    private String version;

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno == null ? null : oilno.trim();
    }

    public Double getStandardl() {
        return standardl;
    }

    public void setStandardl(Double standardl) {
        DecimalFormat df   = new DecimalFormat("######0.00");
        if (standardl!=null){
            standardl=Double.parseDouble(df.format(standardl));
        }
        this.standardl = standardl;
    }

    public Double getHeighttotal() {
        return heighttotal;
    }

    public void setHeighttotal(Double heighttotal) {
        DecimalFormat df   = new DecimalFormat("######0.00");
        if (heighttotal!=null){
            heighttotal=Double.parseDouble(df.format(heighttotal));
        }
        this.heighttotal = heighttotal;
    }

    public Double getOill() {
        DecimalFormat decimalFormat = new DecimalFormat("######0.00");
        return oill == null ? 0.00 : Double.parseDouble(decimalFormat.format(oill));
    }

    public void setOill(Double oill) {
        this.oill = oill;
    }

    public Double getHeightwater() {
        return heightwater;
    }

    public void setHeightwater(Double heightwater) {
        DecimalFormat df   = new DecimalFormat("######0.00");
        if (heightwater!=null){
            heightwater=Double.parseDouble(df.format(heightwater));
        }
        this.heightwater = heightwater;
    }

    public Double getWaterl() {
        DecimalFormat decimalFormat = new DecimalFormat("######0.00");
        return waterl == null ? 0.00 : Double.parseDouble(decimalFormat.format(waterl));
    }

    public void setWaterl(Double waterl) {
        DecimalFormat df   = new DecimalFormat("######0.00");
        if (waterl!=null){
            waterl=Double.parseDouble(df.format(waterl));
        }
        this.waterl = waterl;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        DecimalFormat df   = new DecimalFormat("######0.00");
        if (temperature!=null){
            temperature=Double.parseDouble(df.format(temperature));
        }
        this.temperature = temperature;
    }

    public Double getVolumeempty() {
        DecimalFormat decimalFormat = new DecimalFormat("######0.00");
        return volumeempty == null ? 0.00 : Double.parseDouble(decimalFormat.format(volumeempty));
    }

    public void setVolumeempty(Double volumeempty) {
        this.volumeempty = volumeempty;
    }

    public Double getTemp1() {
        return temp1;
    }

    public void setTemp1(Double temp1) {
        DecimalFormat df   = new DecimalFormat("######0.00");
        if (temp1!=null){
            temp1=Double.parseDouble(df.format(temp1));
        }
        this.temp1 = temp1;
    }

    public Double getTemp2() {
        return temp2;
    }

    public void setTemp2(Double temp2) {
        DecimalFormat df   = new DecimalFormat("######0.00");
        if (temp2!=null){
            temp2=Double.parseDouble(df.format(temp2));
        }
        this.temp2 = temp2;
    }

    public Double getTemp3() {
        return temp3;
    }

    public void setTemp3(Double temp3) {
        DecimalFormat df   = new DecimalFormat("######0.00");
        if (temp3!=null){
            temp3=Double.parseDouble(df.format(temp3));
        }
        this.temp3 = temp3;
    }

    public Double getTemp4() {
        return temp4;
    }

    public void setTemp4(Double temp4) {
        DecimalFormat df   = new DecimalFormat("######0.00");
        if (temp4!=null){
            temp4=Double.parseDouble(df.format(temp4));
        }
        this.temp4 = temp4;
    }

    public Double getTemp5() {
        return temp5;
    }

    public void setTemp5(Double temp5) {
        DecimalFormat df   = new DecimalFormat("######0.00");
        if (temp5!=null){
            temp5=Double.parseDouble(df.format(temp5));
        }
        this.temp5 = temp5;
    }

    public Double getDensities() {
        return densities;
    }

    public void setDensities(Double densities) {
        DecimalFormat df   = new DecimalFormat("######0.00");
        if (densities!=null){
            densities=Double.parseDouble(df.format(densities));
        }
        this.densities = densities;
    }

    public Double getDensitiesstandard() {
        return densitiesstandard;
    }

    public void setDensitiesstandard(Double densitiesstandard) {
        DecimalFormat df   = new DecimalFormat("######0.00");
        if (densitiesstandard!=null){
            densitiesstandard=Double.parseDouble(df.format(densitiesstandard));
        }
        this.densitiesstandard = densitiesstandard;
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus == null ? null : transtatus.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift == null ? null : shift.trim();
    }

    @Test
    public void a() {
        MonitorTimeInventory aa = new MonitorTimeInventory();
        aa.setTranstatus("");
        aa.setOill(2.2251231322);
        //System.out.println(aa.getOill());
    }

    @Override
    public String toString() {
        return "MonitorTimeInventory{" +
                "oilno='" + oilno + '\'' +
                ", standardl=" + standardl +
                ", heighttotal=" + heighttotal +
                ", oill=" + oill +
                ", heightwater=" + heightwater +
                ", waterl=" + waterl +
                ", temperature=" + temperature +
                ", volumeempty=" + volumeempty +
                ", temp1=" + temp1 +
                ", temp2=" + temp2 +
                ", temp3=" + temp3 +
                ", temp4=" + temp4 +
                ", temp5=" + temp5 +
                ", densities=" + densities +
                ", densitiesstandard=" + densitiesstandard +
                ", transtatus='" + transtatus + '\'' +
                ", createtime=" + createtime +
                ", shift='" + shift + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}