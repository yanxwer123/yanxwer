package com.kld.gsm.center.dao;


import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.SysManageDict;

import java.util.HashMap;
import java.util.List;

@MysqlRepository
public interface SysManageDictDao {
    //删除
    int deleteByPrimaryKey(Integer dictid);
    //添加
    int insertSelective(SysManageDict sysManageDict);
    //查询
    SysManageDict selectByPrimaryKey(Integer id);

    List<SysManageDict> selectAll();
    //修改
    int updateByPrimaryKeySelective(SysManageDict sysManageDict);

    int insert(SysManageDict record);

    int updateByPrimaryKey(SysManageDict record);

    SysManageDict selectByCode(String code);



    int selectByName(String name);

    int  selectDictidByName(String name);

    List<String> selectSBByDictID(int DicID);

    List<HashMap<String,String>> selectYJLX();

    List<HashMap<String,String>> selectByParentId(String parentid);

    List<SysManageDict> selectbyVersion(Integer version);

    List<HashMap<String,Object>>  getDictList(HashMap map);

    List<HashMap<String,Object>> getDictAllList(HashMap map);

    int insertRow(HashMap map);

    int updateRow(HashMap map);
}