package com.kld.gsm.center.domain;

public class oss_sysmanage_powerRecordKey {
    private String date;

    private String time;

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
}