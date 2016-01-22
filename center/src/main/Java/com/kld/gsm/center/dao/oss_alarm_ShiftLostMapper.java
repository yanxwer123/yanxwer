package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_alarm_ShiftLost;
import com.kld.gsm.center.domain.oss_alarm_ShiftLostKey;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@MysqlRepository
public interface oss_alarm_ShiftLostMapper {
    int deleteByPrimaryKey(oss_alarm_ShiftLostKey key);

    int insert(oss_alarm_ShiftLost record);

    int insertSelective(oss_alarm_ShiftLost record);

    oss_alarm_ShiftLost selectByPrimaryKey(oss_alarm_ShiftLostKey key);

    int updateByPrimaryKeySelective(oss_alarm_ShiftLost record);

    int updateByPrimaryKey(oss_alarm_ShiftLost record);
    List<HashMap<String,Object>> selectshiftlostbywhere(HashMap hashmap);
    List<HashMap<String,Object>> querypage(HashMap hashmap);
    List<HashMap<String,Object>> selectAlarmCount(HashMap map);
}