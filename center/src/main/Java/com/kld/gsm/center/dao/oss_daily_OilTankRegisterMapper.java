package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_daily_OilTankRegister;
import com.kld.gsm.center.domain.oss_daily_OilTankRegisterKey;
import org.springframework.stereotype.Repository;

@MysqlRepository
public interface oss_daily_OilTankRegisterMapper {
    int deleteByPrimaryKey(oss_daily_OilTankRegisterKey key);

    int insert(oss_daily_OilTankRegister record);

    int insertSelective(oss_daily_OilTankRegister record);

    oss_daily_OilTankRegister selectByPrimaryKey(oss_daily_OilTankRegisterKey key);

    int updateByPrimaryKeySelective(oss_daily_OilTankRegister record);

    int updateByPrimaryKey(oss_daily_OilTankRegister record);
}