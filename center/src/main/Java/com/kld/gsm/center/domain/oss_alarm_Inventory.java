package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_alarm_Inventory extends oss_alarm_InventoryKey {
   private Date endtime;

    private String alarmdesc;

    private String transtatus;

    private String shift;

    private String oucode;

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getAlarmdesc() {
        return alarmdesc;
    }

    public void setAlarmdesc(String alarmdesc) {
        this.alarmdesc = alarmdesc == null ? null : alarmdesc.trim();
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

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode == null ? null : oucode.trim();
    }
}