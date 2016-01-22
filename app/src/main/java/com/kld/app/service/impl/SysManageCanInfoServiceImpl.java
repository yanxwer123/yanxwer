package com.kld.app.service.impl;

import com.kld.app.service.SysManageCanInfoService;
import com.kld.gsm.ATG.dao.SysManageCanInfoDao;
import com.kld.gsm.ATG.dao.SysManageOilGunInfoDao;
import com.kld.gsm.ATG.domain.SysManageCanInfo;
import com.kld.gsm.ATG.domain.SysManageOilGunInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fangzhun on 2015/11/26.
 */
@SuppressWarnings("all")
@Service("SysManageTankInfoService")
public class SysManageCanInfoServiceImpl implements SysManageCanInfoService {

    @Autowired
    private SysManageCanInfoDao sysManageCanInfoDao;
    @Autowired
    private SysManageOilGunInfoDao sysManageOilGunInfoDao;
    @Override
    public List<SysManageCanInfo> selectAll() {
        return sysManageCanInfoDao.selectAll();

    }

    /**
     * @description 根据油品编码获取，相应的罐 ny
     */
    @Override
    public List<SysManageCanInfo> selectByOilNo(String OilNo) {

        return sysManageCanInfoDao.selectByOilNo(OilNo);
      //  return null;
    }

    /**
     * @param CanNo
     * @decription 根据罐号 获取油枪列表 ny
     */
    @Override
    public List<SysManageOilGunInfo> selectGunsByCanNo(String CanNo) {
        return sysManageOilGunInfoDao.selectByOilCan(CanNo);
    }

    @Override
    public List<String> findByOilcan(String oilno) {
       return sysManageCanInfoDao.findByOilcan(oilno);
    }
    @Override
    public SysManageCanInfo selectbycanno(Integer canno) {
        return sysManageCanInfoDao.selectByPrimaryKey(canno);

    }
}
