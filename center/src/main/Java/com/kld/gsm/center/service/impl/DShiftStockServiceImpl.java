package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.domain.oss_daily_ShiftStock;
import com.kld.gsm.center.service.DShiftStockService;
import org.springframework.stereotype.Service;
import com.kld.gsm.center.dao.oss_daily_ShiftStockMapper;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 班结库存表
 */
@Service("DShiftStockService")
public class DShiftStockServiceImpl implements DShiftStockService {
    @Resource
    private oss_daily_ShiftStockMapper ossDailyShiftStockMapper;
    public int  AddShiftStock(List<oss_daily_ShiftStock> oss_daily_shiftStocks) {
        for (oss_daily_ShiftStock item:oss_daily_shiftStocks)
        {
            ossDailyShiftStockMapper.insert(item);
        }
        return 1;
    }
}
