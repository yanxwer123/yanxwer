package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_daily_TradeAccount;
import com.kld.gsm.center.domain.oss_daily_TradeAccountKey;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@MysqlRepository
public interface oss_daily_TradeAccountMapper {
    int deleteByPrimaryKey(oss_daily_TradeAccountKey key);

    int insert(oss_daily_TradeAccount record);

    int merge(oss_daily_TradeAccount record);

    int insertSelective(oss_daily_TradeAccount record);

    oss_daily_TradeAccount selectByPrimaryKey(oss_daily_TradeAccountKey key);

    int updateByPrimaryKeySelective(oss_daily_TradeAccount record);

    int updateByPrimaryKey(oss_daily_TradeAccount record);
    List<HashMap<String,Object>> selectOilLiter(HashMap map);
    List<HashMap<String,Object>> selectTqCount(HashMap map);
    List<HashMap<String,Object>> selectOilLiterByName(HashMap map);
    List<HashMap<String,Object>> selectNewLiter(HashMap map);
}