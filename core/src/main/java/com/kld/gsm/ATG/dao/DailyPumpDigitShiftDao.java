package com.kld.gsm.ATG.dao;

import java.util.List;
import java.util.Map;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.DailyOilDailyRecord;
import com.kld.gsm.ATG.domain.DailyPumpDigitShift;
import com.kld.gsm.ATG.domain.DailyPumpDigitShiftKey;
@MySqlRepository
public interface DailyPumpDigitShiftDao {
    int deleteByPrimaryKey(DailyPumpDigitShiftKey key);

    int insert(com.kld.gsm.ATG.domain.DailyPumpDigitShift record);

    int insertSelective(com.kld.gsm.ATG.domain.DailyPumpDigitShift record);

    com.kld.gsm.ATG.domain.DailyPumpDigitShift selectByPrimaryKey(DailyPumpDigitShiftKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.DailyPumpDigitShift record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.DailyPumpDigitShift record);
    
    List<com.kld.gsm.ATG.domain.DailyPumpDigitShift> selectByShift(String shift);
    List<DailyPumpDigitShift> selectByTrans(String stauts);

    int updateByShift(Map<String,String> map);
}