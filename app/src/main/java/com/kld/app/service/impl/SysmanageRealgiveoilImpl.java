package com.kld.app.service.impl;

import com.kld.app.service.SysmanageRealgiveoilService;
import com.kld.gsm.ATG.dao.SysmanageRealgiveoilDao;
import com.kld.gsm.ATG.domain.SysmanageRealgiveoil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2016/2/28  15:07
 * @Description:
 */
@Service
@SuppressWarnings("all")
public class SysmanageRealgiveoilImpl implements SysmanageRealgiveoilService {
    @Autowired
    private SysmanageRealgiveoilDao sysmanageRealgiveoilDao;
    @Override
    public SysmanageRealgiveoil findByCxdId(String cxdid) {
        return sysmanageRealgiveoilDao.selectByPrimaryKey(cxdid);
    }
}
