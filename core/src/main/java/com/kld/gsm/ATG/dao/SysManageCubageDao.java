package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.SysManageCubage;
import com.kld.gsm.ATG.domain.SysManageCubageKey;

import java.util.List;

@MySqlRepository
public interface SysManageCubageDao {
    int deleteByPrimaryKey(SysManageCubageKey key);

    int insert(SysManageCubage record);

    int insertSelective(SysManageCubage record);

    SysManageCubage selectByPrimaryKey(SysManageCubageKey key);

    int updateByPrimaryKeySelective(SysManageCubage record);

    int updateByPrimaryKey(SysManageCubage record);
    List<SysManageCubage> selectAll();

    List<SysManageCubage> selectByKey(int key);

    List<SysManageCubage>selectByKeyAll(int key);

    List<SysManageCubage> selectByTrans(String trans);

    int deleteAll();

    List<SysManageCubage> selectCubageInused();

    int updateUnused(SysManageCubage record);

    List<SysManageCubage> getMaxVersion();
}