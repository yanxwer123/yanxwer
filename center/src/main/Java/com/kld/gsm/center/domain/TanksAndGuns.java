package com.kld.gsm.center.domain;

import java.util.List;

/*
Created BY niyang
Created Date 2015/12/10
*/
public class TanksAndGuns {
    private List<MacLogInfo> guns;

    private List<atg_stock_data_out_t> tanks;

    public List<MacLogInfo> getGuns() {
        return guns;
    }

    public void setGuns(List<MacLogInfo> guns) {
        this.guns = guns;
    }

    public List<atg_stock_data_out_t> getTanks() {
        return tanks;
    }

    public void setTanks(List<atg_stock_data_out_t> tanks) {
        this.tanks = tanks;
    }
}
