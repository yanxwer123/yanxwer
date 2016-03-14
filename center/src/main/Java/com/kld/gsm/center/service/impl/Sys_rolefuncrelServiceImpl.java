package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.Sys_rolefuncrelMapper;
import com.kld.gsm.center.domain.Sys_rolefuncrel;
import com.kld.gsm.center.domain.Sys_userrole;
import com.kld.gsm.center.service.Sys_rolefuncrelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<Sys_rolefuncrel> selectAll() {
        return sys_rolefuncrelDao.selectAll();
    }

    @Override
    public List<Sys_rolefuncrel> selectByrolecode(String rolecode) {
        return sys_rolefuncrelDao.selectByrolecode(rolecode);
    }

    @Override
    public List<Sys_rolefuncrel> selectRCBycode(String delfunccode) {
        return sys_rolefuncrelDao.selectRCBycode(delfunccode);
    }

    @Override
    public List<Sys_rolefuncrel> selectRCBylist(List<Sys_userrole> sysUserRole) {
        return sys_rolefuncrelDao.selectRCBylist(sysUserRole);
    }

    @Override
    public int insertList(List<Sys_rolefuncrel> list) {
        return sys_rolefuncrelDao.insertList(list);
    }

}
