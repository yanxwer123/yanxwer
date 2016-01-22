package com.kld.app.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.kld.gsm.ATG.domain.AlarmMeasureLeak;
import com.kld.gsm.ATG.domain.AlarmMeasureLeakKey;

public interface AlarmMeasureLeakService {
    int deleteByPrimaryKey(AlarmMeasureLeakKey key);

    List<AlarmMeasureLeak> selectByOilcan(Integer oilcanno);

    List<AlarmMeasureLeak> selecthasStartByOilcan(Integer oilcanno);

    int updateEndDate(HashMap map1);

    int insert(com.kld.gsm.ATG.domain.AlarmMeasureLeak record);

    int insertSelective(com.kld.gsm.ATG.domain.AlarmMeasureLeak record);

    com.kld.gsm.ATG.domain.AlarmMeasureLeak selectByPrimaryKey(AlarmMeasureLeakKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.AlarmMeasureLeak record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.AlarmMeasureLeak record);
    
    List<com.kld.gsm.ATG.domain.AlarmMeasureLeak> selectByDate(Date begin, Date end,String oilcan);

    AlarmMeasureLeak selectinfoByCanNo(Integer canno);
}
