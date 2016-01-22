package com.kld.gsm.center.domain.hn;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

/*
Created BY niyang
Created Date 2015/11/21
Description 湖南接口返回出库单实体类
*/
public class HNDelivery {
    @JsonProperty("CK_ID")
    private String CK_ID;

    @JsonProperty("PSD_ID")
    private String PSD_ID;

    @JsonProperty("OILS_ID")
    private String OILS_ID;

    @JsonProperty("YK_ID")
    private String YK_ID;

    @JsonProperty("FH_DATE")
    private String FH_DATE;

    @JsonProperty("FH_WEIGHT")
    private Double FH_WEIGHT;

    @JsonProperty("FH_BULK")
    private Double FH_BULK;
    /*
    * 确定是数字？
    * */
    @JsonProperty("W_UNIT")
    private String W_UNIT;

    @JsonProperty("CP_NO")
    private String CP_NO;

    @JsonProperty("CYR")
    private String CYR;

    @JsonProperty("TEST_TEMP")
    private Double TEST_TEMP;

    @JsonProperty("TEST_DENSITY")
    private Double TEST_DENSITY;

    @JsonProperty("LSGC")
    private String LSGC;

    @JsonProperty("KCDD")
    private String KCDD;

/*    @JsonProperty("CSWLPZH")
    private String CSWLPZH;

    @JsonProperty("JDBS")
    private String JDBS;

    @JsonProperty("XYBJ")
    private Integer XYBJ;*/

    private String nodeno;

    public String getCK_ID() {
        return CK_ID;
    }

    public void setCK_ID(String CK_ID) {
        this.CK_ID = CK_ID;
    }

    public String getPSD_ID() {
        return PSD_ID;
    }

    public void setPSD_ID(String PSD_ID) {
        this.PSD_ID = PSD_ID;
    }

    public String getOILS_ID() {
        return OILS_ID;
    }

    public void setOILS_ID(String OILS_ID) {
        this.OILS_ID = OILS_ID;
    }

    public String getYK_ID() {
        return YK_ID;
    }

    public void setYK_ID(String YK_ID) {
        this.YK_ID = YK_ID;
    }

    public String getFH_DATE() {
        return FH_DATE;
    }

    public void setFH_DATE(String FH_DATE) {
        this.FH_DATE = FH_DATE;
    }

    public Double getFH_BULK() {
        return FH_BULK;
    }

    public void setFH_BULK(Double FH_BULK) {
        this.FH_BULK = FH_BULK;
    }

    public Double getFH_WEIGHT() {
        return FH_WEIGHT;
    }

    public void setFH_WEIGHT(Double FH_WEIGHT) {
        this.FH_WEIGHT = FH_WEIGHT;
    }

    public String getW_UNIT() {
        return W_UNIT;
    }

    public void setW_UNIT(String w_UNIT) {
        W_UNIT = w_UNIT;
    }

    public String getCP_NO() {
        return CP_NO;
    }

    public void setCP_NO(String CP_NO) {
        this.CP_NO = CP_NO;
    }

    public String getCYR() {
        return CYR;
    }

    public void setCYR(String CYR) {
        this.CYR = CYR;
    }

    public Double getTEST_TEMP() {
        return TEST_TEMP;
    }

    public void setTEST_TEMP(Double TEST_TEMP) {
        this.TEST_TEMP = TEST_TEMP;
    }

    public Double getTEST_DENSITY() {
        return TEST_DENSITY;
    }

    public void setTEST_DENSITY(Double TEST_DENSITY) {
        this.TEST_DENSITY = TEST_DENSITY;
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

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno;
    }

    /*    public String getCSWLPZH() {
        return CSWLPZH;
    }

    public void setCSWLPZH(String CSWLPZH) {
        this.CSWLPZH = CSWLPZH;
    }

    public String getJDBS() {
        return JDBS;
    }

    public void setJDBS(String JDBS) {
        this.JDBS = JDBS;
    }

    public Integer getXYBJ() {
        return XYBJ;
    }

    public void setXYBJ(Integer XYBJ) {
        this.XYBJ = XYBJ;
    }*/
}

