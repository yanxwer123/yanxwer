package com.kld.gsm.coord.dao;

import com.kld.gsm.ATG.common.base.BaseDao;
import com.kld.gsm.coord.domain.Roleinfor;
import com.kld.gsm.coord.mybatis.SybaseRepository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/29 15:01
 * @Description:
 */

@SybaseRepository
public interface RoleinforDao extends BaseDao<Roleinfor,Long> {
    //通过权限名称查询
    List<Roleinfor> getByRoleName(String roleName);
    List<String> getAll(int oprno);
}
