package com.kld.gsm.ATG.domain;

import java.util.Date;

public class AlarmEquipmentKey {
    private Integer oilcan;

    private Date startalarmtime;

    public Integer getOilcan() {
        return oilcan;
    }

    public void setOilcan(Integer oilcan) {
        this.oilcan = oilcan;
    }

    public Date getStartalarmtime() {
        return startalarmtime;
    }

    public void setStartalarmtime(Date startalarmtime) {
        this.startalarmtime = startalarmtime;
    }
}