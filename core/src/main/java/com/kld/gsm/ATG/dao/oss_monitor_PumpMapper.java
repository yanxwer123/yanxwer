package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.oss_monitor_Pump;

import java.util.List;
@MySqlRepository
public interface oss_monitor_PumpMapper {
    int deleteByPrimaryKey(Integer gunno);

    int insert(oss_monitor_Pump record);

    int insertSelective(oss_monitor_Pump record);

    oss_monitor_Pump selectByPrimaryKey(Integer gunno);

    int updateByPrimaryKeySelective(oss_monitor_Pump record);

    int updateByPrimaryKey(oss_monitor_Pump record);

    int merge(oss_monitor_Pump record);

    List<oss_monitor_Pump> selectAll();
}