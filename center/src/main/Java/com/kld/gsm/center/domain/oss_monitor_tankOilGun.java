package com.kld.gsm.center.domain;

import java.util.List;

/**
 * Created by fangzhun on 2015/11/25.
 */
public class oss_monitor_tankOilGun {
    public List<oss_monitor_oilgun> getMonitorOilgun() {
        return monitorOilgun;
    }

    public void setMonitorOilgun(List<oss_monitor_oilgun> monitorOilgun) {
        this.monitorOilgun = monitorOilgun;
    }

    public oss_monitor_tankoil getMonitorTankOil() {
        return monitorTankOil;
    }

    public void setMonitorTankOil(oss_monitor_tankoil monitorTankOil) {
        this.monitorTankOil = monitorTankOil;
    }

    public oss_monitor_tankoil monitorTankOil;
    public List<oss_monitor_oilgun> monitorOilgun;
}
