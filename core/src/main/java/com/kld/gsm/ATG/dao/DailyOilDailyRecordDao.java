package com.kld.gsm.ATG.dao;

import java.util.HashMap;
import java.util.List;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.DailyOilDailyRecord;
import com.kld.gsm.ATG.domain.DailyOilDailyRecordKey;
import com.kld.gsm.ATG.domain.DailyTradeAccount;

import java.util.HashMap;
import java.util.List;

@MySqlRepository
public interface DailyOilDailyRecordDao {
    int deleteByPrimaryKey(DailyOilDailyRecordKey key);

    int insert(com.kld.gsm.ATG.domain.DailyOilDailyRecord record);

    int insertSelective(com.kld.gsm.ATG.domain.DailyOilDailyRecord record);

    com.kld.gsm.ATG.domain.DailyOilDailyRecord selectByPrimaryKey(DailyOilDailyRecordKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.DailyOilDailyRecord record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.DailyOilDailyRecord record);

    List<DailyOilDailyRecord> selectByTrans(String stauts);

    List<DailyOilDailyRecord> selectByDate(HashMap map);

}