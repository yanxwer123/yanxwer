package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_sysmanage_AlarmPlus;
import com.kld.gsm.center.domain.oss_sysmanage_AlarmPlusKey;
@MysqlRepository
public interface oss_sysmanage_AlarmPlusMapper {
    int deleteByPrimaryKey(oss_sysmanage_AlarmPlusKey key);

    int insert(oss_sysmanage_AlarmPlus record);

    int insertSelective(oss_sysmanage_AlarmPlus record);

    oss_sysmanage_AlarmPlus selectByPrimaryKey(oss_sysmanage_AlarmPlusKey key);

    int updateByPrimaryKeySelective(oss_sysmanage_AlarmPlus record);

    int updateByPrimaryKey(oss_sysmanage_AlarmPlus record);
}