package com.kld.gsm.ATG.dao;


import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.SysManageIquidCubage;
import com.kld.gsm.ATG.domain.SysManageIquidCubageKey;
@MySqlRepository
public interface SysManageIquidCubageDao {
    int deleteByPrimaryKey(SysManageIquidCubageKey key);

    int insert(SysManageIquidCubage record);

    int insertSelective(SysManageIquidCubage record);

    SysManageIquidCubage selectByPrimaryKey(SysManageIquidCubageKey key);

    int updateByPrimaryKeySelective(SysManageIquidCubage record);

    int updateByPrimaryKey(SysManageIquidCubage record);
}