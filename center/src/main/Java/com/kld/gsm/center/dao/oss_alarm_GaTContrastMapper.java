package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_alarm_DailyLost;
import com.kld.gsm.center.domain.oss_alarm_GaTContrast;
import com.kld.gsm.center.domain.oss_alarm_GaTContrastKey;

import java.util.HashMap;
import java.util.List;


@MysqlRepository
public interface oss_alarm_GaTContrastMapper {
    int deleteByPrimaryKey(oss_alarm_GaTContrastKey key);

    int insert(oss_alarm_GaTContrast record);

    int insertSelective(oss_alarm_GaTContrast record);

    oss_alarm_GaTContrast selectByPrimaryKey(oss_alarm_GaTContrastKey key);

    int updateByPrimaryKeySelective(oss_alarm_GaTContrast record);

    int updateByPrimaryKey(oss_alarm_GaTContrast record);

    List<HashMap<String,Object>> selectGatInfo(HashMap<String,Object> map);
    List<HashMap<String,Object>> selectGatAllInfo(HashMap<String,Object> map);
}