package com.kld.gsm.center.domain.hn;


import com.fasterxml.jackson.annotation.JsonProperty;

/*
Created BY niyang
Created Date 2015/11/21
Description 湖南接口油库信息实体类
*/
public class HNOilDepot {
    @JsonProperty("YK_ID")
    private String CODE;

    @JsonProperty("YK_NAME")
    private String NAME;

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
