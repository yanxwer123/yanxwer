package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.oss_daily_tankshift;

import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 油罐交接表
 */
public interface DTankShiftService {
    int AddTankShift(List<oss_daily_tankshift> oss_daily_tankshifts);
    ResultMsg selectByShift(String shift,String oucode);
}
