package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.oss_monitor_oilgunMapper;
import com.kld.gsm.center.dao.oss_monitor_tankoilMapper;
import com.kld.gsm.center.domain.oss_monitor_oilgun;
import com.kld.gsm.center.service.DOilGunService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by fangzhun on 2015/11/25.
 */
@Service("DOilGunService")
public class DOilGunServiceImpl implements DOilGunService {
    @Resource
    private oss_monitor_oilgunMapper oilgunMapper;
    @Override
    public int AddOilgun(oss_monitor_oilgun oilgun) {
        try {
            oilgunMapper.insert(oilgun);
            return  1;
        }
        catch (Exception ex)
        {
            return  0;
        }
    }
}
