package com.kld.gsm.center.domain;

import java.util.Date;

/**
 * Created by fangzhun on 2015/12/8.
 * 实时库存输出数据
 */
public class atg_stock_data_out_t {
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
        this.standardl = standardl;
    }

    public Double getHeighttotal() {
        return heighttotal;
    }

    public void setHeighttotal(Double heighttotal) {
        this.heighttotal = heighttotal;
    }

    public Double getOill() {
        return oill;
    }

    public void setOill(Double oill) {
        this.oill = oill;
    }

    public Double getHeightwater() {
        return heightwater;
    }

    public void setHeightwater(Double heightwater) {
        this.heightwater = heightwater;
    }

    public Double getWaterl() {
        return waterl;
    }

    public void setWaterl(Double waterl) {
        this.waterl = waterl;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getVolumeempty() {
        return volumeempty;
    }

    public void setVolumeempty(Double volumeempty) {
        this.volumeempty = volumeempty;
    }

    public Double getTemp1() {
        return temp1;
    }

    public void setTemp1(Double temp1) {
        this.temp1 = temp1;
    }

    public Double getTemp2() {
        return temp2;
    }

    public void setTemp2(Double temp2) {
        this.temp2 = temp2;
    }

    public Double getTemp3() {
        return temp3;
    }

    public void setTemp3(Double temp3) {
        this.temp3 = temp3;
    }

    public Double getTemp4() {
        return temp4;
    }

    public void setTemp4(Double temp4) {
        this.temp4 = temp4;
    }

    public Double getTemp5() {
        return temp5;
    }

    public void setTemp5(Double temp5) {
        this.temp5 = temp5;
    }

    public Double getDensities() {
        return densities;
    }

    public void setDensities(Double densities) {
        this.densities = densities;
    }

    public Double getDensitiesstandard() {
        return densitiesstandard;
    }

    public void setDensitiesstandard(Double densitiesstandard) {
        this.densitiesstandard = densitiesstandard;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
