package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.domain.oss_daily_OilDailyRecord;
import com.kld.gsm.center.service.DOilDailyRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kld.gsm.center.dao.oss_daily_OilDailyRecordMapper;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 成品油日结存报表
 */
@Service("DOilDailyRecordService")
public class DOilDailyRecordServiceImpl implements DOilDailyRecordService {
    @Resource
    private oss_daily_OilDailyRecordMapper ossDailyOilDailyRecordMapper;
    @Transactional(rollbackFor = Exception.class)
    public int AddOilDailyRecord(List<oss_daily_OilDailyRecord> oss_daily_oilDailyRecords) {
        for (oss_daily_OilDailyRecord item:oss_daily_oilDailyRecords)
        {
            ossDailyOilDailyRecordMapper.insert(item);
        }
        return 1;
    }
}
