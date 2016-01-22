package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.SysManageCubageInfo;
import com.kld.gsm.ATG.domain.SysManageCubageInfoKey;

import java.util.List;
import java.util.Map;

@MySqlRepository
public interface SysManageCubageInfoDao {
    int deleteByPrimaryKey(SysManageCubageInfoKey key);

    int insert(SysManageCubageInfo record);

    int insertSelective(SysManageCubageInfo record);

    SysManageCubageInfo selectByPrimaryKey(SysManageCubageInfoKey key);

    int updateByPrimaryKeySelective(SysManageCubageInfo record);
    List<SysManageCubageInfo> selectByKey(SysManageCubageInfoKey key);

    int updateByPrimaryKey(SysManageCubageInfo record);

    List<SysManageCubageInfo> selectByTrans(String trans);

    int deleteAll();

    List<SysManageCubageInfo> selectCubageInfo(SysManageCubageInfoKey key);

    List<SysManageCubageInfo> selectCubageInfoByPar(Map map);

}