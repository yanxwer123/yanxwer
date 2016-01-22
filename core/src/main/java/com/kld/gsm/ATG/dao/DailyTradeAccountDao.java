package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.DailyTradeAccount;
import com.kld.gsm.ATG.domain.DailyTradeAccountKey;
import com.kld.gsm.ATG.domain.OilVouch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MySqlRepository
public interface DailyTradeAccountDao {
    int deleteByPrimaryKey(DailyTradeAccountKey key);

    int insert(DailyTradeAccount record);

    int insertSelective(DailyTradeAccount record);

    DailyTradeAccount selectByPrimaryKey(DailyTradeAccountKey key);

    int updateByPrimaryKeySelective(DailyTradeAccount record);

    int updateByPrimaryKey(DailyTradeAccount record);

    List<DailyTradeAccount> findNotRecieved();
    
    List<DailyTradeAccount> query(HashMap map);

    int updateIsRecieved(DailyTradeAccount record);

    Map selectDuringSales(Map params);

    List<DailyTradeAccount> selectByTrans(String stauts);


    List selectFyxx(String shift, String oilNo);

    List queryFyxxOilNo(String shift);

    DailyTradeAccount selectByOilGun(String oilGun);


    Map GetSaleOilLiterByCanNo(Map params);

   ArrayList<String> findLikeShift(HashMap shiftmap);

    int updateByKey(OilVouch oil);

    String findLiter(Map<String,String> map);

    Double selectRangeOil(Map<String,String> map);

    int updateByShift(Map<String,String> map);

    //已班结未日结的付油量
    List<Map<String,Object>> getLiter(String shift);
}