package com.kld.gsm.ATG.domain;

import java.util.List;

/**
 * Created by fangzhun on 2015/12/10.
 */
public class JTGC {

    private List<DailyStaticOilGunInverntory> oilgunLst;
    private DailyStaticOilCanInventory  tankoil;

    public List<DailyStaticOilGunInverntory> getOilgunLst() {
        return oilgunLst;
    }

    public void setOilgunLst(List<DailyStaticOilGunInverntory> oilgunLst) {
        this.oilgunLst = oilgunLst;
    }

    public DailyStaticOilCanInventory getTankoil() {
        return tankoil;
    }

    public void setTankoil(DailyStaticOilCanInventory tankoil) {
        this.tankoil = tankoil;
    }

}
