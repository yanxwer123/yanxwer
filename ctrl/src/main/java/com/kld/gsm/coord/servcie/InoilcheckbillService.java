package com.kld.gsm.coord.servcie;


import com.kld.gsm.ATG.domain.AcceptanceDeliveryBills;
import com.kld.gsm.ATG.domain.AcceptanceOdRegister;
import com.kld.gsm.coord.domain.InOilCheckBill;

/**
 * Created by chen on 2015/11/7.
 */
public interface InoilcheckbillService {
    int insertInoilcheckbill(InOilCheckBill inOilCheckBill);
    int updateInoilcheckbill(String DeliveryNo);
}
