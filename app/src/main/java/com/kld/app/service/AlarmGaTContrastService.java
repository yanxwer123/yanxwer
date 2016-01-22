package com.kld.app.service;

import java.util.Date;
import java.util.List;

import com.kld.gsm.ATG.domain.AlarmGaTContrastKey;

//枪出罐出对比
public interface AlarmGaTContrastService {
    int deleteByPrimaryKey(AlarmGaTContrastKey key);

    int insert(com.kld.gsm.ATG.domain.AlarmGaTContrast record);

    int insertSelective(com.kld.gsm.ATG.domain.AlarmGaTContrast record);

    com.kld.gsm.ATG.domain.AlarmGaTContrast selectByPrimaryKey(AlarmGaTContrastKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.AlarmGaTContrast record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.AlarmGaTContrast record);
    
    List<com.kld.gsm.ATG.domain.AlarmGaTContrast> selectByDate(Date begin,Date end,String oilcan);
}
