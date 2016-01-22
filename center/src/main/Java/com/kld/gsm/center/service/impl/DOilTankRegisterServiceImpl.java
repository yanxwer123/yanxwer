package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.domain.oss_daily_OilTankRegister;
import com.kld.gsm.center.service.DOilTankRegisterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kld.gsm.center.dao.oss_daily_OilTankRegisterMapper;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 加油站油品分罐保管登记帐
 */
@Service("DOilTankRegisterService")
public class DOilTankRegisterServiceImpl implements DOilTankRegisterService {
    @Resource
    private oss_daily_OilTankRegisterMapper ossDailyOilTankRegisterMapper;
    @Transactional(rollbackFor = Exception.class)
    public int AddOilTankRegister(List<oss_daily_OilTankRegister> oss_daily_oilTankRegisters) {
        for (oss_daily_OilTankRegister item:oss_daily_oilTankRegisters)
        {
            ossDailyOilTankRegisterMapper.insert(item);
        }
        return 1;
    }
}
