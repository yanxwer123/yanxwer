package com.kld.gsm.ATG.domain;

import java.util.List;

/**
 * Created by fangzhun on 2015/11/25.
 */
public class Tankoilgun {
    public List<MonitorOilgun> getMonitorOilgun() {
        return monitorOilgun;
    }

    public void setMonitorOilgun(List<MonitorOilgun> monitorOilgun) {
        this.monitorOilgun = monitorOilgun;
    }

    public MonitorTankOil getMonitorTankOil() {
        return monitorTankOil;
    }

    public void setMonitorTankOil(MonitorTankOil monitorTankOil) {
        this.monitorTankOil = monitorTankOil;
    }

    public MonitorTankOil monitorTankOil;
    public List<MonitorOilgun> monitorOilgun;
}
