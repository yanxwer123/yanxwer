package com.kld.app.service;

import com.kld.gsm.ATG.domain.DailyDailyBalance;

import java.util.Date;
import java.util.List;
public interface DailyDailyBalanceService {
    List<com.kld.gsm.ATG.domain.DailyDailyBalance> selectByDate(Date begin, Date end);
    int save(DailyDailyBalance dailyDailyBalance) throws Exception;
}
