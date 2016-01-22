package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.dao.DailyTradeInventoryDao;
import com.kld.gsm.ATG.domain.DailyTradeInventory;
import com.kld.gsm.coord.servcie.IDailyTradeInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("all")
@Service("DailyTradeInventory")
public class DailyTradeInventoryServiceImpl implements IDailyTradeInventoryService {

    @Autowired
    private DailyTradeInventoryDao dailyTradeInventoryDao;

    @Override
    public int insert(DailyTradeInventory dailyTradeInventory) {
        return dailyTradeInventoryDao.insertSelective(dailyTradeInventory);
    }
}
