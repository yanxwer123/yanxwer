package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.common.base.BaseDao;
import com.kld.gsm.ATG.common.base.BaseServiceImpl;
import com.kld.gsm.coord.dao.RoleinforDao;
import com.kld.gsm.coord.domain.Roleinfor;
import com.kld.gsm.coord.servcie.RoleinforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/29 15:07
 * @Description:
 */
@SuppressWarnings("all")
@Service
public class RoleinforServiceImpl extends BaseServiceImpl<Roleinfor, Long> implements RoleinforService {
    @Autowired
    private RoleinforDao roleinforDao;
    private String roleName;

    @Override
    protected BaseDao<Roleinfor, Long> getDao() {
        return this.roleinforDao;
    }

    @Override
    public List<Roleinfor> getByRoleName(String roleName) {
        List<Roleinfor> list = roleinforDao.getByRoleName(roleName);
        return list;
    }
}
