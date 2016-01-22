package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_sysmanage_Depot;
@MysqlRepository
public interface oss_sysmanage_DepotMapper {
    int deleteByPrimaryKey(String ykId);

    int insert(oss_sysmanage_Depot record);

    int insertSelective(oss_sysmanage_Depot record);

    oss_sysmanage_Depot selectByPrimaryKey(String ykId);

    int updateByPrimaryKeySelective(oss_sysmanage_Depot record);

    int updateByPrimaryKey(oss_sysmanage_Depot record);

    int merge(oss_sysmanage_Depot record);
}