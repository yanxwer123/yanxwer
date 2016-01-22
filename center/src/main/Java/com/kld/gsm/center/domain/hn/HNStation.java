package com.kld.gsm.center.domain.hn;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
Created BY niyang
Created Date 2015/11/21
Description 湖南接口加油站实体类
*/
public class HNStation {

    @JsonProperty("STATION_CODE")
    private String STATION_CODE;

    @JsonProperty("STATION_NAME")
    private String STATION_NAME;

    @JsonProperty("SHORT_NAME")
    private String SHORT_NAME;

    @JsonProperty("DSGS")
    private String DSGS;

    @JsonProperty("LSPQ")
    private String LSPQ;

    @JsonProperty("LSGC")
    private String LSGC;

    @JsonProperty("KCDD")
    private String KCDD;

    public String getSTATION_CODE() {
        return STATION_CODE;
    }

    public void setSTATION_CODE(String STATION_CODE) {
        this.STATION_CODE = STATION_CODE;
    }

    public String getSTATION_NAME() {
        return STATION_NAME;
    }

    public void setSTATION_NAME(String STATION_NAME) {
        this.STATION_NAME = STATION_NAME;
    }

    public String getSHORT_NAME() {
        return SHORT_NAME;
    }

    public void setSHORT_NAME(String SHORT_NAME) {
        this.SHORT_NAME = SHORT_NAME;
    }

    public String getDSGS() {
        return DSGS;
    }

    public void setDSGS(String DSGS) {
        this.DSGS = DSGS;
    }

    public String getLSPQ() {
        return LSPQ;
    }

    public void setLSPQ(String LSPQ) {
        this.LSPQ = LSPQ;
    }

    public String getLSGC() {
        return LSGC;
    }

    public void setLSGC(String LSGC) {
        this.LSGC = LSGC;
    }

    public String getKCDD() {
        return KCDD;
    }

    public void setKCDD(String KCDD) {
        this.KCDD = KCDD;
    }
}


