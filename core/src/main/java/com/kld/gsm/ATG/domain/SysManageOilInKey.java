package com.kld.gsm.ATG.domain;

public class SysManageOilInKey {
    private Integer oilcanno;

    private String startdate;

    private String starttime;

    public Integer getOilcanno() {
        return oilcanno;
    }

    public void setOilcanno(Integer oilcanno) {
        this.oilcanno = oilcanno;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate == null ? null : startdate.trim();
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime == null ? null : starttime.trim();
    }
}