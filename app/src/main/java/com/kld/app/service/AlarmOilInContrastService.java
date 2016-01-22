package com.kld.app.service;

import java.util.Date;
import java.util.List;

public interface AlarmOilInContrastService {
    int deleteByPrimaryKey(String deliveryno);

    int insert(com.kld.gsm.ATG.domain.AlarmOilInContrast record);

    int insertSelective(com.kld.gsm.ATG.domain.AlarmOilInContrast record);

    com.kld.gsm.ATG.domain.AlarmOilInContrast selectByPrimaryKey(String deliveryno);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.AlarmOilInContrast record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.AlarmOilInContrast record);
    
    List<com.kld.gsm.ATG.domain.AlarmOilInContrast> selectByDate(Date begin,Date end);
}
