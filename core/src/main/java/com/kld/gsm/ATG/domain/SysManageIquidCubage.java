package com.kld.gsm.ATG.domain;

import java.util.Date;

public class SysManageIquidCubage extends SysManageIquidCubageKey {
    private Date updatetime;

    private Integer setstate;

    private String setman;

    private Date settime;

    private Integer inused;

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getSetstate() {
        return setstate;
    }

    public void setSetstate(Integer setstate) {
        this.setstate = setstate;
    }

    public String getSetman() {
        return setman;
    }

    public void setSetman(String setman) {
        this.setman = setman == null ? null : setman.trim();
    }

    public Date getSettime() {
        return settime;
    }

    public void setSettime(Date settime) {
        this.settime = settime;
    }

    public Integer getInused() {
        return inused;
    }

    public void setInused(Integer inused) {
        this.inused = inused;
    }
}