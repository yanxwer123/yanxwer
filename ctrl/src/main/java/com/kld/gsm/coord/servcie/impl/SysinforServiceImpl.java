package com.kld.gsm.coord.servcie.impl;


import com.kld.gsm.ATG.common.base.BaseDao;
import com.kld.gsm.ATG.common.base.BaseServiceImpl;
import com.kld.gsm.coord.dao.SysinforDao;
import com.kld.gsm.coord.domain.Sysinfor;
import com.kld.gsm.coord.servcie.SysinforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 *c
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/24 17:19
 * @Description:
 */
@SuppressWarnings("all")
@Service
public class SysinforServiceImpl extends BaseServiceImpl<Sysinfor, Long> implements SysinforService {
    @Autowired
     SysinforDao sysinforDao;

    @Override
    protected BaseDao<Sysinfor, Long> getDao() {
        return this.sysinforDao;
    }

    @Override
    public Sysinfor getAll() {
        return this.sysinforDao.getAll();
    }

}
