package com.kld.gsm.ATG.dao;

import java.util.HashMap;
import java.util.List;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.AlarmShiftLostKey;
@MySqlRepository
public interface AlarmShiftLostDao {
    int deleteByPrimaryKey(AlarmShiftLostKey key);

    int insert(com.kld.gsm.ATG.domain.AlarmShiftLost record);

    int insertSelective(com.kld.gsm.ATG.domain.AlarmShiftLost record);

    com.kld.gsm.ATG.domain.AlarmShiftLost selectByPrimaryKey(AlarmShiftLostKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.AlarmShiftLost record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.AlarmShiftLost record);
    
    List<com.kld.gsm.ATG.domain.AlarmShiftLost> selectByDate(HashMap map);

    List<com.kld.gsm.ATG.domain.AlarmShiftLost> selectByTrans(String stauts);
}