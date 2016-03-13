package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.Sys_role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jw on 2015/7/30.
 */
public interface Sys_roleService {


    List<Sys_role> getRoleList(Map<String, Object> map);

    List<Sys_role> selectAll();

    List<Sys_role> getSysRoleFunList(Map<String, Object> map);

    int delete(String rolecode);

    int insertRow(HashMap map);

    int updateRow(HashMap map);

    int updateSysRoleByCode(Sys_role sys_role);

    int insert(Sys_role sys_role);

    List<HashMap<String,Object>>  getSysRolePageList(Integer intPage,Integer intPageSize,String name);

    List<HashMap<String,Object>> getSysRoleList(String name);



    /**
     * 通过rolecode查询是否有重复数据
     * @param sys_role
     * @return
     */
    Sys_role selectSysRoleByRolecode(Sys_role sys_role);
}
