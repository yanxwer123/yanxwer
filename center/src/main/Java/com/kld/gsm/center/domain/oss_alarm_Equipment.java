package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_alarm_Equipment extends oss_alarm_EquipmentKey {
    private Date endalarmtime;

    private String failuretype;

    private String equipcode;

    private String malfunctioncode;

    private String equipbrand;

    private String probemodel;

    private String remark;

    private String transtatus;

    private String shift;

    private String oucode;

    public Date getEndalarmtime() {
        return endalarmtime;
    }

    public void setEndalarmtime(Date endalarmtime) {
        this.endalarmtime = endalarmtime;
    }

    public String getFailuretype() {
        return failuretype;
    }

    public void setFailuretype(String failuretype) {
        this.failuretype = failuretype == null ? null : failuretype.trim();
    }

    public String getEquipcode() {
        return equipcode;
    }

    public void setEquipcode(String equipcode) {
        this.equipcode = equipcode == null ? null : equipcode.trim();
    }

    public String getMalfunctioncode() {
        return malfunctioncode;
    }

    public void setMalfunctioncode(String malfunctioncode) {
        this.malfunctioncode = malfunctioncode == null ? null : malfunctioncode.trim();
    }

    public String getEquipbrand() {
        return equipbrand;
    }

    public void setEquipbrand(String equipbrand) {
        this.equipbrand = equipbrand == null ? null : equipbrand.trim();
    }

    public String getProbemodel() {
        return probemodel;
    }

    public void setProbemodel(String probemodel) {
        this.probemodel = probemodel == null ? null : probemodel.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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