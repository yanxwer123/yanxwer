package com.kld.gsm.ATG.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.MonitorTimeInventory;
import com.kld.gsm.ATG.domain.MonitorTimeInventoryKey;

@MySqlRepository
public interface MonitorTimeInventoryDao {
    int deleteByPrimaryKey(MonitorTimeInventoryKey key);

    int insert(MonitorTimeInventory record);

    int insertSelective(MonitorTimeInventory record);

    MonitorTimeInventory selectByYGBH(Integer OilCan, Timestamp storetime);

    MonitorTimeInventory selectByPrimaryKey(MonitorTimeInventoryKey key);

    int updateByPrimaryKeySelective(MonitorTimeInventory record);

    int updateByPrimaryKey(MonitorTimeInventory record);

    String getTimeInventoryList(Map map);
    
    List<MonitorTimeInventory> selectAllOilCanInfoByOilNo(String oilNo);

	List<MonitorTimeInventory> querySdkc(HashMap map);
	
	MonitorTimeInventory selectBeginDataByCanNo(Integer oilCan);
    List<com.kld.gsm.ATG.domain.MonitorTimeInventory> selectByTrans(String stauts);
}