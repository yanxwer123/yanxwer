package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.dao.DailyPowerRecordDao;
import com.kld.gsm.ATG.domain.DailyPowerRecord;
import com.kld.gsm.ATG.domain.DailyPowerRecordKey;
import com.kld.gsm.coord.servcie.DailyPowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * Created by IntelliJ IDEA.
 *
 * @author <a href="yanxwer@163.com">yanxiaowei</a>
 * @version 1.0
 * @CreationTime: 2015/11/24 15:09
 * @Description:液位仪开关记录
 */

@SuppressWarnings("all")
@Service
public class DailyPowerServiceImpl implements DailyPowerService {
    @Autowired
    DailyPowerRecordDao dailyPowerRecordDao;

    @Override
    public DailyPowerRecord selectByPrimaryKey(DailyPowerRecordKey key) {

        return this.dailyPowerRecordDao.selectByPrimaryKey(key);
    }

    @Override
    public int insertSelective(DailyPowerRecord record) {

        return this.dailyPowerRecordDao.insertSelective(record);
    }
}
