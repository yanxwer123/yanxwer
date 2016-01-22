package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.coord.dao.OilcanindetailDao;
import com.kld.gsm.coord.domain.OilCanIndeTail;
import com.kld.gsm.coord.servcie.OilcanindetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chen on 2015/11/6.
 */
@SuppressWarnings("all")
@Service
public class OilcanindetailServiceImpl implements OilcanindetailService {

    @Autowired
    private OilcanindetailDao oilcanindetailDao;

    @Override
    public int insertOilcanindetail(OilCanIndeTail oilCanIndeTail) {

        return oilcanindetailDao.insertOilcanindetail(oilCanIndeTail);
    }

    @Override
    public int updateOilcanindetail(String DeliveryNo) {
        return oilcanindetailDao.updateOilcanindetail(DeliveryNo);
    }

}
