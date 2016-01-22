package com.kld.gsm.ATG.dao;


import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.DailyStaticOilCanInventory;
import com.kld.gsm.ATG.domain.DailyStaticOilCanInventoryKey;

import java.util.List;

@MySqlRepository
public interface DailyStaticOilCanInventoryDao {
    int deleteByPrimaryKey(DailyStaticOilCanInventoryKey key);

    int insert(DailyStaticOilCanInventory record);

    int insertSelective(DailyStaticOilCanInventory record);

    DailyStaticOilCanInventory selectByPrimaryKey(DailyStaticOilCanInventoryKey key);

    int updateByPrimaryKeySelective(DailyStaticOilCanInventory record);

    int updateByPrimaryKey(DailyStaticOilCanInventory record);

    List<DailyStaticOilCanInventory>selectBytrans(String trans);
}