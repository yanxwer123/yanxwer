package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_alarm_SaleOutKey {
    private String oilno;

    private Date mesasuretime;

    private String nodeno;

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

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }
}