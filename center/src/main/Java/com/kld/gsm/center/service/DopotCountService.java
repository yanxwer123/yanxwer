package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.oss_daily_opotCount;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 付油量分类统计表（按付油类型）
 */
public interface DopotCountService {
    int AddDopotCount(List<oss_daily_opotCount> oss_daily_opotCounts);
    ResultMsg selectByShift(String shift,String oucode);
}
