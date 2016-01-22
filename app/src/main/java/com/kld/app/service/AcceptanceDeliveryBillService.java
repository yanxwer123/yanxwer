package com.kld.app.service;


import com.kld.gsm.ATG.domain.AcceptanceDeliveryBills;

import java.util.List;

/**
 * Created by chen on 2015/11/7.
 */
public interface AcceptanceDeliveryBillService {
    AcceptanceDeliveryBills selectAcceptanceDeliveryBill(String oilStock);

}
