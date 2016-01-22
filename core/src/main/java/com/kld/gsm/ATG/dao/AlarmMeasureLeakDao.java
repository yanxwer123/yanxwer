package com.kld.gsm.ATG.dao;

import java.util.HashMap;
import java.util.List;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.AlarmMeasureLeak;
import com.kld.gsm.ATG.domain.AlarmMeasureLeakKey;
@MySqlRepository
public interface AlarmMeasureLeakDao {

    int deleteByPrimaryKey(AlarmMeasureLeakKey key);

    int updateEndDate(HashMap map1);

    List<AlarmMeasureLeak> selectByOilcan(Integer oilcanno);

    List<AlarmMeasureLeak> selecthasStartByOilcan(Integer oilcanno);

    int insert(com.kld.gsm.ATG.domain.AlarmMeasureLeak record);

    int insertSelective(com.kld.gsm.ATG.domain.AlarmMeasureLeak record);

    com.kld.gsm.ATG.domain.AlarmMeasureLeak selectByPrimaryKey(AlarmMeasureLeakKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.AlarmMeasureLeak record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.AlarmMeasureLeak record);
    
    List<com.kld.gsm.ATG.domain.AlarmMeasureLeak> selectByDate(HashMap map);
    List<com.kld.gsm.ATG.domain.AlarmMeasureLeak> selectByTrans(String stauts);
    int selectByOilCanNo(Integer oilcanno);

    com.kld.gsm.ATG.domain.AlarmMeasureLeak selectinfoBycanno(Integer canno);


}