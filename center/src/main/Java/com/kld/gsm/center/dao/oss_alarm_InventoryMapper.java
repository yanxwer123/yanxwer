package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_alarm_Inventory;
import com.kld.gsm.center.domain.oss_alarm_InventoryKey;

import java.util.HashMap;
import java.util.List;


@MysqlRepository
public interface oss_alarm_InventoryMapper {
    int deleteByPrimaryKey(oss_alarm_InventoryKey key);

    int insert(oss_alarm_Inventory record);

    int insertSelective(oss_alarm_Inventory record);

    int merge(oss_alarm_Inventory record);

    oss_alarm_Inventory selectByPrimaryKey(oss_alarm_InventoryKey key);

    int updateByPrimaryKeySelective(oss_alarm_Inventory record);

    int updateByPrimaryKey(oss_alarm_Inventory record);
    List<HashMap<String,Object>> selectInventoryInfo(HashMap<String,Object> map);
    List<HashMap<String,Object>> selectInventoryAllInfo(HashMap<String,Object> map);
}