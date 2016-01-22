package com.kld.gsm.ATG.domain;


import java.util.Date;

/*
Created BY niyang
Created Date 2015/11/25
*/
public class OdregStatus {

    private String nodeno;

    private String deliveryno;

    private String status;

    private Date opttime;

    private String oilcan;

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno;
    }

    public String getDeliveryno() {
        return deliveryno;
    }

    public void setDeliveryno(String deliveryno) {
        this.deliveryno = deliveryno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOpttime() {
        return opttime;
    }

    public void setOpttime(Date opttime) {
        this.opttime = opttime;
    }

    public String getOilcan() {
        return oilcan;
    }

    public void setOilcan(String oilcan) {
        this.oilcan = oilcan;
    }
}
