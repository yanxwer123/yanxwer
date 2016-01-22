package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.oss_acceptance_deliveryBill;
import com.kld.gsm.center.domain.oss_acceptance_nobills;
import com.kld.gsm.center.domain.oss_acceptance_odRegister;
import com.kld.gsm.center.domain.oss_acceptance_odRegisterInfo;

import java.util.List;

/*
Created BY niyang
Created Date 2015/11/20
*/
public interface OilDownService {
    int addOilDown(List<oss_acceptance_odRegister> ossAcceptanceOdRegisterInfos);

    int addOilDownInfo(List<oss_acceptance_odRegisterInfo> oss_acceptance_odRegisterInfos );

    int addnobill(List<oss_acceptance_nobills> ossAcceptanceNobillses);

    int addOutBills(List<oss_acceptance_deliveryBill> ossAcceptanceDeliveryBills);

    List<oss_acceptance_deliveryBill> getOutBillsByNodeNo(String nodeno);

    oss_acceptance_deliveryBill getOutBillByNo(String No);
}
