package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_alarm_Equipment;
import com.kld.gsm.center.domain.oss_alarm_EquipmentKey;

import java.util.HashMap;
import java.util.List;

@MysqlRepository
public interface oss_alarm_EquipmentMapper {
    int deleteByPrimaryKey(oss_alarm_EquipmentKey key);

    int insert(oss_alarm_Equipment record);

    int insertSelective(oss_alarm_Equipment record);

    oss_alarm_Equipment selectByPrimaryKey(oss_alarm_EquipmentKey key);

    int updateByPrimaryKeySelective(oss_alarm_Equipment record);

    int updateByPrimaryKey(oss_alarm_Equipment record);

    List<HashMap<String,Object>> queryPrepayPageList(HashMap hashmap);
    int queryPrepayCount(HashMap hashmap);

    List<HashMap<String, Object>> selectiq(HashMap hashmap);
    List<HashMap<String,Object>> selectEqbywhere(HashMap hashmap);
}