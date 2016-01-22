package com.kld.app.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.kld.gsm.ATG.domain.AlarmDailyLostKey;

public interface AlarmDailyLostService {
    int deleteByPrimaryKey(AlarmDailyLostKey key);

    int insert(com.kld.gsm.ATG.domain.AlarmDailyLost record);

    int insertSelective(com.kld.gsm.ATG.domain.AlarmDailyLost record);

    com.kld.gsm.ATG.domain.AlarmDailyLost selectByPrimaryKey(AlarmDailyLostKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.AlarmDailyLost record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.AlarmDailyLost record);
    
    List<com.kld.gsm.ATG.domain.AlarmDailyLost> selectByDate(Date begin, Date end);

    List<com.kld.gsm.ATG.domain.AlarmDailyLost> selectByOilNo(String oilno,Date begin, Date end);

    String selectOilNo(String OilNo);
}
