package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.DailyDailyBalance;
import com.kld.gsm.ATG.domain.DailyDailyBalanceKey;

import java.util.HashMap;
import java.util.List;

@MySqlRepository
public interface DailyDailyBalanceDao {
    int deleteByPrimaryKey(DailyDailyBalanceKey key);

    int insert(com.kld.gsm.ATG.domain.DailyDailyBalance record);

    int insertSelective(com.kld.gsm.ATG.domain.DailyDailyBalance record);

    com.kld.gsm.ATG.domain.DailyDailyBalance selectByPrimaryKey(DailyDailyBalanceKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.DailyDailyBalance record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.DailyDailyBalance record);

    List<DailyDailyBalance> selectByTrans(String stauts);

    List<DailyDailyBalance> selectByDate(HashMap map);

    //查看创建时间最新的期初库存
    String findRealStock(String oilno);
}