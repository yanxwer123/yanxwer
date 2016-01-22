package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.Sys_rolefuncrelMapper;
import com.kld.gsm.center.domain.Sys_rolefuncrel;
import com.kld.gsm.center.service.Sys_rolefuncrelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by jw on 2015/7/30.
 */
@Service
public class Sys_rolefuncrelServiceImpl implements Sys_rolefuncrelService {
    @Resource
    private Sys_rolefuncrelMapper sys_rolefuncrelDao;

    @Override
    public int deleteRoleFuncByRolecode(String rolecode){
        return sys_rolefuncrelDao.deleteRoleFuncByRolecode(rolecode);
    }
    public int insert(Sys_rolefuncrel record){
        return sys_rolefuncrelDao.insert(record);
    }

}
