package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.domain.oss_daily_TradeInventory;
import com.kld.gsm.center.service.DTradeInventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kld.gsm.center.dao.oss_daily_TradeInventoryMapper;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 交易库存表
 */
@Service("DTradeInventoryService")
public class DTradeInventoryServiceImpl implements DTradeInventoryService {
    @Resource
    private oss_daily_TradeInventoryMapper ossDailyTradeInventoryMapper;
    @Transactional(rollbackFor=Exception.class)
    public int AddTradeInventory(List<oss_daily_TradeInventory> oss_daily_tradeInventories) {
        for (oss_daily_TradeInventory item:oss_daily_tradeInventories)
        {
            ossDailyTradeInventoryMapper.insert(item);
        }
        return 1;
    }
}
