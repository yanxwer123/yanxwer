package com.kld.gsm.center.dao;


import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.SysManageDict;

import java.util.HashMap;
import java.util.List;

@MysqlRepository
public interface SysManageDictDao {
    int deleteByPrimaryKey(Integer dictid);

    int insert(SysManageDict record);

    int insertSelective(SysManageDict record);

    SysManageDict selectByPrimaryKey(Integer dictid);

    int updateByPrimaryKeySelective(SysManageDict record);

    int updateByPrimaryKey(SysManageDict record);

    SysManageDict selectByCode(String code);

   List<SysManageDict> selectAll();

    int selectByName(String name);

    int  selectDictidByName(String name);

    List<String> selectSBByDictID(int DicID);

    List<HashMap<String,String>> selectYJLX();

    List<HashMap<String,String>> selectByParentId(String parentid);

    List<SysManageDict> selectbyVersion(Integer version);
}