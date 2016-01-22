package com.kld.gsm.ATG.domain;

import java.util.Date;

public class AcceptanceDeliveryBills {
    private String testpomautoupdate;
     public String getTestpomautoupdate(){
    return testpomautoupdate;
};
    private String deliveryno;

    private String psdId;

    private Date deliverytime;

    private String oilno;

    private String tonodeno;

    private String fromoildepot;

    private String fromdepotname;

    private String tostationname;

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

    private String oucode;

    private Double density;

    /*标志 出库单 无货单，不在数据表中体现
    * 1 :出库单  2：无货单
    * */
    private String type;

    public String getType() {
        return type;
    }

    private Date begintime;

    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPsdId() {
        return psdId;
    }

    public void setPsdId(String psdId) {
        this.psdId = psdId;
    }

    public String getPsdid() {
        return psdId;
    }

    public void setPsdid(String psdid) {
        this.psdId = psdid;
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

    public String getDeliveryno() {
        return deliveryno;
    }

    public void setDeliveryno(String deliveryno) {
        this.deliveryno = deliveryno == null ? null : deliveryno.trim();
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

    public String getFromoildepot() {
        return fromoildepot;
    }

    public void setFromoildepot(String fromoildepot) {
        this.fromoildepot = fromoildepot == null ? null : fromoildepot.trim();
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