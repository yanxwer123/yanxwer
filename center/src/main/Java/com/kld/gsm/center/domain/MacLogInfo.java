package com.kld.gsm.center.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by fangzhun on 2015/11/25.
 */
public class MacLogInfo {
     private String nodeno;

     private  Integer oilGun;

     private Integer oilGunStatus;

     private String  pumpUp;

     private Date getTime;

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno;
    }

    public Integer getOilGun() {
        return oilGun;
    }

    public void setOilGun(Integer oilGun) {
        this.oilGun = oilGun;
    }

    public Integer getOilGunStatus() {
        return oilGunStatus;
    }

    public void setOilGunStatus(Integer oilGunStatus) {
        this.oilGunStatus = oilGunStatus;
    }

    public String getPumpUp() {
        return pumpUp;
    }

    public void setPumpUp(String pumpUp) {
        this.pumpUp = pumpUp;
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }
}
