package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.oss_alarm_DailyLost;
import com.kld.gsm.center.domain.oss_daily_StationShiftInfo;
import com.kld.gsm.center.domain.oss_daily_pumpDigitShift;
import com.kld.gsm.center.service.DPumpDigitShiftService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kld.gsm.center.dao.oss_daily_pumpDigitShiftMapper;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 泵码交接表
 */
@Service("DPumpDigitShiftService")
public class DPumpDigitShiftServiceImpl implements DPumpDigitShiftService {
    @Resource
    private oss_daily_pumpDigitShiftMapper ossDailyPumpDigitShiftMapper;
    @Transactional(rollbackFor = Exception.class)
    public int AddPumpDigitShift(List<oss_daily_pumpDigitShift> oss_daily_pumpDigitShifts) {
        for (oss_daily_pumpDigitShift item :oss_daily_pumpDigitShifts)
        {
            ossDailyPumpDigitShiftMapper.insert(item);
        }
        return 1;
    }

    @Override
    public ResultMsg selectByShift(String shift) {
        ResultMsg result=new ResultMsg();
        List<oss_daily_pumpDigitShift> exchangeList=ossDailyPumpDigitShiftMapper.selectByShift(shift);
        result.setResult(true);
        result.setData(exchangeList);
        result.setRows(exchangeList);
        result.setTotal(exchangeList.size());
        return result;
    }


}
