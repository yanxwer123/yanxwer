package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_daily_StationShiftInfo;
import com.kld.gsm.center.domain.oss_daily_StationShiftInfoKey;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@MysqlRepository
public interface oss_daily_StationShiftInfoMapper {
    int deleteByPrimaryKey(oss_daily_StationShiftInfoKey key);

    int insert(oss_daily_StationShiftInfo record);

    int insertSelective(oss_daily_StationShiftInfo record);

    oss_daily_StationShiftInfo selectByPrimaryKey(oss_daily_StationShiftInfoKey key);

    int updateByPrimaryKeySelective(oss_daily_StationShiftInfo record);

    int updateByPrimaryKey(oss_daily_StationShiftInfo record);
    List<HashMap<String,Object>> selectShiftInfo(HashMap<String,Object> map);
    List<HashMap<String,Object>> selectPageShiftInfo(HashMap<String,Object> map);

}