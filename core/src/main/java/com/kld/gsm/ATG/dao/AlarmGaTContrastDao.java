package com.kld.gsm.ATG.dao;

import java.util.HashMap;
import java.util.List;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.AlarmGaTContrastKey;
@MySqlRepository
public interface AlarmGaTContrastDao {
    int deleteByPrimaryKey(AlarmGaTContrastKey key);

    int insert(com.kld.gsm.ATG.domain.AlarmGaTContrast record);

    int insertSelective(com.kld.gsm.ATG.domain.AlarmGaTContrast record);

    com.kld.gsm.ATG.domain.AlarmGaTContrast selectByPrimaryKey(AlarmGaTContrastKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.AlarmGaTContrast record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.AlarmGaTContrast record);
    
    List<com.kld.gsm.ATG.domain.AlarmGaTContrast> selectByDate(HashMap map);

    List<com.kld.gsm.ATG.domain.AlarmGaTContrast> selectByTrans(String stauts);
}