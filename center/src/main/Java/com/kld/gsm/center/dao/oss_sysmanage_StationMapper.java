package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_sysmanage_Station;

import java.util.List;

@MysqlRepository
public interface oss_sysmanage_StationMapper {
    int deleteByPrimaryKey(String stationCode);

    int insert(oss_sysmanage_Station record);

    int insertSelective(oss_sysmanage_Station record);

    oss_sysmanage_Station selectByPrimaryKey(String stationCode);

    int updateByPrimaryKeySelective(oss_sysmanage_Station record);

    int updateByPrimaryKey(oss_sysmanage_Station record);

    int merge(oss_sysmanage_Station record);

    oss_sysmanage_Station selectByLsgcandKcdd(String lsgc,String kcdd);

    List<oss_sysmanage_Station> getStationsByArea(String ano);
}