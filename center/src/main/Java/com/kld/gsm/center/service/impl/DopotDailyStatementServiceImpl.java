package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.domain.oss_daily_opotDailyStatement;
import com.kld.gsm.center.service.DopotDailyStatementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kld.gsm.center.dao.oss_daily_opotDailyStatementMapper;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 付油量分类日结表
 */
@Service("DopotDailyStatementService")
public class DopotDailyStatementServiceImpl implements DopotDailyStatementService {

    @Resource
    private oss_daily_opotDailyStatementMapper ossDailyOpotDailyStatementMapper;
    @Transactional(rollbackFor = Exception.class)
    public int AddDopotDailyStatement(List<oss_daily_opotDailyStatement> oss_daily_opotDailyStatements) {
        for (oss_daily_opotDailyStatement item:oss_daily_opotDailyStatements)
        {
            ossDailyOpotDailyStatementMapper.insert(item);
        }
        return 1;
    }
}
