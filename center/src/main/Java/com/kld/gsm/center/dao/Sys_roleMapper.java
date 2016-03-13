package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.Sys_role;

import java.util.HashMap;
import java.util.List;

@MysqlRepository
public interface Sys_roleMapper {
    int insert(Sys_role record);

    int insertSelective(Sys_role record);

    int delete(String rolecode);

    int insertRow(HashMap map);

    int updateRow(HashMap map);

    List<Sys_role> selectAll();

    List<HashMap<String,Object>> getSysRolePageList(HashMap map);

    List<HashMap<String,Object>> getSysRoleList(HashMap map);

}