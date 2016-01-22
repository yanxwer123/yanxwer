package com.kld.app.service;

import java.util.Date;
import java.util.List;

import com.kld.gsm.ATG.domain.AlarmSaleOutKey;

//脱销预警
public interface AlarmSaleOutService {
    int deleteByPrimaryKey(AlarmSaleOutKey key);

    int insert(com.kld.gsm.ATG.domain.AlarmSaleOut record);

    int insertSelective(com.kld.gsm.ATG.domain.AlarmSaleOut record);

    com.kld.gsm.ATG.domain.AlarmSaleOut selectByPrimaryKey(AlarmSaleOutKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.AlarmSaleOut record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.AlarmSaleOut record);
    
    List<com.kld.gsm.ATG.domain.AlarmSaleOut> selectByDate(Date begin,Date end);
}
