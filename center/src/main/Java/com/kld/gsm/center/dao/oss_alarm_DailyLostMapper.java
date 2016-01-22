package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_alarm_DailyLost;
import com.kld.gsm.center.domain.oss_alarm_DailyLostKey;
import com.kld.gsm.center.domain.oss_daily_DailyBalance;
import com.kld.gsm.center.domain.oss_daily_StationShiftInfo;

import java.util.HashMap;
import java.util.List;

@MysqlRepository
public interface oss_alarm_DailyLostMapper {
    int deleteByPrimaryKey(oss_alarm_DailyLostKey key);

    int insert(oss_alarm_DailyLost record);

    int insertSelective(oss_alarm_DailyLost record);

    oss_alarm_DailyLost selectByPrimaryKey(oss_alarm_DailyLostKey key);

    int updateByPrimaryKeySelective(oss_alarm_DailyLost record);

    int updateByPrimaryKey(oss_alarm_DailyLost record);
    List<HashMap<String,Object>> selectDailyLost(HashMap<String,Object> map);
    List<HashMap<String,Object>> selectPageDailyLost(HashMap<String,Object> map);
}