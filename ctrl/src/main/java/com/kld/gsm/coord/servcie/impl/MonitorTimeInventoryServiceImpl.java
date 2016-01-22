package com.kld.gsm.coord.servcie.impl;


import com.kld.gsm.ATG.dao.MonitorTimeInventoryDao;
import com.kld.gsm.ATG.domain.MonitorTimeInventory;
import com.kld.gsm.coord.servcie.IMonitorTimeInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yinzhiguang on 2015/11/5.
 */
@SuppressWarnings("all")
@Service
public class MonitorTimeInventoryServiceImpl implements IMonitorTimeInventoryService {

    @Autowired
    private MonitorTimeInventoryDao monitorTimeInventoryDao;

    @Override
    public int insert(MonitorTimeInventory monitorTimeInventory) {
        return monitorTimeInventoryDao.insert(monitorTimeInventory);
    }
}
