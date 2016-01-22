package com.kld.gsm.center.domain;

public class oss_daily_pumpDigitShiftKey {
    private String shift;

    private Integer oilgun;

    private String nodeno;

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift == null ? null : shift.trim();
    }

    public Integer getOilgun() {
        return oilgun;
    }

    public void setOilgun(Integer oilgun) {
        this.oilgun = oilgun;
    }

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }
}