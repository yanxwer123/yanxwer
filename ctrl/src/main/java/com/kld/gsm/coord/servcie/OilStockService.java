package com.kld.gsm.coord.servcie;


import com.kld.gsm.ATG.domain.AcceptanceOdRegisterInfo;

/**
 * Created by chen on 2015/11/5.
 */
public interface OilStockService {
    AcceptanceOdRegisterInfo selectOilStock(String oilStock);
}
