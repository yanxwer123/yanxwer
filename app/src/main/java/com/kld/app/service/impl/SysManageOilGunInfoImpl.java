package com.kld.app.service.impl;

import com.kld.app.service.SysManageOilGunInfoService;
import com.kld.gsm.ATG.dao.SysManageOilGunInfoDao;
import com.kld.gsm.ATG.domain.SysManageOilGunInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ken on 2016/2/3.
 */
@Service
@SuppressWarnings("all")
public class SysManageOilGunInfoImpl implements SysManageOilGunInfoService {
    @Autowired
    SysManageOilGunInfoDao sysManageOilGunInfoDao;

    @Override
    public List<SysManageOilGunInfo> findByOilCanNo(String canno) {
        return sysManageOilGunInfoDao.findByOilCanNo(canno);
    }
}
