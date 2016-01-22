package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_sysmanage_OilGunInfo;
import com.kld.gsm.center.domain.oss_sysmanage_OilGunInfoKey;
import org.springframework.stereotype.Repository;

@MysqlRepository
public interface oss_sysmanage_OilGunInfoMapper {
    int deleteByPrimaryKey(oss_sysmanage_OilGunInfoKey key);

    int insert(oss_sysmanage_OilGunInfo record);

    int insertSelective(oss_sysmanage_OilGunInfo record);

    oss_sysmanage_OilGunInfo selectByPrimaryKey(oss_sysmanage_OilGunInfoKey key);

    int updateByPrimaryKeySelective(oss_sysmanage_OilGunInfo record);

    int updateByPrimaryKey(oss_sysmanage_OilGunInfo record);

    int merge(oss_sysmanage_OilGunInfo record);
}