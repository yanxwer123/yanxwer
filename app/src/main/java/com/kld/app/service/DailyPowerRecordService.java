package com.kld.app.service;

import com.kld.gsm.ATG.domain.DailyOilDailyRecord;
import com.kld.gsm.ATG.domain.DailyPowerRecord;

import java.util.Date;
import java.util.List;

/**
 * Created by fangzhun on 2015/11/27.
 */
public interface DailyPowerRecordService {
    List<DailyPowerRecord> selectByDate(String begin, String end);
}
