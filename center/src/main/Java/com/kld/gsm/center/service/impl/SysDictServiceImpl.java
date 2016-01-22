package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.oss_sysmanage_oilTypeMapper;
import com.kld.gsm.center.domain.SysManageDict;
import com.kld.gsm.center.service.SysDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fangzhun on 2015/12/4.
 */
@Service("SysDictService")
public class SysDictServiceImpl implements SysDictService {

    @Resource
    private com.kld.gsm.center.dao. SysManageDictDao sysManageDictDao;
    @Override
    public int selectPSYJFZ() {
        return sysManageDictDao.selectByName("配送建议阀值");
    }

    @Override
    public List<HashMap<String, String>> selectYJLX() {
        return sysManageDictDao.selectYJLX();
    }

    @Override
    public SysManageDict selectByCode(String code) {
        return sysManageDictDao.selectByCode(code);
    }

    @Override
    public List<HashMap<String, String>> selectByParentId(String parentid){return sysManageDictDao.selectByParentId(parentid);}

    @Override
    public List<SysManageDict> selectbyVersion(Integer version) {
      return sysManageDictDao.selectbyVersion(version);
    }
}
