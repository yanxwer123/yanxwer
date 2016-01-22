package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.oss_daily_ShiftStock;

import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 班结库存表
 */
public interface DShiftStockService {
    int AddShiftStock(List<oss_daily_ShiftStock> oss_daily_shiftStocks);
}
