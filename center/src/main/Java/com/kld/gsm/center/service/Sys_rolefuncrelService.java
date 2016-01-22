package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.Sys_rolefuncrel;

/**
 * Created by jw on 2015/7/30.
 */
public interface Sys_rolefuncrelService {


    int deleteRoleFuncByRolecode(String rolecode);

    int insert(Sys_rolefuncrel record);
}
