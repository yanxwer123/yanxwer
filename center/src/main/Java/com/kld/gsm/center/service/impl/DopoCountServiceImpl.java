package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.domain.oss_daily_opoCount;
import com.kld.gsm.center.service.DopoCountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kld.gsm.center.dao.oss_daily_opoCountMapper;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 付油数量统计交接表
 */
@Service("DopoCountService")
public class DopoCountServiceImpl implements DopoCountService {
    @Resource
    private oss_daily_opoCountMapper ossDailyOpoCountMapper;

    @Transactional(rollbackFor = Exception.class)
    public int AddOPOCount(List<oss_daily_opoCount> oss_daily_opoCounts) {
        for (oss_daily_opoCount item:oss_daily_opoCounts)
        {
            ossDailyOpoCountMapper.insert(item);
        }
        return 1;
    }
}
