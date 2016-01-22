package com.kld.gsm.center.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class oss_acceptance_odRegisterInfoKey {
    @JsonProperty("manualNo")
    private String manualno;

    private Integer oilcan;

    private String nodeno;

    public String getManualno() {
        return manualno;
    }

    public void setManualno(String manualno) {
        this.manualno = manualno == null ? null : manualno.trim();
    }

    public Integer getOilcan() {
        return oilcan;
    }

    public void setOilcan(Integer oilcan) {
        this.oilcan = oilcan;
    }

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }
}