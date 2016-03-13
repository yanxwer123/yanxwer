package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_sysmanage_AlarmParameter;
import com.kld.gsm.center.domain.oss_sysmanage_AlarmParameterKey;

import java.util.List;
import java.util.Map;

@MysqlRepository
public interface oss_sysmanage_AlarmParameterMapper {
    int deleteByPrimaryKey(oss_sysmanage_AlarmParameterKey key);

    int insert(oss_sysmanage_AlarmParameter record);

    int insertSelective(oss_sysmanage_AlarmParameter record);

    oss_sysmanage_AlarmParameter selectByPrimaryKey(oss_sysmanage_AlarmParameterKey key);

    int updateByPrimaryKeySelective(oss_sysmanage_AlarmParameter record);

    int updateByPrimaryKey(oss_sysmanage_AlarmParameter record);

    int merge(oss_sysmanage_AlarmParameter record);

    List<oss_sysmanage_AlarmParameter> selectByNodeNo(String nodeno);

    List<oss_sysmanage_AlarmParameter> findByOUCode(String oucode);
    List<oss_sysmanage_AlarmParameter> findByOUCodePage(Map map);
}