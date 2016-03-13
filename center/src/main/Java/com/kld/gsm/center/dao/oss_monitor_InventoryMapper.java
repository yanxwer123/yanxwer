package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_monitor_Inventory;
import com.kld.gsm.center.domain.oss_monitor_InventoryKey;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@MysqlRepository
public interface oss_monitor_InventoryMapper {
    int deleteByPrimaryKey(oss_monitor_InventoryKey key);

    int insert(oss_monitor_Inventory record);

    int insertSelective(oss_monitor_Inventory record);

    oss_monitor_Inventory selectByPrimaryKey(oss_monitor_InventoryKey key);

    int updateByPrimaryKeySelective(oss_monitor_Inventory record);

    int updateByPrimaryKey(oss_monitor_Inventory record);


    List<HashMap<String,Object>> getInventoryList(HashMap<String,Object> map);

    List<HashMap<String,Object>> getInventoryAllList(HashMap<String,Object> map);




}