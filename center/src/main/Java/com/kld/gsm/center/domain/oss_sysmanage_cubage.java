package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_sysmanage_cubage extends oss_sysmanage_cubageKey {
    private Double height;

    private Double liter;

    private Date updatetime;

    private String updatetype;

    private String oucode;

    private String transtatus;

    private Integer status;

    private String oilcan;

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getLiter() {
        return liter;
    }

    public void setLiter(Double liter) {
        this.liter = liter;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdatetype() {
        return updatetype;
    }

    public void setUpdatetype(String updatetype) {
        this.updatetype = updatetype == null ? null : updatetype.trim();
    }

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode == null ? null : oucode.trim();
    }

    public String getTranstatus(){return transtatus;}

    public  void setTranstatus(String transtatus){this.transtatus=transtatus;}

    public Integer getStatus(){return status;}

    public void setStatus(Integer status){this.status=status;}
}