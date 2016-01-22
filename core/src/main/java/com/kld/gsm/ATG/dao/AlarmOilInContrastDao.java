package com.kld.gsm.ATG.dao;

import java.util.HashMap;
import java.util.List;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
@MySqlRepository
public interface AlarmOilInContrastDao {
    int deleteByPrimaryKey(String deliveryno);

    int insert(com.kld.gsm.ATG.domain.AlarmOilInContrast record);

    int insertSelective(com.kld.gsm.ATG.domain.AlarmOilInContrast record);

    com.kld.gsm.ATG.domain.AlarmOilInContrast selectByPrimaryKey(String deliveryno);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.AlarmOilInContrast record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.AlarmOilInContrast record);
    
    List<com.kld.gsm.ATG.domain.AlarmOilInContrast> selectByDate(HashMap map);
    List<com.kld.gsm.ATG.domain.AlarmOilInContrast> selectByTrans(String stauts);
}