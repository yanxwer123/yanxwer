package com.kld.gsm.center.domain.hn;

import com.kld.gsm.center.domain.oss_monitor_oilgun;
import com.kld.gsm.center.domain.oss_monitor_tankoil;

import java.util.List;

/**
 * Created by fangzhun on 2015/12/10.
 */
public class JTGC {

    private List<HNMonitor_Oilgun> oilgunLst;
    private HNMonitor_Tankoil  tankoil;

    public List<HNMonitor_Oilgun> getOilgunLst() {
        return oilgunLst;
    }

    public void setOilgunLst(List<HNMonitor_Oilgun> oilgunLst) {
        this.oilgunLst = oilgunLst;
    }

    public HNMonitor_Tankoil getTankoil() {
        return tankoil;
    }

    public void setTankoil(HNMonitor_Tankoil tankoil) {
        this.tankoil = tankoil;
    }

}
