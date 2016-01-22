package com.kld.app.service.impl;

import com.kld.app.service.DailyPowerRecordService;
import com.kld.gsm.ATG.dao.DailyPowerRecordDao;
import com.kld.gsm.ATG.domain.DailyPowerRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fangzhun on 2015/11/27.
 */
@Service("dailyPowerRecordService")
public class DailyPowerRecordServiceImpl implements DailyPowerRecordService {

    @Resource
    @SuppressWarnings("all")
    private DailyPowerRecordDao dailyPowerRecordDao;
    @Override
    public List<DailyPowerRecord> selectByDate(String begin, String end) {
        HashMap pMap=new HashMap();
        pMap.put("begin",begin);
        pMap.put("end",end);
        return dailyPowerRecordDao.selectByDate(pMap);
    }
}
