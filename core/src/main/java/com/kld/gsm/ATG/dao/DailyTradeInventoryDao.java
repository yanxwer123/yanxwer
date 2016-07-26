package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.DailyTradeInventory;
import com.kld.gsm.ATG.domain.DailyTradeInventoryKey;
import com.kld.gsm.ATG.domain.OilVouch;

import java.util.List;
import java.util.Map;

@MySqlRepository
public interface DailyTradeInventoryDao {
    int deleteByPrimaryKey(DailyTradeInventoryKey key);

    int insert(DailyTradeInventory record);

    int insertSelective(DailyTradeInventory record);

    DailyTradeInventory selectByPrimaryKey(DailyTradeInventoryKey key);

    int updateByPrimaryKeySelective(DailyTradeInventory record);

    int updateByPrimaryKey(DailyTradeInventory record);

    List<DailyTradeInventory> selectByTrans(String stauts);

    List<DailyTradeInventory> selectByTrans1(String stauts);

    String selectOilL(Map map);

    int updateByKey(OilVouch oil);
}