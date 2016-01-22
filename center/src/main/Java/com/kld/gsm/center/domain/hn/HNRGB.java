package com.kld.gsm.center.domain.hn;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/*
Created BY niyang
Created Date 2015/11/21
description 湖南接口 容积表主表实体类
*/
public class HNRGB {
    @JsonProperty("RGB_ID")
    private Integer RGB_ID;

    @JsonProperty("STATION_CODE")
    private String STATION_CODE;

    @JsonProperty("TANK_CODE")
    private String TANK_CODE;

    @JsonProperty("ACCEPTOR")
    private String ACCEPTOR;

    @JsonProperty("CHECK_DATE")
    private String CHECK_DATE;

    @JsonProperty("VER_NO")
    private String VER_NO;

    @JsonProperty("STATUS")
    private Integer STATUS;

    public Integer getRGB_ID() {
        return RGB_ID;
    }

    public void setRGB_ID(Integer RGB_ID) {
        this.RGB_ID = RGB_ID;
    }

    public String getSTATION_CODE() {
        return STATION_CODE;
    }

    public void setSTATION_CODE(String STATION_CODE) {
        this.STATION_CODE = STATION_CODE;
    }

    public String getTANK_CODE() {
        return TANK_CODE;
    }

    public void setTANK_CODE(String TANK_CODE) {
        this.TANK_CODE = TANK_CODE;
    }

    public String getACCEPTOR() {
        return ACCEPTOR;
    }

    public void setACCEPTOR(String ACCEPTOR) {
        this.ACCEPTOR = ACCEPTOR;
    }

    public String getCHECK_DATE() {
        return CHECK_DATE;
    }

    public void setCHECK_DATE(String CHECK_DATE) {
        this.CHECK_DATE = CHECK_DATE;
    }

    public String getVER_NO() {
        return VER_NO;
    }

    public void setVER_NO(String VER_NO) {
        this.VER_NO = VER_NO;
    }

    public Integer getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(Integer STATUS) {
        this.STATUS = STATUS;
    }
}
