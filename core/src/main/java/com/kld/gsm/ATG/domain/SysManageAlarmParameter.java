package com.kld.gsm.ATG.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * 预报警设置表
 */
public class SysManageAlarmParameter {
    private Integer oilcan;//油罐编号
    private Double lowprealarm;//低液位预警
    private Double lowalarm;//低液位报警
    private Double highprealarm;//高液位预警
    private Double highalarm;//高液位报警
    private Double wateralarm;//水杂报警
    private Double waterhightalarm;//高水位报警
    private Double hightempalarm;//高温报警
    private Double lowtempalarm;//低温报警
    private Date lastoptime;//上次设置时间
    private Date optime;//操作时间
    private String transtatus;//传输状态

    //盗油报警
    private Double stealoilalarm;

    //渗油报警
    private Double leakoilalarm;

    //漏油报警
    private Double leakageoilalarm;

    @JsonIgnore
    private Integer saleouttime;


    public Double getWaterhightalarm() {
        return waterhightalarm;
    }

    public void setWaterhightalarm(Double waterhightalarm) {
        this.waterhightalarm = waterhightalarm;
    }

    public Double getStealoilalarm() {
        return stealoilalarm;
    }

    public void setStealoilalarm(Double stealoilalarm) {
        this.stealoilalarm = stealoilalarm;
    }

    public Double getLeakoilalarm() {
        return leakoilalarm;
    }

    public void setLeakoilalarm(Double leakoilalarm) {
        this.leakoilalarm = leakoilalarm;
    }

    public Double getLeakageoilalarm() {
        return leakageoilalarm;
    }

    public void setLeakageoilalarm(Double leakageoilalarm) {
        this.leakageoilalarm = leakageoilalarm;
    }

    public void setSaleouttime(Integer saleouttime) {
        this.saleouttime = saleouttime;
    }

    public Integer getOilcan() {
        return oilcan;
    }

    public void setOilcan(Integer oilcan) {
        this.oilcan = oilcan;
    }

    public Double getLowprealarm() {
        return lowprealarm;
    }

    public void setLowprealarm(Double lowprealarm) {
        this.lowprealarm = lowprealarm;
    }

    public Double getLowalarm() {
        return lowalarm;
    }

    public void setLowalarm(Double lowalarm) {
        this.lowalarm = lowalarm;
    }

    public Double getHighprealarm() {
        return highprealarm;
    }

    public void setHighprealarm(Double highprealarm) {
        this.highprealarm = highprealarm;
    }

    public Double getHighalarm() {
        return highalarm;
    }

    public void setHighalarm(Double highalarm) {
        this.highalarm = highalarm;
    }

    public Double getWateralarm() {
        return wateralarm;
    }

    public void setWateralarm(Double wateralarm) {
        this.wateralarm = wateralarm;
    }

    public Double getHightempalarm() {
        return hightempalarm;
    }

    public void setHightempalarm(Double hightempalarm) {
        this.hightempalarm = hightempalarm;
    }

    public Double getLowtempalarm() {
        return lowtempalarm;
    }

    public void setLowtempalarm(Double lowtempalarm) {
        this.lowtempalarm = lowtempalarm;
    }

    public Date getLastoptime() {
        return lastoptime;
    }

    public void setLastoptime(Date lastoptime) {
        this.lastoptime = lastoptime;
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus == null ? null : transtatus.trim();
    }

    public int getSaleouttime() {
        return saleouttime;
    }

    public void setSaleouttime(int saleouttime) {
        this.saleouttime = saleouttime;
    }

    @Override
    public String toString() {
        return "SysManageAlarmParameter{" +
                "oilcan=" + oilcan +
                ", lowprealarm=" + lowprealarm +
                ", lowalarm=" + lowalarm +
                ", highprealarm=" + highprealarm +
                ", highalarm=" + highalarm +
                ", wateralarm=" + wateralarm +
                ", waterhightalarm=" + waterhightalarm +
                ", hightempalarm=" + hightempalarm +
                ", lowtempalarm=" + lowtempalarm +
                ", lastoptime=" + lastoptime +
                ", optime=" + optime +
                ", transtatus='" + transtatus + '\'' +
                ", stealoilalarm=" + stealoilalarm +
                ", leakoilalarm=" + leakoilalarm +
                ", leakageoilalarm=" + leakageoilalarm +
                ", saleouttime=" + saleouttime +
                '}';
    }
}