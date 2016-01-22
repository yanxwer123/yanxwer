package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.oss_daily_opotDailyStatement;

import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 *   付油量分类日结表
 */
public interface DopotDailyStatementService {
    int AddDopotDailyStatement(List<oss_daily_opotDailyStatement> oss_daily_opotDailyStatements);
}
