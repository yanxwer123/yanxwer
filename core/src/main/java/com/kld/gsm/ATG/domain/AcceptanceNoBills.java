package com.kld.gsm.ATG.domain;

import java.util.Date;

public class AcceptanceNoBills {
    private String deliveryno;

    private String confirmno;

    private String oilno;

    private String oilname;

    private Double planl;

    private Double planton;

    private String cardno;

    private Date arrivetime;

    private String fromoildepot;

    private String tonodeno;

    private Date createtime;

    private String transtatus;

    private String iscomplete;



    public Double getPlanton() {
        return planton;
    }

    public void setPlanton(Double planton) {
        this.planton = planton;
    }

    public String getIscomplete() {
        return iscomplete;
    }

    public void setIscomplete(String iscomplete) {
        this.iscomplete = iscomplete;
    }

    public String getDeliveryno() {
        return deliveryno;
    }

    public void setDeliveryno(String deliveryno) {
        this.deliveryno = deliveryno == null ? null : deliveryno.trim();
    }

    public String getConfirmno() {
        return confirmno;
    }

    public void setConfirmno(String confirmno) {
        this.confirmno = confirmno == null ? null : confirmno.trim();
    }

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno == null ? null : oilno.trim();
    }

    public String getOilname() {
        return oilname;
    }

    public void setOilname(String oilname) {
        this.oilname = oilname == null ? null : oilname.trim();
    }

    public Double getPlanl() {
        return planl;
    }

    public void setPlanl(Double planl) {
        this.planl = planl;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno == null ? null : cardno.trim();
    }

    public Date getArrivetime() {
        return arrivetime;
    }

    public void setArrivetime(Date arrivetime) {
        this.arrivetime = arrivetime;
    }

    public String getFromoildepot() {
        return fromoildepot;
    }

    public void setFromoildepot(String fromoildepot) {
        this.fromoildepot = fromoildepot == null ? null : fromoildepot.trim();
    }

    public String getTonodeno() {
        return tonodeno;
    }

    public void setTonodeno(String tonodeno) {
        this.tonodeno = tonodeno == null ? null : tonodeno.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus == null ? null : transtatus.trim();
    }
}