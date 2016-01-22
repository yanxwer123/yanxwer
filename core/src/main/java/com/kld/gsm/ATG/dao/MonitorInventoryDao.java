package com.kld.gsm.ATG.dao;


import com.kld.gsm.ATG.domain.MonitorInventory;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.MonitorInventoryKey;

import java.util.List;
@MySqlRepository
public interface MonitorInventoryDao {
    int deleteByPrimaryKey(MonitorInventoryKey key);

    int insert(MonitorInventory record);

    int insertSelective(MonitorInventory record);

    MonitorInventory selectByPrimaryKey(MonitorInventoryKey key);

    int updateByPrimaryKeySelective(MonitorInventory record);

    int updateByPrimaryKey(MonitorInventory record);
    List<MonitorInventory> selectByTrans(String stauts);
}