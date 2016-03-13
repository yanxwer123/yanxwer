package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.Sys_rolefuncrel;
import com.kld.gsm.center.domain.Sys_userrole;

import java.util.List;
import java.util.Map;

/**
 * Created by jw on 2015/7/30.
 */
public interface Sys_rolefuncrelService {


    int deleteRoleFuncByRolecode(String rolecode);

    int insert(Sys_rolefuncrel record);

    List<Sys_rolefuncrel> selectAll();

    List<Sys_rolefuncrel>  selectByrolecode(String rolecode);

    List<Sys_rolefuncrel>  selectRCBycode(String delfunccode);

    List<Sys_rolefuncrel> selectRCBylist(List<Sys_userrole> sysUserRole);

    int insertList(List<Sys_rolefuncrel> list);
}
