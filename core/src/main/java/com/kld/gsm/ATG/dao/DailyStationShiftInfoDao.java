package com.kld.gsm.ATG.dao;

import java.util.HashMap;
import java.util.List;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.DailyStationShiftInfo;
@MySqlRepository
public interface DailyStationShiftInfoDao {
    int deleteByPrimaryKey(Integer shift);

    int insert(com.kld.gsm.ATG.domain.DailyStationShiftInfo record);

    int insertSelective(com.kld.gsm.ATG.domain.DailyStationShiftInfo record);

    com.kld.gsm.ATG.domain.DailyStationShiftInfo selectByPrimaryKey(Integer shift);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.DailyStationShiftInfo record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.DailyStationShiftInfo record);

	List<DailyStationShiftInfo> selectByDate(HashMap map);

    List<DailyStationShiftInfo> selectByTrans(String stauts);
}