package com.kld.gsm.syntocenter.domain;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * Created by fangzhun on 2015/12/8.
 * 实时库存输出数据
 */
public class CanInfo {
     private String nodeno;

    private  Integer oilcan;

    private Integer oilno;

    private Date storetime;

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

    private Double densitiesstandard;

    private String version;

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno;
    }

    public Integer getOilcan() {
        return oilcan;
    }

    public void setOilcan(Integer oilcan) {
        this.oilcan = oilcan;
    }

    public Integer getOilno() {
        return oilno;
    }

    public void setOilno(Integer oilno) {
        this.oilno = oilno;
    }

    public Date getStoretime() {
        return storetime;
    }

    public void setStoretime(Date storetime) {
        this.storetime = storetime;
    }

    public Double getStandardl() {
        return standardl;
    }

    public void setStandardl(Double standardl) {
        DecimalFormat df=new DecimalFormat("#####.00");
        standardl=Double.parseDouble(df.format(standardl));
        this.standardl = standardl;
    }

    public Double getHeighttotal() {
        return heighttotal;
    }

    public void setHeighttotal(Double heighttotal) {
        DecimalFormat df=new DecimalFormat("#####.00");
        heighttotal=Double.parseDouble(df.format(heighttotal));
        this.heighttotal = heighttotal;
    }

    public Double getOill() {
        return oill;
    }

    public void setOill(Double oill) {
        DecimalFormat df=new DecimalFormat("#####.00");
        oill=Double.parseDouble(df.format(oill));
        this.oill = oill;
    }

    public Double getHeightwater() {
        return heightwater;
    }

    public void setHeightwater(Double heightwater) {
        DecimalFormat df=new DecimalFormat("#####.00");
        heightwater=Double.parseDouble(df.format(heightwater));
        this.heightwater = heightwater;
    }

    public Double getWaterl() {
        return waterl;
    }

    public void setWaterl(Double waterl) {
        DecimalFormat df=new DecimalFormat("#####.00");
        waterl=Double.parseDouble(df.format(waterl));
        this.waterl = waterl;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (waterl!=null){
            waterl=Double.parseDouble(df.format(waterl));
        }
        this.temperature = temperature;
    }

    public Double getVolumeempty() {
        return volumeempty;
    }

    public void setVolumeempty(Double volumeempty) {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (volumeempty!=null){
            volumeempty=Double.parseDouble(df.format(volumeempty));
        }
        this.volumeempty = volumeempty;
    }

    public Double getTemp1() {
        return temp1;
    }

    public void setTemp1(Double temp1) {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (temp1!=null){
            temp1=Double.parseDouble(df.format(temp1));
        }
        this.temp1 = temp1;
    }

    public Double getTemp2() {
        return temp2;
    }

    public void setTemp2(Double temp2) {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (temp2!=null){
            temp2=Double.parseDouble(df.format(temp2));
        }
        this.temp2 = temp2;
    }

    public Double getTemp3() {
        return temp3;
    }

    public void setTemp3(Double temp3) {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (temp3!=null){
            temp3=Double.parseDouble(df.format(temp3));
        }
        this.temp3 = temp3;
    }

    public Double getTemp4() {
        return temp4;
    }

    public void setTemp4(Double temp4) {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (temp4!=null) {
            temp4 = Double.parseDouble(df.format(temp4));
        }
        this.temp4 = temp4;
    }

    public Double getTemp5() {
        return temp5;
    }

    public void setTemp5(Double temp5) {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (temp5!=null) {
            temp5 = Double.parseDouble(df.format(temp5));
        }
        this.temp5 = temp5;
    }

    public Double getDensities() {
        return densities;
    }

    public void setDensities(Double densities) {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (densities!=null){
            densities=Double.parseDouble(df.format(densities));
        }
        this.densities = densities;
    }

    public Double getDensitiesstandard() {
        return densitiesstandard;
    }

    public void setDensitiesstandard(Double densitiesstandard) {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (densitiesstandard!=null) {
            densitiesstandard = Double.parseDouble(df.format(densitiesstandard));
        }
        this.densitiesstandard = densitiesstandard;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
