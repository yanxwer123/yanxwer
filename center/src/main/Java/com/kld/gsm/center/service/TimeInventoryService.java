package com.kld.gsm.center.service;


import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.oss_monitor_Inventory;
import com.kld.gsm.center.domain.oss_monitor_TimeInventory;

import java.util.HashMap;
import java.util.List;

/*
Created BY niyang
Created Date 2015/11/17
*/
public interface TimeInventoryService {
     int AddTimeInventory(List<oss_monitor_TimeInventory> oss_monitor_timeInventories) ;
     int InsertInventory(List<oss_monitor_Inventory> oss_monitor_inventories);
     List<HashMap<String,Object>> selectTimeInventoryInfo(Integer page, Integer rows,String oucode);
     int selectTimeInventoryCount(String oucode);
     List<HashMap<String,Object>> selectInventory(String oiltype,String oucode);
     List<HashMap<String,Object>> selectUploadInventory(String oucode);
}

