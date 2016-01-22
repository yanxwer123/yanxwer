package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.oss_daily_opoCount;

import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 付油数量统计交接表
 */
public interface DopoCountService {
    int AddOPOCount(List<oss_daily_opoCount> oss_daily_opoCounts);
}
