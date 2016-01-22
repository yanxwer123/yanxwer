package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.domain.AcceptanceOdRegisterInfo;
import com.kld.gsm.coord.dao.OilStockDao;
import com.kld.gsm.coord.servcie.OilStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chen on 2015/11/5.
 */
@SuppressWarnings("all")
@Service
public class OilStockServiceImpl implements OilStockService {

    @Autowired
    private OilStockDao oilStockDao;

    @Override
    public AcceptanceOdRegisterInfo selectOilStock(String oilStock){
        return oilStockDao.selectOilStock(oilStock);

    };
}
