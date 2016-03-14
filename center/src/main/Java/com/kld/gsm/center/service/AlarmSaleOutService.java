package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.oss_alarm_SaleOut;

import javax.servlet.ServletOutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 */
public interface AlarmSaleOutService {
    int AddSaleOut(List<oss_alarm_SaleOut> oss_alarm_saleOuts);
    List<HashMap<String,Object>> selectSaleOut(HashMap hashmap);
    List<HashMap<String,Object>>  queryPrepayPageList(Integer pageNo, Integer pageSize,String oucode,String StartAlarmTime,String EndAlarmTime);
     int queryPrepayCount(String oucode,String StartAlarmTime,String EndAlarmTime);
    List<HashMap<String,Object>>  queryJYPrepayPageList(Integer pageNo, Integer pageSize,String oucode,String StartAlarmTime,String EndAlarmTime);
    List<HashMap<String,Object>> queryJYCountPrepayPageList(String oucode,String StartAlarmTime,String EndAlarmTime);
    public void ExcelSaleOut(List<HashMap<String,Object>> list,String [] titles,ServletOutputStream outputStream);

}
