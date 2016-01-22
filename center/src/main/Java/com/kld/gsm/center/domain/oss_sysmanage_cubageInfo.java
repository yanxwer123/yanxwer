package com.kld.gsm.center.domain;

public class oss_sysmanage_cubageInfo extends oss_sysmanage_cubageInfoKey {


    private Double liter;

    private String oucode;

    private String transtatus;

    private Integer status;



    public Double getLiter() {
        return liter;
    }

    public void setLiter(Double liter) {
        this.liter = liter;
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