package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.DailyOilTankRegister;
import com.kld.gsm.ATG.domain.DailyOilTankRegisterKey;
import com.kld.gsm.ATG.domain.DailyOpotDailyStatement;

import java.util.List;

@MySqlRepository
public interface DailyOilTankRegisterDao {
    int deleteByPrimaryKey(DailyOilTankRegisterKey key);

    int insert(com.kld.gsm.ATG.domain.DailyOilTankRegister record);

    int insertSelective(com.kld.gsm.ATG.domain.DailyOilTankRegister record);

    com.kld.gsm.ATG.domain.DailyOilTankRegister selectByPrimaryKey(DailyOilTankRegisterKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.DailyOilTankRegister record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.DailyOilTankRegister record);

    List<DailyOilTankRegister> selectByTrans(String stauts);
}