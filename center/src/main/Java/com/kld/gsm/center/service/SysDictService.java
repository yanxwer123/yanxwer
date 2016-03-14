package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.SysManageDict;

import java.util.HashMap;
import java.util.List;


/**
 * Created by fangzhun on 2015/12/4.
 */
public interface SysDictService {
    //获取配送建议阀值
    int selectPSYJFZ();
    List<HashMap<String,String>> selectYJLX();
    SysManageDict selectByCode(String code);
    List<HashMap<String,String>> selectByParentId(String parentid);

    List<SysManageDict> selectbyVersion(Integer version);
    SysManageDict selectByPrimaryKey(int id);
    List<SysManageDict> selectAll();
    int insertSelective(SysManageDict sysManageDict);
    int updateByPrimaryKeySelective(SysManageDict sysManageDict);
    int deleteByPrimaryKey(Integer id);
    List<HashMap<String,Object>>  getDictList(Integer intPage,Integer intPageSize,Integer dictID,Integer parentID,String name);

    List<HashMap<String,Object>> getDictAllList(Integer dictID,Integer parentID,String name);

    int insertRow(HashMap map);

    int updateRow(HashMap map);
}
