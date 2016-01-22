package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.domain.oss_daily_oildaybill;
import com.kld.gsm.center.service.OildaybillService;
import org.springframework.stereotype.Service;
import com.kld.gsm.center.dao.oss_daily_oildaybillMapper;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 泵码日结表
 */
@Service("OildaybillService")
public class OildaybillServiceImpl implements OildaybillService {
    @Resource
    private oss_daily_oildaybillMapper ossDailyOildaybillMapper;
    public int AddOildaybill(List<oss_daily_oildaybill> oss_daily_oildaybills) {
        for (oss_daily_oildaybill item:oss_daily_oildaybills)
        {
            ossDailyOildaybillMapper.insert(item);
        }
        return 1;
    }
}
