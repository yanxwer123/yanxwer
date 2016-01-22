package com.kld.gsm.ATG.dao;


import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.SysManageDict;

import java.util.List;
import java.util.Map;

@MySqlRepository
public interface SysManageDictDao {
    int deleteByPrimaryKey(Integer dictid);

    int insert(SysManageDict record);

    int insertSelective(SysManageDict record);

    SysManageDict selectByPrimaryKey(Integer dictid);

    int updateByPrimaryKeySelective(SysManageDict record);

    int updateByPrimaryKey(SysManageDict record);

    SysManageDict selectByCode(String code);

   List<SysManageDict> selectAll();

    String selectByName(String name);

    int  selectDictidByName(String name);

    String selectBySort(Integer sort);

    String selectBySort1(Integer sort);

    String selectBySort2(Integer sort);

    List<String> selectSBByDictID(int DicID);

    List<String>  selectYJLXByDictID();

    Map getversion();
    int merge(SysManageDict record);

    List<SysManageDict> selectByParentCode(String pcode);
}