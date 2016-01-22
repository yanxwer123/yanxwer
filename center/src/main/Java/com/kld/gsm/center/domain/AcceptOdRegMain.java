package com.kld.gsm.center.domain;


import java.util.List;

/*
Created BY niyang
Created Date 2015/11/25
Description:卸油登记主对象实体类
*/
public class AcceptOdRegMain {

    private oss_acceptance_odRegister acceptanceOdRegister;

    private List<oss_acceptance_odRegisterInfo> acceptanceOdRegisterInfos;

    public oss_acceptance_odRegister getAcceptanceOdRegister() {
        return acceptanceOdRegister;
    }

    public void setAcceptanceOdRegister(oss_acceptance_odRegister acceptanceOdRegister) {
        this.acceptanceOdRegister = acceptanceOdRegister;
    }

    public List<oss_acceptance_odRegisterInfo> getAcceptanceOdRegisterInfos() {
        return acceptanceOdRegisterInfos;
    }

    public void setAcceptanceOdRegisterInfos(List<oss_acceptance_odRegisterInfo> acceptanceOdRegisterInfos) {
        this.acceptanceOdRegisterInfos = acceptanceOdRegisterInfos;
    }
}
