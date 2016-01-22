package com.kld.gsm.ATG.domain;

public class DailyDeviceInfoDetailKey {
    private Integer oilcanno;//油罐编号

    private String probeno;//探棒序列号

    public Integer getOilcanno() {
        return oilcanno;
    }

    public void setOilcanno(Integer oilcanno) {
        this.oilcanno = oilcanno;
    }

    public String getProbeno() {
        return probeno;
    }

    public void setProbeno(String probeno) {
        this.probeno = probeno == null ? null : probeno.trim();
    }

    @Override
    public String toString() {
        return "DailyDeviceInfo{" +
                "oilcanno=" + oilcanno +
                ", probeno='" + probeno +
                '}';
    }
}