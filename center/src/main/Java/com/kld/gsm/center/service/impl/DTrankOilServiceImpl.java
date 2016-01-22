package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.oss_daily_TradeInventoryMapper;
import com.kld.gsm.center.dao.oss_monitor_tankoilMapper;
import com.kld.gsm.center.domain.oss_daily_TradeInventory;
import com.kld.gsm.center.domain.oss_monitor_tankoil;
import com.kld.gsm.center.service.DTrankOilService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 5 on 2015/11/19.
 *  油罐静态库存
 */
@Service("DTrankOilService")
public class DTrankOilServiceImpl implements DTrankOilService {
    @Resource
    private oss_monitor_tankoilMapper oss_Monitor_TankOilMapper;
    @Override
    public int AddTankoil(oss_monitor_tankoil oss_daily_shiftStocks) {
        try {
            oss_Monitor_TankOilMapper.insert(oss_daily_shiftStocks);
            return 1;
        }
        catch(Exception ex)
        {
            return 0;
        }
    }
}
