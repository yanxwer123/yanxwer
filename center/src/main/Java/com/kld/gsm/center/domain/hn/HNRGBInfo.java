package com.kld.gsm.center.domain.hn;


import com.fasterxml.jackson.annotation.JsonProperty;

/*
Created BY niyang
Created Date 2015/11/21
Description 容积表明细表实体类
*/
public class HNRGBInfo {

    @JsonProperty("RGB_ID")
    private Integer RGB_ID;
    @JsonProperty("WEIGHT")
    private Double WEIGHT;
    @JsonProperty("VOLUMN")
    private Double VOLUMN;

    public Integer getRGB_ID() {
        return RGB_ID;
    }

    public void setRGB_ID(Integer RGB_ID) {
        this.RGB_ID = RGB_ID;
    }

    public Double getWEIGHT() {
        return WEIGHT;
    }

    public void setWEIGHT(Double WEIGHT) {
        this.WEIGHT = WEIGHT;
    }

    public Double getVOLUMN() {
        return VOLUMN;
    }

    public void setVOLUMN(Double VOLUMN) {
        this.VOLUMN = VOLUMN;
    }
}
