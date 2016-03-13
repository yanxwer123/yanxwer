package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.Sys_userrole;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2016/2/25 9:01
 * @Description:
 */
public interface Sys_userroleService {

    int deleteUserRoleByUserids(String userids);

    List<Sys_userrole>  selectByrolecode(String delrolecode);

    int insertList(List<Sys_userrole> list);

    List<Sys_userrole> selectByUserids(String userids);
}
