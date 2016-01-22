package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.oss_daily_TradeInventory;

import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 交易库存表
 */
public interface DTradeInventoryService {
    int AddTradeInventory(List<oss_daily_TradeInventory> oss_daily_tradeInventories);
}
