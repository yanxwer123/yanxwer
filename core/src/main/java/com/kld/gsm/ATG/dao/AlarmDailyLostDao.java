package com.kld.gsm.ATG.dao;

import java.util.HashMap;
import java.util.List;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.AlarmDailyLostKey;

@MySqlRepository
public interface AlarmDailyLostDao {
    int deleteByPrimaryKey(AlarmDailyLostKey key);

    int insert(com.kld.gsm.ATG.domain.AlarmDailyLost record);

    int insertSelective(com.kld.gsm.ATG.domain.AlarmDailyLost record);

    com.kld.gsm.ATG.domain.AlarmDailyLost selectByPrimaryKey(AlarmDailyLostKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.AlarmDailyLost record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.AlarmDailyLost record);
    
    List<com.kld.gsm.ATG.domain.AlarmDailyLost> selectByDate(HashMap map);

    List<com.kld.gsm.ATG.domain.AlarmDailyLost> selectByTrans(String stauts);
    //按照时间断和oilno查询对应得出库单号
    List<com.kld.gsm.ATG.domain.AlarmDailyLost> selectByOilNo(HashMap map);
}