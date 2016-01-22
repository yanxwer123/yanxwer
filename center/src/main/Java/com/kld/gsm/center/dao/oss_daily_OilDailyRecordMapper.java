package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_daily_OilDailyRecord;
import com.kld.gsm.center.domain.oss_daily_OilDailyRecordKey;
import org.springframework.stereotype.Repository;

@MysqlRepository
public interface oss_daily_OilDailyRecordMapper {
    int deleteByPrimaryKey(oss_daily_OilDailyRecordKey key);

    int insert(oss_daily_OilDailyRecord record);

    int insertSelective(oss_daily_OilDailyRecord record);

    oss_daily_OilDailyRecord selectByPrimaryKey(oss_daily_OilDailyRecordKey key);

    int updateByPrimaryKeySelective(oss_daily_OilDailyRecord record);

    int updateByPrimaryKey(oss_daily_OilDailyRecord record);
}