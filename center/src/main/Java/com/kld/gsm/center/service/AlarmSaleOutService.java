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

    List<HashMap<String,Object>>  queryPrepayPageList(Integer intPage, Integer intPageSize,String OilNo,String StartAlarmTime,String EndAlarmTime,String PSJY, Integer PSJYFZ);
     int queryPrepayCount(String OilNo,String StartAlarmTime,String EndAlarmTime);

    List<HashMap<String,Object>>  queryJYPrepayPageList(Integer pageNo, Integer pageSize,String OilNo,String StartAlarmTime,String EndAlarmTime,String PSJY,Integer PSJYFZ,String oucode);
    List<HashMap<String,Object>> queryJYCountPrepayPageList(String OilNo,String StartAlarmTime,String EndAlarmTime,String PSJY,Integer PSJYFZ,String oucode);
    public void ExcelSaleOut(List<HashMap<String,Object>> list,String [] titles,ServletOutputStream outputStream);

}
