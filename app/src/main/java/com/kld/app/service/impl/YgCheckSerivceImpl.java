package com.kld.app.service.impl;

import com.kld.app.service.YgCheckSerivce;
import com.kld.gsm.ATG.dao.SysManageCanInfoDao;
import com.kld.gsm.ATG.domain.SysManageCanInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ken on 2016/2/2.
 */
@Service
public class YgCheckSerivceImpl implements YgCheckSerivce {
    @Autowired
    SysManageCanInfoDao sysManageCanInfoDao;

    @Override
    public List<SysManageCanInfo> findOilCan() {
        return sysManageCanInfoDao.selectAll();
    }
}
