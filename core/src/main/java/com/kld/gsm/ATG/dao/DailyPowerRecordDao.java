package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.DailyPowerRecord;
import com.kld.gsm.ATG.domain.DailyPowerRecordKey;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

//液位仪开关记录
@MySqlRepository
public interface DailyPowerRecordDao {

    int deleteByPrimaryKey(DailyPowerRecordKey key);

    int insert(DailyPowerRecord record);

    int insertSelective(DailyPowerRecord record);

    DailyPowerRecord selectByPrimaryKey(DailyPowerRecordKey key);

    int updateByPrimaryKeySelective(DailyPowerRecord record);

    int updateByPrimaryKey(DailyPowerRecord record);

    List<DailyPowerRecord> selectByDate(HashMap pMap);


}