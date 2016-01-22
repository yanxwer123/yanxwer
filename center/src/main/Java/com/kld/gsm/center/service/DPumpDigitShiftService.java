package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.oss_daily_pumpDigitShift;

import java.util.Date;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 泵码交接表
 */
public interface DPumpDigitShiftService {
    int AddPumpDigitShift(List<oss_daily_pumpDigitShift> oss_daily_pumpDigitShifts);
    ResultMsg selectByShift(String shift);
}
