package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.oss_sysmanage_TankInfoMapper;
import com.kld.gsm.center.domain.oss_sysmanage_TankInfo;
import com.kld.gsm.center.service.TankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2016-01-04 17:56
 * @Description: 油罐
 */
@Service
public class TankInfoServiceImpl implements TankInfoService {
    @Autowired
    oss_sysmanage_TankInfoMapper oss_sysmanage_tankInfoMapper;
    @Override
    public List<oss_sysmanage_TankInfo> findAll() {
        return oss_sysmanage_tankInfoMapper.findAll();
    }

    @Override
    public String findOilName(String oilno) {
        return oss_sysmanage_tankInfoMapper.findOilName(oilno);
    }


}
