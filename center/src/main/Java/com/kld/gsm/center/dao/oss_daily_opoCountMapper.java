package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_daily_opoCount;
import com.kld.gsm.center.domain.oss_daily_opoCountKey;
import org.springframework.stereotype.Repository;

@MysqlRepository
public interface oss_daily_opoCountMapper {
    int deleteByPrimaryKey(oss_daily_opoCountKey key);

    int insert(oss_daily_opoCount record);

    int insertSelective(oss_daily_opoCount record);

    oss_daily_opoCount selectByPrimaryKey(oss_daily_opoCountKey key);

    int updateByPrimaryKeySelective(oss_daily_opoCount record);

    int updateByPrimaryKey(oss_daily_opoCount record);
}