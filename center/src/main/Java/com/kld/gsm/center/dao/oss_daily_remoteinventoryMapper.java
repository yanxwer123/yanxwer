package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.hn.HNGunInfo;
import com.kld.gsm.center.domain.oss_alarm_GaTContrast;
import com.kld.gsm.center.domain.oss_daily_remoteinventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MysqlRepository
public interface oss_daily_remoteinventoryMapper {
    int deleteByPrimaryKey(Integer rid);

    int insert(oss_daily_remoteinventory record);

    int insertSelective(oss_daily_remoteinventory record);

    oss_daily_remoteinventory selectByPrimaryKey(Integer rid);

    int updateByPrimaryKeySelective(oss_daily_remoteinventory record);

    int updateByPrimaryKey(oss_daily_remoteinventory record);

    List<oss_daily_remoteinventory> selectRemoteInfo(HashMap<String,Object> map);
    List<oss_daily_remoteinventory> selectRemoteAllInfo(HashMap<String,Object> map);
    String selectOilName(Map<String, Object> map);
}