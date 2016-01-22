package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.DailyTradeAccount;
import com.kld.gsm.ATG.domain.MonitorTankOil;

import java.util.List;
@MySqlRepository
public interface MonitorTankOilMapper {
    int deleteByPrimaryKey(String id);

    int insert(MonitorTankOil record);

    int insertSelective(MonitorTankOil record);

    MonitorTankOil selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MonitorTankOil record);

    int updateByPrimaryKey(MonitorTankOil record);

    List<MonitorTankOil> selectByTrans(String stauts);
}