package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.DailyTankShift;
import com.kld.gsm.ATG.domain.DailyTankShiftKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MySqlRepository
public interface DailyTankShiftDao {
    int deleteByPrimaryKey(DailyTankShiftKey key);

    int insert(DailyTankShift record);

    int insertSelective(DailyTankShift record);

    DailyTankShift selectByPrimaryKey(DailyTankShiftKey key);

    int updateByPrimaryKeySelective(DailyTankShift record);

    int updateByPrimaryKey(DailyTankShift record);

    List<DailyTankShift> selectByShift(String shift);

    String findByShift(Map map);

    List<DailyTankShift> selectByTrans(String stauts);

    //期初-期末库存
    List<HashMap> findToOilL(String shift);
    List<DailyTankShift> findBetweenShift(Map map);
    int updateByShift(Map map);
    int updateByShift(DailyTankShift dailyTankShift);

    List<DailyTankShift> selectByAccountIsNull(String shift);

    //查询班次号 条件：未日结 已班结
    String findOilNoAndShift();
  }