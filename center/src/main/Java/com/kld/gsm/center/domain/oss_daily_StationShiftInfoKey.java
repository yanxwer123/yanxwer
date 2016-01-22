package com.kld.gsm.center.domain;

public class oss_daily_StationShiftInfoKey {
    private Integer shift;

    private String nodeno;

    public Integer getShift() {
        return shift;
    }

    public void setShift(Integer shift) {
        this.shift = shift;
    }

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }
}