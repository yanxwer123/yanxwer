package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_sysmanage_OilMachineInfo;
import com.kld.gsm.center.domain.oss_sysmanage_OilMachineInfoKey;
import org.springframework.stereotype.Repository;

@MysqlRepository
public interface oss_sysmanage_OilMachineInfoMapper {
    int deleteByPrimaryKey(oss_sysmanage_OilMachineInfoKey key);

    int insert(oss_sysmanage_OilMachineInfo record);

    int insertSelective(oss_sysmanage_OilMachineInfo record);

    oss_sysmanage_OilMachineInfo selectByPrimaryKey(oss_sysmanage_OilMachineInfoKey key);

    int updateByPrimaryKeySelective(oss_sysmanage_OilMachineInfo record);

    int updateByPrimaryKey(oss_sysmanage_OilMachineInfo record);

    int merge(oss_sysmanage_OilMachineInfo record);
}