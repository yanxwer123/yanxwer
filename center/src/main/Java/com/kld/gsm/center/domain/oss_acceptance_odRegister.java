package com.kld.gsm.center.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class oss_acceptance_odRegister extends oss_acceptance_odRegisterKey {
    private String oucode;

    private String oilno;

    private String shift;

    private String deliveryno;

    private Double dischargeloss;

    @JsonProperty("dischargeLossV20")
    private Double dischargelossv20;

    private Double dischargerate;

    @JsonProperty("dischargeRateV20")
    private Double dischargeratev20;

    private Double planl;

    private Double realreceivel;

    private Double heightempey;

    private Double heighttotal;

    private Double heightwater;

    private Double cubagetable;

    private String plumbunbankoperator;

    private String calculateoperator;

    private String backbankno;

    private Double cantrucktemp;

    private Integer isfulldose;

    private Integer servicelevel;

    private Date createtime;

    private Integer isdel;

    @JsonProperty("realGetLV20")
    private Double realgetlv20;

    private Double realgetl;

    private Double duringsales;

    private String transtatus;

    private Date instationtime;

    private Double indemnityloss;

    private Date begintime;

    private Date endtime;

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

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode == null ? null : oucode.trim();
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

    public String getDeliveryno() {
        return deliveryno;
    }

    public void setDeliveryno(String deliveryno) {
        this.deliveryno = deliveryno == null ? null : deliveryno.trim();
    }

    public Double getDischargeloss() {
        return dischargeloss;
    }

    public void setDischargeloss(Double dischargeloss) {
        this.dischargeloss = dischargeloss;
    }

    public Double getDischargelossv20() {
        return dischargelossv20;
    }

    public void setDischargelossv20(Double dischargelossv20) {
        this.dischargelossv20 = dischargelossv20;
    }

    public Double getDischargerate() {
        return dischargerate;
    }

    public void setDischargerate(Double dischargerate) {
        this.dischargerate = dischargerate;
    }

    public Double getDischargeratev20() {
        return dischargeratev20;
    }

    public void setDischargeratev20(Double dischargeratev20) {
        this.dischargeratev20 = dischargeratev20;
    }

    public Double getPlanl() {
        return planl;
    }

    public void setPlanl(Double planl) {
        this.planl = planl;
    }

    public Double getRealreceivel() {
        return realreceivel;
    }

    public void setRealreceivel(Double realreceivel) {
        this.realreceivel = realreceivel;
    }

    public Double getHeightempey() {
        return heightempey;
    }

    public void setHeightempey(Double heightempey) {
        this.heightempey = heightempey;
    }

    public Double getHeighttotal() {
        return heighttotal;
    }

    public void setHeighttotal(Double heighttotal) {
        this.heighttotal = heighttotal;
    }

    public Double getHeightwater() {
        return heightwater;
    }

    public void setHeightwater(Double heightwater) {
        this.heightwater = heightwater;
    }

    public Double getCubagetable() {
        return cubagetable;
    }

    public void setCubagetable(Double cubagetable) {
        this.cubagetable = cubagetable;
    }

    public String getPlumbunbankoperator() {
        return plumbunbankoperator;
    }

    public void setPlumbunbankoperator(String plumbunbankoperator) {
        this.plumbunbankoperator = plumbunbankoperator == null ? null : plumbunbankoperator.trim();
    }

    public String getCalculateoperator() {
        return calculateoperator;
    }

    public void setCalculateoperator(String calculateoperator) {
        this.calculateoperator = calculateoperator == null ? null : calculateoperator.trim();
    }

    public String getBackbankno() {
        return backbankno;
    }

    public void setBackbankno(String backbankno) {
        this.backbankno = backbankno == null ? null : backbankno.trim();
    }

    public Double getCantrucktemp() {
        return cantrucktemp;
    }

    public void setCantrucktemp(Double cantrucktemp) {
        this.cantrucktemp = cantrucktemp;
    }

    public Integer getIsfulldose() {
        return isfulldose;
    }

    public void setIsfulldose(Integer isfulldose) {
        this.isfulldose = isfulldose;
    }

    public Integer getServicelevel() {
        return servicelevel;
    }

    public void setServicelevel(Integer servicelevel) {
        this.servicelevel = servicelevel;
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

    public Double getRealgetlv20() {
        return realgetlv20;
    }

    public void setRealgetlv20(Double realgetlv20) {
        this.realgetlv20 = realgetlv20;
    }

    public Double getRealgetl() {
        return realgetl;
    }

    public void setRealgetl(Double realgetl) {
        this.realgetl = realgetl;
    }

    public Double getDuringsales() {
        return duringsales;
    }

    public void setDuringsales(Double duringsales) {
        this.duringsales = duringsales;
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus == null ? null : transtatus.trim();
    }

    public Date getInstationtime() {
        return instationtime;
    }

    public void setInstationtime(Date instationtime) {
        this.instationtime = instationtime;
    }

    public Double getIndemnityloss() {
        return indemnityloss;
    }

    public void setIndemnityloss(Double indemnityloss) {
        this.indemnityloss = indemnityloss;
    }
}