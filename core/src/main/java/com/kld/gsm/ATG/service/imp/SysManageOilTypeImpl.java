package com.kld.gsm.ATG.service.imp;

import com.kld.gsm.ATG.dao.SysManageDictDao;
import com.kld.gsm.ATG.dao.SysManageOilTypeDao;
import com.kld.gsm.ATG.service.ISysManageOilType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2015/12/13 16:19
 * @Description:
 */
@Service
public class SysManageOilTypeImpl implements ISysManageOilType {
    @Autowired
    private SysManageOilTypeDao sysManageOilTypeDao;
    @Override
    public List<com.kld.gsm.ATG.domain.SysManageOilType> selectByInUse() {
        return sysManageOilTypeDao.selectByInUse();
    }
}
