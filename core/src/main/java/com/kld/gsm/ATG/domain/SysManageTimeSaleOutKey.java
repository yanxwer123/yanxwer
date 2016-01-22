package com.kld.gsm.ATG.domain;

public class SysManageTimeSaleOutKey {
    private String oilno;

    private Integer saletime;

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno;
    }

    public Integer getSaletime() {
        return saletime;
    }

    public void setSaletime(Integer saletime) {
        this.saletime = saletime;
    }

    @Override
    public String toString() {
        return "SysManageTimeSaleOutKey{" +
                "oilno='" + oilno + '\'' +
                ", saletime=" + saletime +
                '}';
    }
}