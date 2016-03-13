package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.Sys_userrole;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2016/2/25 9:15
 * @Description:
 */
@MysqlRepository
public interface Sys_userRoleMapper {


    int deleteUserRoleByUserids(String userids);

    int insertList(List<Sys_userrole> list);

    List<Sys_userrole> selectByUserids(String userids);

    List<Sys_userrole>  selectByrolecode(String delrolecode);


}
