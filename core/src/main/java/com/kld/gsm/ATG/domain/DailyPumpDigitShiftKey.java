package com.kld.gsm.ATG.domain;

public class DailyPumpDigitShiftKey {
    private String shift;

    private Integer oilgun;

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
}