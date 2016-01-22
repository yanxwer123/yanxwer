package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.oss_daily_OilTankRegister;

import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 加油站油品分罐保管登记帐
 */
public interface DOilTankRegisterService {
    int AddOilTankRegister(List<oss_daily_OilTankRegister> oss_daily_oilTankRegisters);
}
