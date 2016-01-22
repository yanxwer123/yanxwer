package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.oss_daily_TradeAccount;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 交易加油流水表
 */
public interface DTradeAccountService {
    int AddTradeAccount(List<oss_daily_TradeAccount> oss_daily_tradeAccounts);
    List<HashMap<String,Object>> selectOilLiter(String oiltype,String start,String end,String oucode);
    List<HashMap<String,Object>> selectTqCount(String start,String oiltype,String end,String oucode);
    List<HashMap<String,Object>> selectOilLiterByName(String oilname,String start,String end,String oucode);
    List<HashMap<String,Object>> selectNewLiter(String oiltype,String type,String oucode);
}
