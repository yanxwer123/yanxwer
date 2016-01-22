package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_daily_StationShiftInfo extends oss_daily_StationShiftInfoKey {
    private String successor;

    private Date succeedtime;

    private String shiftoperator;

    private Date shifttime;

    private String transtatus;

    private String oucode;

    public String getSuccessor() {
        return successor;
    }

    public void setSuccessor(String successor) {
        this.successor = successor == null ? null : successor.trim();
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

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode == null ? null : oucode.trim();
    }
}