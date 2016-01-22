package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_alarm_measureLeak;
import com.kld.gsm.center.domain.oss_alarm_measureLeakKey;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@MysqlRepository
public interface oss_alarm_measureLeakMapper {
    int deleteByPrimaryKey(oss_alarm_measureLeakKey key);

    int insert(oss_alarm_measureLeak record);

    int insertSelective(oss_alarm_measureLeak record);

    oss_alarm_measureLeak selectByPrimaryKey(oss_alarm_measureLeakKey key);

    int updateByPrimaryKeySelective(oss_alarm_measureLeak record);

    int updateByPrimaryKey(oss_alarm_measureLeak record);
    List<HashMap<String,Object>> selectmeasurebywhere(HashMap hashmap);
    List<HashMap<String,Object>> querypage(HashMap hashmap);
}