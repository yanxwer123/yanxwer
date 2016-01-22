package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_sysmanage_timeSaleOut;
import com.kld.gsm.center.domain.oss_sysmanage_timeSaleOutKey;

@MysqlRepository
public interface oss_sysmanage_timeSaleOutMapper {
    int deleteByPrimaryKey(oss_sysmanage_timeSaleOutKey key);

    int insert(oss_sysmanage_timeSaleOut record);

    int insertSelective(oss_sysmanage_timeSaleOut record);

    oss_sysmanage_timeSaleOut selectByPrimaryKey(oss_sysmanage_timeSaleOutKey key);

    int updateByPrimaryKeySelective(oss_sysmanage_timeSaleOut record);

    int updateByPrimaryKey(oss_sysmanage_timeSaleOut record);
}