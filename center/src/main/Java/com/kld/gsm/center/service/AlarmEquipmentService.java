package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.oss_alarm_Equipment;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 设备故障表
 */
public interface AlarmEquipmentService {
    int AddEquipment(List<oss_alarm_Equipment> oss_alarm_equipments);
    List<HashMap<String,Object>>  queryPrepayPageList(Integer intPage, Integer intPageSize,String FailureType,String StartAlarmTime,String EndAlarmTime,String oucode);
    int queryPrepayCount(String FailureType,String StartAlarmTime,String EndAlarmTime,String oucode);

    List<HashMap<String,Object>> selectiq(HashMap hashmap);

    List<HashMap<String, Object>> selectEqbywhere(String start,String end,String oucode);
}
