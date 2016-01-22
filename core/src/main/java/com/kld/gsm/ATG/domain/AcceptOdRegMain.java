package com.kld.gsm.ATG.domain;


import java.util.List;

/*
Created BY niyang
Created Date 2015/11/25
*/
public class AcceptOdRegMain {

    private AcceptanceOdRegister acceptanceOdRegister;

    private List<AcceptanceOdRegisterInfo> acceptanceOdRegisterInfos;

    public AcceptanceOdRegister getAcceptanceOdRegister() {
        return acceptanceOdRegister;
    }

    public void setAcceptanceOdRegister(AcceptanceOdRegister acceptanceOdRegister) {
        this.acceptanceOdRegister = acceptanceOdRegister;
    }

    public List<AcceptanceOdRegisterInfo> getAcceptanceOdRegisterInfos() {
        return acceptanceOdRegisterInfos;
    }

    public void setAcceptanceOdRegisterInfos(List<AcceptanceOdRegisterInfo> acceptanceOdRegisterInfos) {
        this.acceptanceOdRegisterInfos = acceptanceOdRegisterInfos;
    }
}
