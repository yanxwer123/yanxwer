package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_alarm_SaleOut;
import com.kld.gsm.center.domain.oss_alarm_SaleOutKey;

import java.util.HashMap;
import java.util.List;

@MysqlRepository
public interface oss_alarm_SaleOutMapper {
    int deleteByPrimaryKey(oss_alarm_SaleOutKey key);

    int insert(oss_alarm_SaleOut record);

    int insertSelective(oss_alarm_SaleOut record);

    oss_alarm_SaleOut selectByPrimaryKey(oss_alarm_SaleOutKey key);

    int updateByPrimaryKeySelective(oss_alarm_SaleOut record);

    int updateByPrimaryKey(oss_alarm_SaleOut record);

    List<HashMap<String,Object>>  selectSaleOut(HashMap hashmap);
    List<HashMap<String,Object>> queryPrepayPageList(HashMap hashmap);
    int queryPrepayCount(HashMap hashmap);

    List<HashMap<String,Object>> queryJYPrepayPageList(HashMap hashmap);
    List<HashMap<String,Object>> queryJYCountPrepayPageList(HashMap hashmap);
}