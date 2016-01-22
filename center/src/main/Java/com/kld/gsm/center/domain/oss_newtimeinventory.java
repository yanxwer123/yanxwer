package com.kld.gsm.center.domain;

import java.util.Date;

/**
 * Created by xhz on 2015/12/8.
 */
public class oss_newtimeinventory {
    private String oucode;
    private String ouname;
    private String oil0;
    private String oil97;
    private String oil93;
    private String oil98;
    private Date storetime;
    public String getOucode(){return oucode;}
    public void setOucode(String oucode){this.oucode=oucode;}
    public String getOuname(){return ouname;}
    public void setOuname(String ouname){this.ouname=ouname;}
    public String getOil0(){return oil0;}
    public void setOil0(String oil0){this.oil0=oil0;}
    public String getOil97(){return oil97;}
    public void setOil97(String oil97){this.oil97=oil97;}
    public String getOil93(){return  oil93;}
    public void setOil93(String oil93){this.oil93=oil93;}
    public String getOil98(){return oil98;}
    public void setOil98(String oil98){this.oil98=oil98;}
    public Date getStoretime(){return storetime;}
    public void setStoretime(Date storetime){this.storetime=storetime;}
}
