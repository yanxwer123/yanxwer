package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.oss_daily_DailyBalance;

import javax.servlet.ServletOutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 日平衡表
 */
public interface DDailyBalanceService {
    int AddDailyBalance(List<oss_daily_DailyBalance> accDeliveryBillLostList);
    List<HashMap<String,Object>> selectbalancebywhere(String start,String end,String oucode);
    List<oss_daily_DailyBalance> selectbalance(String start,String end,String oucode);

    List<HashMap<String,Object>>  querypage(Integer intPage, Integer intPageSize,String oucode,String starttime,String endtime);
    public void ExportBalance(List<HashMap<String,Object>> list,String [] titles,ServletOutputStream outputStream);
}
