package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_sysmanage_Area;

import java.util.List;

@MysqlRepository
public interface oss_sysmanage_AreaMapper {
    int insert(oss_sysmanage_Area record);

    int insertSelective(oss_sysmanage_Area record);

    int merge(oss_sysmanage_Area record);

    List<oss_sysmanage_Area> getAreasByCity(String cno);
}