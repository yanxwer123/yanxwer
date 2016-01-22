package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.oss_daily_OilDailyRecord;

import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 成品油日结存报表
 */
public interface DOilDailyRecordService {
    int AddOilDailyRecord(List<oss_daily_OilDailyRecord> oss_daily_oilDailyRecords);
}
