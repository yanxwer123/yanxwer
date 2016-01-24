package com.kld.gsm.ATG.domain;

import java.text.DecimalFormat;
import java.util.Date;

public class AcceptanceOdRegister {
    private String deliveryno;

    private String manualNo;

    private String oilno;

    private String shift;

    private Double dischargeloss;

    private Double dischargerate;

    private Double dischargeLossV20;

    private Double dischargeRateV20;

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

    @Override
    public String toString() {
        return "AcceptanceOdRegister{" +
                "deliveryno='" + deliveryno + '\'' +
                ", manualNo='" + manualNo + '\'' +
                ", oilno='" + oilno + '\'' +
                ", shift='" + shift + '\'' +
                ", dischargeloss=" + dischargeloss +
                ", dischargerate=" + dischargerate +
                ", dischargeLossV20=" + dischargeLossV20 +
                ", dischargeRateV20=" + dischargeRateV20 +
                ", planl=" + planl +
                ", realreceivel=" + realreceivel +
                ", heightempey=" + heightempey +
                ", heighttotal=" + heighttotal +
                ", heightwater=" + heightwater +
                ", cubagetable=" + cubagetable +
                ", plumbunbankoperator='" + plumbunbankoperator + '\'' +
                ", calculateoperator='" + calculateoperator + '\'' +
                ", backbankno='" + backbankno + '\'' +
                ", cantrucktemp=" + cantrucktemp +
                ", isfulldose=" + isfulldose +
                ", servicelevel=" + servicelevel +
                ", createtime=" + createtime +
                ", isdel=" + isdel +
                ", realgetl=" + realgetl +
                ", realGetLV20=" + realGetLV20 +
                ", duringsales=" + duringsales +
                ", transtatus='" + transtatus + '\'' +
                ", begintime=" + begintime +
                ", endtime=" + endtime +
                ", instationtime=" + instationtime +
                ", indemnityloss=" + indemnityloss +
                '}';
    }

    private Integer isfulldose;

    private Integer servicelevel;

    private Date createtime;

    private Integer isdel;

    private Double realgetl;

    private Double realGetLV20;

    private Double duringsales;

    private String transtatus;

    private Date begintime;

    private Date endtime;

    private Date instationtime;

    private Double indemnityloss;

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
        DecimalFormat df=new DecimalFormat("#####.00");
        if (indemnityloss!=null){
            indemnityloss=Double.parseDouble(df.format(indemnityloss));
        }
        this.indemnityloss = indemnityloss;
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

    public String getDeliveryno() {
        return deliveryno;
    }

    public void setDeliveryno(String deliveryno) {
        this.deliveryno = deliveryno == null ? null : deliveryno.trim();
    }

    public String getManualNo() {
        return manualNo;
    }

    public void setManualNo(String manualNo) {
        this.manualNo = manualNo;
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

    public Double getDischargeloss() {
        return dischargeloss;
    }

    public void setDischargeloss(Double dischargeloss) {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (dischargeloss!=null){
            dischargeloss=Double.parseDouble(df.format(dischargeloss));
        }
        this.dischargeloss = dischargeloss;
    }

    public Double getDischargerate() {
        return dischargerate;
    }

    public void setDischargerate(Double dischargerate) {
        DecimalFormat df=new DecimalFormat("#####.0000");
        if (dischargerate==null)
        {
            dischargerate=0.0;
        }else{
            dischargerate=Double.parseDouble(df.format(dischargerate));
        }
        this.dischargerate = dischargerate;
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
        DecimalFormat df=new DecimalFormat("#####.00");
        if (realreceivel==null)
        {
            realreceivel=0.0;
        }else{
            realreceivel=Double.parseDouble(df.format(realreceivel));
        }
        this.realreceivel = realreceivel;
    }

    public Double getHeightempey() {
        return heightempey;
    }

    public void setHeightempey(Double heightempey) {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (heightempey==null)
        {
            heightempey=0.0;
        }else{
            heightempey=Double.parseDouble(df.format(heightempey));
        }
        this.heightempey = heightempey;
    }

    public Double getHeighttotal() {
        return heighttotal;
    }

    public void setHeighttotal(Double heighttotal) {

        DecimalFormat df=new DecimalFormat("#####.00");
        if (heighttotal==null)
        {
            heighttotal=0.0;
        }else{
            heighttotal=Double.parseDouble(df.format(heighttotal));
        }
        this.heighttotal = heighttotal;
    }

    public Double getHeightwater() {
        return heightwater;
    }

    public void setHeightwater(Double heightwater) {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (heightwater==null)
        {
            heightwater=0.0;
        }else{
            heightwater=Double.parseDouble(df.format(heightwater));
        }
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

    public Double getRealgetl() {
        return realgetl;
    }

    public void setRealgetl(Double realgetl) {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (realgetl==null)
        {
            realgetl=0.0;
        }else{
            realgetl=Double.parseDouble(df.format(realgetl));
        }
        this.realgetl = realgetl;
    }

    public Double getDuringsales() {
        return duringsales;
    }

    public void setDuringsales(Double duringsales) {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (duringsales==null)
        {
            duringsales=0.0;
        }else{
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

    public Double getDischargeLossV20() {
        return dischargeLossV20;
    }

    public void setDischargeLossV20(Double dischargeLossV20) {
        DecimalFormat df=new DecimalFormat("#####.00");
        if (dischargeLossV20==null)
        {
            dischargeLossV20=0.0;
        }else{
            dischargeLossV20=Double.parseDouble(df.format(dischargeLossV20));
        }
        this.dischargeLossV20 = dischargeLossV20;
    }

    public Double getDischargeRateV20() {
        return dischargeRateV20;
    }

    public void setDischargeRateV20(Double dischargeRateV20) {
        DecimalFormat df=new DecimalFormat("#####.0000");
        if (dischargeRateV20==null)
        {
            dischargeRateV20=0.0;
        }else{
            dischargeRateV20=Double.parseDouble(df.format(dischargeRateV20));
        }
        this.dischargeRateV20 = dischargeRateV20;
    }

    public Double getRealGetLV20() {
        return realGetLV20;
    }

    public void setRealGetLV20(Double realGetLV20) {
        DecimalFormat df=new DecimalFormat("######.00");
        if (realGetLV20==null){
            realGetLV20=0.0;
        }else{
            realGetLV20=Double.parseDouble(df.format(realGetLV20));
        }
        this.realGetLV20 = realGetLV20;
    }
}