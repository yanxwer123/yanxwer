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


    List<HashMap> findToOilL(String shift);
    List<DailyTankShift> findBetweenShift(Map map);
    int updateByShift(Map map);
    int updateByShift(DailyTankShift dailyTankShift);

    List<DailyTankShift> selectByAccountIsNull(String shift);


    String findOilNoAndShift();

    String findByOilcan(Map map);

    String findToOilLByOilNo(Map map);
}