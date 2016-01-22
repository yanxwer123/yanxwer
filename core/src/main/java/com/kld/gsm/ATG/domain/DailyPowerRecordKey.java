package com.kld.gsm.ATG.domain;

public class DailyPowerRecordKey {
    private String date;
    private String time;
    private Integer oilcanno;
    private Integer operatetype;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getOilcanno() {
        return oilcanno;
    }

    public void setOilcanno(Integer oilcanno) {
        this.oilcanno = oilcanno;
    }

    public Integer getOperatetype() {
        return operatetype;
    }

    public void setOperatetype(Integer operatetype) {
        this.operatetype = operatetype;
    }
}