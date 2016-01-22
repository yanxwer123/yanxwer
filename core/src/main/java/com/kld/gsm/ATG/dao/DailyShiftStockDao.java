package com.kld.gsm.ATG.dao;


import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.DailyPumpDigitShift;
import com.kld.gsm.ATG.domain.DailyShiftStock;
import com.kld.gsm.ATG.domain.DailyShiftStockKey;

import java.util.List;

@MySqlRepository
public interface DailyShiftStockDao {
    int deleteByPrimaryKey(DailyShiftStockKey key);

    int insert(DailyShiftStock record);

    int insertSelective(DailyShiftStock record);

    DailyShiftStock selectByPrimaryKey(DailyShiftStockKey key);

    int updateByPrimaryKeySelective(DailyShiftStock record);

    int updateByPrimaryKey(DailyShiftStock record);

    List<DailyShiftStock> selectByTrans(String stauts);
}