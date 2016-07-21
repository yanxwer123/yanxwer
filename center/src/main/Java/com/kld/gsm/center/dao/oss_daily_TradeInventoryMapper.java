package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_daily_TradeInventory;
import com.kld.gsm.center.domain.oss_daily_TradeInventoryKey;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@MysqlRepository
public interface oss_daily_TradeInventoryMapper {
    int deleteByPrimaryKey(oss_daily_TradeInventoryKey key);

    int insert(oss_daily_TradeInventory record);

    int update(oss_daily_TradeInventory record);

    int insertSelective(oss_daily_TradeInventory record);

    oss_daily_TradeInventory selectByPrimaryKey(oss_daily_TradeInventoryKey key);

    int updateByPrimaryKeySelective(oss_daily_TradeInventory record);

    int updateByPrimaryKey(oss_daily_TradeInventory record);

}