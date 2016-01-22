package com.kld.gsm.center.domain;

public class oss_monitor_InventoryKey {
    private Integer oilcanno;

    private String date;

    private String time;

    private String nodeno;

    public Integer getOilcanno() {
        return oilcanno;
    }

    public void setOilcanno(Integer oilcanno) {
        this.oilcanno = oilcanno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }
}