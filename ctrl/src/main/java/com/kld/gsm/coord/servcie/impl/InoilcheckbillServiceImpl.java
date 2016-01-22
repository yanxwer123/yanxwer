package com.kld.gsm.coord.servcie.impl;


import com.kld.gsm.coord.dao.InoilcheckbillDao;
import com.kld.gsm.coord.domain.InOilCheckBill;
import com.kld.gsm.coord.servcie.InoilcheckbillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chen on 2015/11/7.
 */
@SuppressWarnings("all")
@Service
public class InoilcheckbillServiceImpl implements InoilcheckbillService {

    @Autowired
    private InoilcheckbillDao inoilcheckbillDao;

    @Override
    public int insertInoilcheckbill(InOilCheckBill inOilCheckBill) {
        return inoilcheckbillDao.insertInoilcheckbill(inOilCheckBill);
    }

    @Override
    public int updateInoilcheckbill(String DeliveryNo) {
        return inoilcheckbillDao.updateInoilcheckbill(DeliveryNo);
    }
}
