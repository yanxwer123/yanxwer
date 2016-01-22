package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_alarm_measureLeak extends oss_alarm_measureLeakKey {
    private String revealstatus;

    private String revealrate;

    private Double startoilheight;

    private Double startwaterheight;

    private Double startoiltemp1;

    private Double startoiltemp2;

    private Double startoiltemp3;

    private Double startoiltemp4;

    private Double startoiltemp5;

    private Date enddate;

    private Double endoilheight;

    private Double endwaterheight;

    private Double endoiltemp1;

    private Double endoiltemp2;

    private Double endoiltemp3;

    private Double endoiltemp4;

    private Double endoiltemp5;

    private String transtatus;

    private String shift;

    private String oucode;

    private Double startoill;

    private Double startwaterl;

    private Double endoill;

    private Double endwaterl;

    private Integer time;

    private Integer valid;

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public String getRevealstatus() {
        return revealstatus;
    }

    public void setRevealstatus(String revealstatus) {
        this.revealstatus = revealstatus == null ? null : revealstatus.trim();
    }

    public String getRevealrate() {
        return revealrate;
    }

    public void setRevealrate(String revealrate) {
        this.revealrate = revealrate == null ? null : revealrate.trim();
    }

    public Double getStartoilheight() {
        return startoilheight;
    }

    public void setStartoilheight(Double startoilheight) {
        this.startoilheight = startoilheight;
    }

    public Double getStartwaterheight() {
        return startwaterheight;
    }

    public void setStartwaterheight(Double startwaterheight) {
        this.startwaterheight = startwaterheight;
    }

    public Double getStartoiltemp1() {
        return startoiltemp1;
    }

    public void setStartoiltemp1(Double startoiltemp1) {
        this.startoiltemp1 = startoiltemp1;
    }

    public Double getStartoiltemp2() {
        return startoiltemp2;
    }

    public void setStartoiltemp2(Double startoiltemp2) {
        this.startoiltemp2 = startoiltemp2;
    }

    public Double getStartoiltemp3() {
        return startoiltemp3;
    }

    public void setStartoiltemp3(Double startoiltemp3) {
        this.startoiltemp3 = startoiltemp3;
    }

    public Double getStartoiltemp4() {
        return startoiltemp4;
    }

    public void setStartoiltemp4(Double startoiltemp4) {
        this.startoiltemp4 = startoiltemp4;
    }

    public Double getStartoiltemp5() {
        return startoiltemp5;
    }

    public void setStartoiltemp5(Double startoiltemp5) {
        this.startoiltemp5 = startoiltemp5;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Double getEndoilheight() {
        return endoilheight;
    }

    public void setEndoilheight(Double endoilheight) {
        this.endoilheight = endoilheight;
    }

    public Double getEndwaterheight() {
        return endwaterheight;
    }

    public void setEndwaterheight(Double endwaterheight) {
        this.endwaterheight = endwaterheight;
    }

    public Double getEndoiltemp1() {
        return endoiltemp1;
    }

    public void setEndoiltemp1(Double endoiltemp1) {
        this.endoiltemp1 = endoiltemp1;
    }

    public Double getEndoiltemp2() {
        return endoiltemp2;
    }

    public void setEndoiltemp2(Double endoiltemp2) {
        this.endoiltemp2 = endoiltemp2;
    }

    public Double getEndoiltemp3() {
        return endoiltemp3;
    }

    public void setEndoiltemp3(Double endoiltemp3) {
        this.endoiltemp3 = endoiltemp3;
    }

    public Double getEndoiltemp4() {
        return endoiltemp4;
    }

    public void setEndoiltemp4(Double endoiltemp4) {
        this.endoiltemp4 = endoiltemp4;
    }

    public Double getEndoiltemp5() {
        return endoiltemp5;
    }

    public void setEndoiltemp5(Double endoiltemp5) {
        this.endoiltemp5 = endoiltemp5;
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

    public Double getStartoill() {
        return startoill;
    }

    public void setStartoill(Double startoill) {
        this.startoill = startoill;
    }

    public Double getStartwaterl() {
        return startwaterl;
    }

    public void setStartwaterl(Double startwaterl) {
        this.startwaterl = startwaterl;
    }

    public Double getEndoill() {
        return endoill;
    }

    public void setEndoill(Double endoill) {
        this.endoill = endoill;
    }

    public Double getEndwaterl() {
        return endwaterl;
    }

    public void setEndwaterl(Double endwaterl) {
        this.endwaterl = endwaterl;
    }
}