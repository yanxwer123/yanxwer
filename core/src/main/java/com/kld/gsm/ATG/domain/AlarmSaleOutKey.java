package com.kld.gsm.ATG.domain;

import java.util.Date;

public class AlarmSaleOutKey {
    private String oilno;

    private Date mesasuretime;

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno == null ? null : oilno.trim();
    }

    public Date getMesasuretime() {
        return mesasuretime;
    }

    public void setMesasuretime(Date mesasuretime) {
        this.mesasuretime = mesasuretime;
    }
}