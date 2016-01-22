package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.oss_alarm_Inventory;

import javax.servlet.ServletOutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xhz on 2015/11/17.
 * 库存预警
 */
public interface AlarmInventoryService {
    int AddInventory(List<oss_alarm_Inventory> oss_alarm_inventories);
    List<HashMap<String,Object>> selectInventoryInfo(Integer page, Integer rows,String begin,String end,String oucode,String alarmtype);
    List<HashMap<String,Object>> selectInventoryAllInfo(String begin,String end,String oucode,String alarmtype);
    public void ExportIntenvory(List<HashMap<String,Object>> list,String [] titles,ServletOutputStream outputStream);

}
