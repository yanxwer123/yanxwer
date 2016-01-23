package com.kld.gsm.ATG.domain;


import java.text.DecimalFormat;
import java.util.Date;

public class AcceptanceOdRegisterInfo extends AcceptanceOdRegisterInfoKey {
    private String oilno;

    private String shift;

    private String deliveryno;

    private Date begintime;

    private Date endtime;

    private Double dischargel;

    private Double beginoilheight;

    private Double beginoill;

    private Double beginv20l;

    private Double begintemperature;

    private Double endoilheight;

    private Double endoill;

    private Double endv20l;

    private Double endtemperature;

    private Date stablebegintime;

    private Date stableendtime;

    private Date createtime;

    private Integer isdel;

    private Integer entertype;

    private Double duringsales;

    private String transtatus;

    private Integer forcecancelstable;

    private Integer isbeforestable;

    private Double beginwaterheight;

    private Double endwaterheight;

    public String getDeliveryno() {
        return deliveryno;
    }

    public void setDeliveryno(String deliveryno) {
        this.deliveryno = deliveryno;
    }

    public Integer getForcecancelstable() {
        return forcecancelstable;
    }

    public void setForcecancelstable(Integer forcecancelstable) {
        this.forcecancelstable = forcecancelstable;
    }

    public Integer getIsbeforestable() {
        return isbeforestable;
    }

    public void setIsbeforestable(Integer isbeforestable) {
        this.isbeforestable = isbeforestable;
    }

    public Date getStableendtime() {
        return stableendtime;
    }

    public void setStableendtime(Date stableendtime) {
        this.stableendtime = stableendtime;
    }

    public Date getStablebegintime() {
        return stablebegintime;
    }

    public void setStablebegintime(Date stablebegintime) {
        this.stablebegintime = stablebegintime;
    }

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno == null ? null : oilno.trim();
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift == null ? null : shift.trim();
    }

    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Double getDischargel() {
        return dischargel;
    }

    public void setDischargel(Double dischargel) {
        DecimalFormat df   = new DecimalFormat("######0.00");
        if (dischargel!=null){
            dischargel=Double.parseDouble(df.format(dischargel));
        }
        this.dischargel = dischargel;
    }

    public Double getBeginoilheight() {
        return beginoilheight;
    }

    public void setBeginoilheight(Double beginoilheight) {
        this.beginoilheight = beginoilheight;
    }

    public Double getBeginoill() {
        return beginoill;
    }

    public void setBeginoill(Double beginoill) {
        this.beginoill = beginoill;
    }

    public Double getBeginv20l() {
        return beginv20l;
    }

    public void setBeginv20l(Double beginv20l) {
        this.beginv20l = beginv20l;
    }

    public Double getBegintemperature() {
        return begintemperature;
    }

    public void setBegintemperature(Double begintemperature) {
        this.begintemperature = begintemperature;
    }

    public Double getEndoilheight() {
        return endoilheight;
    }

    public void setEndoilheight(Double endoilheight) {
        this.endoilheight = endoilheight;
    }

    public Double getEndoill() {
        return endoill;
    }

    public void setEndoill(Double endoill) {
        this.endoill = endoill;
    }

    public Double getEndv20l() {
        return endv20l;
    }

    public void setEndv20l(Double endv20l) {
        this.endv20l = endv20l;
    }

    public Double getEndtemperature() {
        return endtemperature;
    }

    public void setEndtemperature(Double endtemperature) {
        this.endtemperature = endtemperature;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public Integer getEntertype() {
        return entertype;
    }

    public void setEntertype(Integer entertype) {
        this.entertype = entertype;
    }

    public Double getDuringsales() {
        return duringsales;
    }

    public void setDuringsales(Double duringsales) {
        DecimalFormat df   = new DecimalFormat("######0.00");
        if (duringsales!=null){
            duringsales=Double.parseDouble(df.format(duringsales));
        }
        this.duringsales = duringsales;
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus == null ? null : transtatus.trim();
    }

    public Double getBeginwaterheight() {
        return beginwaterheight;
    }

    public void setBeginwaterheight(Double beginwaterheight) {
        this.beginwaterheight = beginwaterheight;
    }

    public Double getEndwaterheight() {
        return endwaterheight;
    }

    public void setEndwaterheight(Double endwaterheight) {
        this.endwaterheight = endwaterheight;
    }
}