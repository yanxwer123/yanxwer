package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.Sys_userRoleMapper;
import com.kld.gsm.center.domain.Sys_userrole;
import com.kld.gsm.center.service.Sys_userroleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2016/2/25 9:12
 * @Description:
 */
@Service
public class Sys_userroleServiceImpl implements Sys_userroleService {
    @Resource
    private Sys_userRoleMapper Sys_userRoleDao;

    @Override
    public int deleteUserRoleByUserids(String userids) {
        return Sys_userRoleDao.deleteUserRoleByUserids(userids);
    }

    @Override
    public List<Sys_userrole> selectByrolecode(String delrolecode) {
        return Sys_userRoleDao.selectByrolecode(delrolecode);
    }

    @Override
    public int insertList(List<Sys_userrole> list) {return Sys_userRoleDao.insertList(list);}

    @Override
    public List<Sys_userrole> selectByUserids(String userids) {return Sys_userRoleDao.selectByUserids(userids);}
}
