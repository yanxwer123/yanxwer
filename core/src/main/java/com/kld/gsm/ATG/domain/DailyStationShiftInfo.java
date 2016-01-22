package com.kld.gsm.ATG.domain;

import com.kld.gsm.util.SybaseUtils;

import java.util.Date;

public class DailyStationShiftInfo {
    private Integer shift;

    private String successor;

    private Date succeedtime;

    private String shiftoperator;

    private Date shifttime;

    private String transtatus;

    public Integer getShift() {
        return shift;
    }

    public void setShift(Integer shift) {
        this.shift = shift;
    }

    public String getSuccessor() {
        return  successor;
    }

    public void setSuccessor(String successor) {
        this.successor = successor;
    }

    public Date getSucceedtime() {
        return succeedtime;
    }

    public void setSucceedtime(Date succeedtime) {
        this.succeedtime = succeedtime;
    }

    public String getShiftoperator() {
        return shiftoperator;
    }

    public void setShiftoperator(String shiftoperator) {
        this.shiftoperator = shiftoperator == null ? null : shiftoperator.trim();
    }

    public Date getShifttime() {
        return shifttime;
    }

    public void setShifttime(Date shifttime) {
        this.shifttime = shifttime;
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {

        this.transtatus = transtatus == null ? null : transtatus.trim();
    }

    @Override
    public String toString() {
        return "DailyStationShiftInfo{" +
                "shift=" + shift +
                ", successor='" + successor + '\'' +
                ", succeedtime=" + succeedtime +
                ", shiftoperator='" + shiftoperator + '\'' +
                ", shifttime=" + shifttime +
                ", transtatus='" + transtatus + '\'' +
                '}';
    }
}