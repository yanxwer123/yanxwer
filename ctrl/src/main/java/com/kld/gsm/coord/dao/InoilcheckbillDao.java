package com.kld.gsm.coord.dao;


import com.kld.gsm.coord.domain.InOilCheckBill;
import com.kld.gsm.coord.mybatis.SybaseRepository;

import java.util.HashMap;

/**
 * Created by chen on 2015/11/7.
 */
@SybaseRepository
public interface InoilcheckbillDao {

    int insertInoilcheckbill(InOilCheckBill inOilCheckBill);

    int insertInoilcheckbill1(String sql);
    int updateInoilcheckbill(String DeliveryNo);

    int updateManualno(HashMap map);

    }

