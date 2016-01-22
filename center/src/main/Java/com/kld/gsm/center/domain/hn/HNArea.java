package com.kld.gsm.center.domain.hn;


import com.fasterxml.jackson.annotation.JsonProperty;

/*
Created BY niyang
Created Date 2015/11/21
Descripiton 湖南接口片区信息实体类
*/
public class HNArea {

    @JsonProperty("CODE")
    private String CODE;

    @JsonProperty("NAME")
    private String NAME;

    @JsonProperty("CITY")
    private String CITY;

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

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }
}
