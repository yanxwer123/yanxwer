package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.common.base.BaseDao;
import com.kld.gsm.ATG.common.base.BaseServiceImpl;
import com.kld.gsm.coord.dao.OprAuthorityDao;
import com.kld.gsm.coord.domain.OprAuthority;
import com.kld.gsm.coord.servcie.OprAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/27 17:19
 * @Description:
 */
@SuppressWarnings("all")
@Service
public class OprAuthorityServiceImpl extends BaseServiceImpl<OprAuthority, Long> implements OprAuthorityService {
    @Autowired
    private OprAuthorityDao oprAuthorityDao;

    @Override
    protected BaseDao<OprAuthority, Long> getDao() {
        return this.oprAuthorityDao;
    }

    @Override
    public List<OprAuthority> getByOprNo(int oprno) {
        List<OprAuthority>list=oprAuthorityDao.getByOprNo(oprno);
        return list;
    }
}
