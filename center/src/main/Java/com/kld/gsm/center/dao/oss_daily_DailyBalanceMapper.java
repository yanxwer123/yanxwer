package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_daily_DailyBalance;
import com.kld.gsm.center.domain.oss_daily_DailyBalanceKey;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@MysqlRepository
public interface oss_daily_DailyBalanceMapper {
    int deleteByPrimaryKey(oss_daily_DailyBalanceKey key);

    int insert(oss_daily_DailyBalance record);

    int insertSelective(oss_daily_DailyBalance record);

    oss_daily_DailyBalance selectByPrimaryKey(oss_daily_DailyBalanceKey key);

    int updateByPrimaryKeySelective(oss_daily_DailyBalance record);

    int updateByPrimaryKey(oss_daily_DailyBalance record);
    List<HashMap<String,Object>> selectbalancebywhere(HashMap hashmap);
    List<oss_daily_DailyBalance> selectbalance(HashMap hashmap);
    List<HashMap<String,Object>> querypage(HashMap hashmap);
}