package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.SysManageAlarmParameter;

import java.util.List;

@MySqlRepository
public interface SysManageAlarmParameterDao {
    int deleteByPrimaryKey(Integer oilcan);

    int insertByKey(Integer oilcan);

    int insert(SysManageAlarmParameter record);

    int insertSelective(SysManageAlarmParameter record);

    SysManageAlarmParameter selectByPrimaryKey(Integer oilcan);

    int updateByPrimaryKeySelective(SysManageAlarmParameter record);

    int updateByPrimaryKey(SysManageAlarmParameter record);

    List<SysManageAlarmParameter> selectAll();

    List<SysManageAlarmParameter> selectAlarmParameter();

    List<SysManageAlarmParameter> selectByTrans(String Trans);

    int merge(SysManageAlarmParameter record);

}