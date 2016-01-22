package com.kld.gsm.ATG.dao;


import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.SysManageIquidCubageInfo;
import com.kld.gsm.ATG.domain.SysManageIquidCubageInfoKey;
@MySqlRepository
public interface SysManageIquidCubageInfoDao {
    int deleteByPrimaryKey(SysManageIquidCubageInfoKey key);

    int insert(SysManageIquidCubageInfo record);

    int insertSelective(SysManageIquidCubageInfo record);

    SysManageIquidCubageInfo selectByPrimaryKey(SysManageIquidCubageInfoKey key);

    int updateByPrimaryKeySelective(SysManageIquidCubageInfo record);

    int updateByPrimaryKey(SysManageIquidCubageInfo record);
}