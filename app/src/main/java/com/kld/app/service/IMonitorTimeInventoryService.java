package com.kld.app.service;

import java.util.List;

import com.kld.gsm.ATG.domain.MonitorTimeInventory;


public interface IMonitorTimeInventoryService {
    List<MonitorTimeInventory> selectAllOilCanInfoByOilNo(String oilNo);
    
    MonitorTimeInventory selectBeginDataByCanNo(Integer oilCan);
}
