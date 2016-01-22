package com.kld.gsm.center.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class oss_sysmanage_AlarmParameter extends oss_sysmanage_AlarmParameterKey {
    private Double lowprealarm;

    private Double lowalarm;

    private Double highprealarm;

    private Double highalarm;

    private Double wateralarm;

    private Double hightempalarm;

    private Double lowtempalarm;

    private Date lastoptime;

    private Date optime;

    private String transtatus;

    private String oucode;

    //高水位报警 不与数据库交互
    private Double waterhightalarm;

    //盗油报警 不与数据库交互
    private Double stealoilalarm;

    //渗油报警 不与数据库交互
    private Double leakoilalarm;

    //漏油报警 不与数据库交互
    private Double leakageoilalarm;


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

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode == null ? null : oucode.trim();
    }
}