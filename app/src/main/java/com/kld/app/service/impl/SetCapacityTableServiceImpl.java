package com.kld.app.service.impl;

import com.kld.app.service.ISetCapacityTableService;
import com.kld.gsm.ATG.dao.SysManageCubageDao;
import com.kld.gsm.ATG.domain.SysManageCubage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/30 19:01
 * @Description:
 */
@Service
@SuppressWarnings("all")
public class SetCapacityTableServiceImpl implements ISetCapacityTableService {
    @Autowired
    SysManageCubageDao sysManageCubageDao;
    @Override
    public int updateByPrimaryKeySelective(SysManageCubage record) {
        sysManageCubageDao.updateUnused(record);
        return sysManageCubageDao.updateByPrimaryKeySelective(record);
    }
}
