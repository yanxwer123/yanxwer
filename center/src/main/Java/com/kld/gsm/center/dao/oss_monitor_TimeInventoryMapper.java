package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_alarm_GaTContrast;
import com.kld.gsm.center.domain.oss_monitor_TimeInventory;
import com.kld.gsm.center.domain.oss_monitor_TimeInventoryKey;
import com.kld.gsm.center.domain.oss_newtimeinventory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@MysqlRepository
public interface oss_monitor_TimeInventoryMapper {
    int deleteByPrimaryKey(oss_monitor_TimeInventoryKey key);

    int insert(oss_monitor_TimeInventory record);

    int insertSelective(oss_monitor_TimeInventory record);

    oss_monitor_TimeInventory selectByPrimaryKey(oss_monitor_TimeInventoryKey key);

    int updateByPrimaryKeySelective(oss_monitor_TimeInventory record);

    int updateByPrimaryKey(oss_monitor_TimeInventory record);

    List<HashMap<String,Object>> selectTimeInventoryInfo(HashMap<String,Object> map);
    int selectPageCount(HashMap<String,Object> map);


    List<HashMap<String,Object>> selectInventory(HashMap map);
    List<HashMap<String,Object>> selectUploadInventory(HashMap map);
}