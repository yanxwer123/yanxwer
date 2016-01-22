package com.kld.gsm.center.domain;

import org.joda.time.DateTime;

import java.util.Date;

public class oss_daily_remoteinventory {
    private String rid;

    private String ouname;

    private String startstock;

    private Date starttime;

    private String oilname;

    private String unloadqty;

    private String saleqty;

    private String backtankqty;

    private String lossrate;

    private String lossqty;

    private String endstock;

    private Date endtime;

    private String oucode;

    private String bqjc;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getOuname() {
        return ouname;
    }

    public void setOuname(String ouname) {
        this.ouname = ouname == null ? null : ouname.trim();
    }

    public String getStartstock() {
        return startstock;
    }

    public void setStartstock(String startstock) {
        this.startstock = startstock == null ? null : startstock.trim();
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public String getOilname() {
        return oilname;
    }

    public void setOilname(String oilname) {
        this.oilname = oilname == null ? null : oilname.trim();
    }

    public String getUnloadqty() {
        return unloadqty;
    }

    public void setUnloadqty(String unloadqty) {
        this.unloadqty = unloadqty == null ? null : unloadqty.trim();
    }

    public String getSaleqty() {
        return saleqty;
    }

    public void setSaleqty(String saleqty) {
        this.saleqty = saleqty == null ? null : saleqty.trim();
    }

    public String getBacktankqty() {
        return backtankqty;
    }

    public void setBacktankqty(String backtankqty) {
        this.backtankqty = backtankqty == null ? null : backtankqty.trim();
    }

    public String getLossrate() {
        return lossrate;
    }

    public void setLossrate(String lossrate) {
        this.lossrate = lossrate == null ? null : lossrate.trim();
    }

    public String getLossqty() {
        return lossqty;
    }

    public void setLossqty(String lossqty) {
        this.lossqty = lossqty == null ? null : lossqty.trim();
    }

    public String getEndstock() {
        return endstock;
    }

    public void setEndstock(String endstock) {
        this.endstock = endstock == null ? null : endstock.trim();
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getOucode(){return oucode;}

    public void setOucode(String oucode){this.oucode=oucode;}

    public String getBqjc(){return bqjc;}

    public void setBqjc(String bqjc){this.bqjc=bqjc;}
}