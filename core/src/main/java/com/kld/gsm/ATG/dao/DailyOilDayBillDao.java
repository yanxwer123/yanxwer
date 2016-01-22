package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.DailyOilDayBill;
import com.kld.gsm.ATG.domain.DailyOilDayBillKey;
import com.kld.gsm.ATG.domain.DailyPumpDigitShift;

import java.util.List;

@MySqlRepository
public interface DailyOilDayBillDao {
    int deleteByPrimaryKey(DailyOilDayBillKey key);

    int insert(com.kld.gsm.ATG.domain.DailyOilDayBill record);

    int insertSelective(com.kld.gsm.ATG.domain.DailyOilDayBill record);

    com.kld.gsm.ATG.domain.DailyOilDayBill selectByPrimaryKey(DailyOilDayBillKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.DailyOilDayBill record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.DailyOilDayBill record);
    List<DailyOilDayBill> selectByTrans(String stauts);
}