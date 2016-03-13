package com.kld.app.service;

import com.kld.gsm.ATG.domain.*;

import java.util.*;


/**
 * 交易加油流水
 */
public interface DailyTradeAccountService {
	int deleteByPrimaryKey(DailyTradeAccountKey key);

    int insert(DailyTradeAccount record);

    int insertSelective(DailyTradeAccount record);

    DailyTradeAccount selectByPrimaryKey(DailyTradeAccountKey key);

    int updateByPrimaryKeySelective(DailyTradeAccount record);

    int updateByPrimaryKey(DailyTradeAccount record);

    List<DailyTradeAccount> findNotRecieved();

    List<DailyTradeAccount> query(HashMap map);

    public List<SysManageOilGunInfo> selectAllOilGun();

    int updateIsRecieved(DailyTradeAccount record);

    Map selectDuringSales(Integer oilCan,Date begin, Date end);

    //int updateByKey(List list);

    Map GetSaleOilSumByCanNoAndDate(String canno,Date st,Date et);

    ArrayList<String> findLikeShift(HashMap shiftmap);

    //  根据油罐-开始时间-结束时间查询付油量
    String findByOilGun(String oilgun, Date begintime, Date endtime);

    //  根据油品-开始时间-结束时间查询付油量
    String findByOilNo(String oilno, Date begintime, Date endtime);

}
