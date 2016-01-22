package com.kld.gsm.center.domain.hn;

import java.util.List;

/**
 * Created by Administrator on 2015/12/10.
 */
/*
Created BY niyang
Created Date 2015/12/10
*/
public class HNodRegMain {
    private HNodRegister odRegister;

    private List<HNodRegisterInfo> registerInfos;

    public List<HNodRegisterInfo> getRegisterInfos() {
        return registerInfos;
    }

    public void setRegisterInfos(List<HNodRegisterInfo> registerInfos) {
        this.registerInfos = registerInfos;
    }

    public HNodRegister getOdRegister() {
        return odRegister;
    }

    public void setOdRegister(HNodRegister odRegister) {
        this.odRegister = odRegister;
    }
}
