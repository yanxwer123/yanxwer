package com.kld.gsm.ATG.domain;

public class DailyStaticOilGunInverntory extends DailyStaticOilGunInverntoryKey {
    private Integer gunno;

    private Double pumpup;

    public Integer getGunno() {
        return gunno;
    }

    public void setGunno(Integer gunno) {
        this.gunno = gunno;
    }

    public Double getPumpup() {
        return pumpup;
    }

    public void setPumpup(Double pumpup) {
        this.pumpup = pumpup;
    }

    @Override
    public String toString() {
        return "DailyStaticOilGunInverntory{" +
                "gunno=" + gunno +
                ", pumpup=" + pumpup +
                '}';
    }
}