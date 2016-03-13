package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_acceptance_deliveryBill {
    private String deliveryno;

    private String psdId;

    private String fromoildepot;

    private String fromdepotname;

    @Override
    public String toString() {
        return "oss_acceptance_deliveryBill{" +
                "deliveryno='" + deliveryno + '\'' +
                ", psdId='" + psdId + '\'' +
                ", fromoildepot='" + fromoildepot + '\'' +
                ", fromdepotname='" + fromdepotname + '\'' +
                ", tostationname='" + tostationname + '\'' +
                ", deliverytime=" + deliverytime +
                ", oilno='" + oilno + '\'' +
                ", tonodeno='" + tonodeno + '\'' +
                ", deliverytemp=" + deliverytemp +
                ", planl=" + planl +
                ", planton=" + planton +
                ", shipmenttime=" + shipmenttime +
                ", carno='" + carno + '\'' +
                ", outsealno='" + outsealno + '\'' +
                ", arrivaltime=" + arrivaltime +
                ", relevancedelveryno='" + relevancedelveryno + '\'' +
                ", transtatus='" + transtatus + '\'' +
                ", iscomplete='" + iscomplete + '\'' +
                ", density=" + density +
                ", oucode='" + oucode + '\'' +
                '}';
    }

    private String tostationname;

    private Date deliverytime;

    private String oilno;

    private String tonodeno;

    private Double deliverytemp;

    private Double planl;

    private Double planton;

    private Date shipmenttime;

    private String carno;

    private String outsealno;

    private Date arrivaltime;

    private String relevancedelveryno;

    private String transtatus;

    private String iscomplete;

    private Double density;

    private String oucode;

    public String getDeliveryno() {
        return deliveryno;
    }

    public void setDeliveryno(String deliveryno) {
        this.deliveryno = deliveryno == null ? null : deliveryno.trim();
    }

    public String getPsdId() {
        return psdId;
    }

    public void setPsdId(String psdId) {
        this.psdId = psdId == null ? null : psdId.trim();
    }

    public String getFromoildepot() {
        return fromoildepot;
    }

    public void setFromoildepot(String depotno) {
        this.fromoildepot = depotno == null ? null : depotno.trim();
    }

    public Date getDeliverytime() {
        return deliverytime;
    }

    public void setDeliverytime(Date deliverytime) {
        this.deliverytime = deliverytime;
    }

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno == null ? null : oilno.trim();
    }

    public String getTonodeno() {
        return tonodeno;
    }

    public void setTonodeno(String tonodeno) {
        this.tonodeno = tonodeno == null ? null : tonodeno.trim();
    }

    public Double getDeliverytemp() {
        return deliverytemp;
    }

    public void setDeliverytemp(Double deliverytemp) {
        this.deliverytemp = deliverytemp;
    }

    public Double getPlanl() {
        return planl;
    }

    public void setPlanl(Double planl) {
        this.planl = planl;
    }

    public Double getPlanton() {
        return planton;
    }

    public void setPlanton(Double planton) {
        this.planton = planton;
    }

    public Date getShipmenttime() {
        return shipmenttime;
    }

    public void setShipmenttime(Date shipmenttime) {
        this.shipmenttime = shipmenttime;
    }

    public String getCarno() {
        return carno;
    }

    public void setCarno(String carno) {
        this.carno = carno == null ? null : carno.trim();
    }

    public String getOutsealno() {
        return outsealno;
    }

    public void setOutsealno(String outsealno) {
        this.outsealno = outsealno == null ? null : outsealno.trim();
    }

    public Date getArrivaltime() {
        return arrivaltime;
    }

    public void setArrivaltime(Date arrivaltime) {
        this.arrivaltime = arrivaltime;
    }

    public String getRelevancedelveryno() {
        return relevancedelveryno;
    }

    public void setRelevancedelveryno(String relevancedelveryno) {
        this.relevancedelveryno = relevancedelveryno == null ? null : relevancedelveryno.trim();
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus == null ? null : transtatus.trim();
    }

    public String getIscomplete() {
        return iscomplete;
    }

    public void setIscomplete(String iscomplete) {
        this.iscomplete = iscomplete == null ? null : iscomplete.trim();
    }

    public Double getDensity() {
        return density;
    }

    public void setDensity(Double density) {
        this.density = density;
    }

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode;
    }

    public String getFromdepotname() {
        return fromdepotname;
    }

    public void setFromdepotname(String fromdepotname) {
        this.fromdepotname = fromdepotname;
    }

    public String getTostationname() {
        return tostationname;
    }

    public void setTostationname(String tostationname) {
        this.tostationname = tostationname;
    }
}