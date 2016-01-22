package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.oss_monitor_InventoryMapper;
import com.kld.gsm.center.dao.oss_monitor_TimeInventoryMapper;
import com.kld.gsm.center.domain.*;
import com.kld.gsm.center.service.TimeInventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;



/*
Created BY ny
Created Date 2015/11/17
*/
@Service("TimeInventoryService")
public class TimeInventoryServiceImpl implements TimeInventoryService {

    @Resource
    private oss_monitor_TimeInventoryMapper ossMonitorTimeInventoryMapper;

    @Transactional(rollbackFor=Exception.class)
    public int AddTimeInventory(List<oss_monitor_TimeInventory> oss_monitor_timeInventories) {
        for(oss_monitor_TimeInventory item:oss_monitor_timeInventories){
            ossMonitorTimeInventoryMapper.insert(item);
        }
        return 1;
    }

    @Resource
    private oss_monitor_InventoryMapper ossMonitorInventoryMapper;

    @Transactional(rollbackFor = Exception.class)
    public int InsertInventory(List<oss_monitor_Inventory> oss_monitor_inventories) {
        for (oss_monitor_Inventory item:oss_monitor_inventories)
        {
            ossMonitorInventoryMapper.insert(item);
        }
        return 1;
    }


    @Override
    public List<HashMap<String,Object>> selectTimeInventoryInfo(Integer page, Integer rows,String oucode) {
        if (page != null && rows != null) {
            page = page < 1 ? 1 : page;
            int firstRow = (page - 1) * rows;
            HashMap hashMap = new HashMap();
            hashMap.put("firstRow", firstRow);
            hashMap.put("pageSize", rows);
            hashMap.put("oucode", oucode + "%");
            return ossMonitorTimeInventoryMapper.selectTimeInventoryInfo(hashMap);

        }
        return null;
    }


    @Override
    public List<HashMap<String, Object>> selectInventory(String oiltype, String oucode) {
        HashMap map=new HashMap();
        map.put("oiltype",oiltype);
        if (oucode!=null&&oucode!="") {
            map.put("oucode", oucode+"%");
        }
        else
        {
            map.put("oucode", oucode);
        }
        return ossMonitorTimeInventoryMapper.selectInventory(map);
    }

    @Override
    public List<HashMap<String, Object>> selectUploadInventory(String oucode) {
        HashMap map=new HashMap();
        if (oucode!=null&&oucode!="") {
            map.put("oucode", oucode+"%");
        }
        else
        {
            map.put("oucode", oucode);
        }
        return ossMonitorTimeInventoryMapper.selectUploadInventory(map);
    }
}
