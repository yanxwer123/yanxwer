package com.kld.gsm.ATG.dao;

import java.util.HashMap;
import java.util.List;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.AlarmSaleOutKey;
@MySqlRepository
public interface AlarmSaleOutDao {
    int deleteByPrimaryKey(AlarmSaleOutKey key);

    int insert(com.kld.gsm.ATG.domain.AlarmSaleOut record);

    int insertSelective(com.kld.gsm.ATG.domain.AlarmSaleOut record);

    com.kld.gsm.ATG.domain.AlarmSaleOut selectByPrimaryKey(AlarmSaleOutKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.AlarmSaleOut record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.AlarmSaleOut record);

    List<com.kld.gsm.ATG.domain.AlarmSaleOut> selectByDate(HashMap map);

    List<com.kld.gsm.ATG.domain.AlarmSaleOut> selectByTrans(String stauts);

    List<com.kld.gsm.ATG.domain.AlarmSaleOut> selectEndTime(com.kld.gsm.ATG.domain.AlarmSaleOut record);

    int updateByOilno(com.kld.gsm.ATG.domain.AlarmSaleOut record);

}