package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.domain.oss_daily_TradeAccount;
import com.kld.gsm.center.service.DTradeAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kld.gsm.center.dao.oss_daily_TradeAccountMapper;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 交易加油流水表
 */
@Service("DTradeAccountService")
public class DTradeAccountServiceImpl implements DTradeAccountService {

    @Resource
    private oss_daily_TradeAccountMapper ossDailyTradeAccountMapper;
    @Transactional(rollbackFor=Exception.class)
    public int AddTradeAccount(List<oss_daily_TradeAccount> oss_daily_tradeAccounts) {
        for (oss_daily_TradeAccount item:oss_daily_tradeAccounts)
        {
            ossDailyTradeAccountMapper.merge(item);
        }
        return 1;
    }

    @Override
    public List<HashMap<String, Object>> selectOilLiter(String oiltype,String start, String end, String oucode) {
        HashMap map=new HashMap();
        map.put("oiltype",oiltype);
        map.put("start",start);
        map.put("end",end);
        if (oucode!=null&&oucode!="") {
            map.put("oucode", oucode+"%");
        }
        else
        {
            map.put("oucode", oucode);
        }
        return ossDailyTradeAccountMapper.selectOilLiter(map);
    }


    @Override
    public List<HashMap<String, Object>> selectTqCount(String start,String oiltype, String end, String oucode) {
        HashMap map=new HashMap();
        map.put("start",start);
        map.put("end",end);
        map.put("oiltype",oiltype);
        if (oucode!=null&&oucode!="") {
            map.put("oucode", oucode+"%");
        }
        else
        {
            map.put("oucode", oucode);
        }
        return ossDailyTradeAccountMapper.selectTqCount(map);
    }

    @Override
    public List<HashMap<String, Object>> selectOilLiterByName(String oilname,String start, String end, String oucode) {
        HashMap map=new HashMap();
        map.put("oilname",oilname+"#%");
        map.put("start",start);
        map.put("end",end);
        if (oucode!=null&&oucode!="") {
            map.put("oucode", oucode+"%");
        }
        else
        {
            map.put("oucode", oucode);
        }
        return ossDailyTradeAccountMapper.selectOilLiterByName(map);
    }


    @Override
    public List<HashMap<String, Object>> selectNewLiter(String oiltype,String type,String oucode) {
        HashMap map=new HashMap();
        map.put("oiltype",oiltype);
        map.put("type",type);
        if (oucode!=null&&oucode!="") {
            map.put("oucode", oucode+"%");
        }
        else
        {
            map.put("oucode", oucode);
        }
        return ossDailyTradeAccountMapper.selectNewLiter(map);
    }
}
