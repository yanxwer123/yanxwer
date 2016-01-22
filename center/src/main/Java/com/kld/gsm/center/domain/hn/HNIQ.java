package com.kld.gsm.center.domain.hn;

/*
Created BY niyang
Created Date 2015/11/21
description 湖南接口液位仪预警阀值实体类
*/
public class HNIQ {

    private String nodeNo;

    private Integer oilcan;

    private Double lowPreAlarm;

    private Double lowAlarm;

    private Double highPreAlarm;

    private Double highAlarm;

    private Double waterAlarm;

    private Double highTempAlarm;

    private Double lowTempAlarm;

    public String getNodeNo() {
        return nodeNo;
    }

    public void setNodeNo(String nodeNo) {
        this.nodeNo = nodeNo;
    }

    public Integer getOilcan() {
        return oilcan;
    }

    public void setOilcan(Integer oilcan) {
        this.oilcan = oilcan;
    }

    public Double getLowPreAlarm() {
        return lowPreAlarm;
    }

    public void setLowPreAlarm(Double lowPreAlarm) {
        this.lowPreAlarm = lowPreAlarm;
    }

    public Double getLowAlarm() {
        return lowAlarm;
    }

    public void setLowAlarm(Double lowAlarm) {
        this.lowAlarm = lowAlarm;
    }

    public Double getHighPreAlarm() {
        return highPreAlarm;
    }

    public void setHighPreAlarm(Double highPreAlarm) {
        this.highPreAlarm = highPreAlarm;
    }

    public Double getHighAlarm() {
        return highAlarm;
    }

    public void setHighAlarm(Double highAlarm) {
        this.highAlarm = highAlarm;
    }

    public Double getWaterAlarm() {
        return waterAlarm;
    }

    public void setWaterAlarm(Double waterAlarm) {
        this.waterAlarm = waterAlarm;
    }

    public Double getHighTempAlarm() {
        return highTempAlarm;
    }

    public void setHighTempAlarm(Double highTempAlarm) {
        this.highTempAlarm = highTempAlarm;
    }

    public Double getLowTempAlarm() {
        return lowTempAlarm;
    }

    public void setLowTempAlarm(Double lowTempAlarm) {
        this.lowTempAlarm = lowTempAlarm;
    }
}
