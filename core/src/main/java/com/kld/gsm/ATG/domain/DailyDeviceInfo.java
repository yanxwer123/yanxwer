package com.kld.gsm.ATG.domain;

import java.util.Date;

public class DailyDeviceInfo {
    private Integer oilcanno;  //油罐编号

    private String devicemodel;//产品型号

    private String equipcode;//设备代码

    private String version;//系统版本

    private Date makedate;//制造日期

    public Integer getOilcanno() {
        return oilcanno;
    }

    public void setOilcanno(Integer oilcanno) {
        this.oilcanno = oilcanno;
    }

    public String getDevicemodel() {
        return devicemodel;
    }

    public void setDevicemodel(String devicemodel) {
        this.devicemodel = devicemodel == null ? null : devicemodel.trim();
    }

    public String getEquipcode() {
        return equipcode;
    }

    public void setEquipcode(String equipcode) {
        this.equipcode = equipcode == null ? null : equipcode.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public Date getMakedate() {
        return makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    @Override
    public String toString() {
        return "DailyDeviceInfo{" +
                "oilcanno=" + oilcanno +
                ", devicemodel='" + devicemodel + '\'' +
                ", equipcode='" + equipcode + '\'' +
                ", version='" + version + '\'' +
                ", makedate=" + makedate +
                '}';
    }
}