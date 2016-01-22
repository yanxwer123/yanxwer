package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_sysmanage_City;

import java.util.List;

@MysqlRepository
public interface oss_sysmanage_CityMapper {
    int deleteByPrimaryKey(String code);

    int insert(oss_sysmanage_City record);

    int insertSelective(oss_sysmanage_City record);

    oss_sysmanage_City selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(oss_sysmanage_City record);

    int updateByPrimaryKey(oss_sysmanage_City record);

    int merge(oss_sysmanage_City record);

    List<oss_sysmanage_City> getAll();
}