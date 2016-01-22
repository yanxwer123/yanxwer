package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_daily_opotDailyStatement;
import com.kld.gsm.center.domain.oss_daily_opotDailyStatementKey;
import org.springframework.stereotype.Repository;

@MysqlRepository
public interface oss_daily_opotDailyStatementMapper {
    int deleteByPrimaryKey(oss_daily_opotDailyStatementKey key);

    int insert(oss_daily_opotDailyStatement record);

    int insertSelective(oss_daily_opotDailyStatement record);

    oss_daily_opotDailyStatement selectByPrimaryKey(oss_daily_opotDailyStatementKey key);

    int updateByPrimaryKeySelective(oss_daily_opotDailyStatement record);

    int updateByPrimaryKey(oss_daily_opotDailyStatement record);
}