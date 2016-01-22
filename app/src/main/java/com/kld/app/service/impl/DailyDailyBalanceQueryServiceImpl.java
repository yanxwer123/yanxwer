package com.kld.app.service.impl;


import com.kld.app.service.DailyDailyBalanceService;
import com.kld.gsm.ATG.dao.DailyDailyBalanceDao;
import com.kld.gsm.ATG.domain.DailyDailyBalance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("all")
@Transactional
@Service("dailyDailyBalanceQueryService")
public class DailyDailyBalanceQueryServiceImpl implements DailyDailyBalanceService {

    @Resource
    private DailyDailyBalanceDao dailyDailyBalanceDao;

    public void setDailyDailyBalanceDao(DailyDailyBalanceDao dailyDailyBalanceDao) {
        this.dailyDailyBalanceDao = dailyDailyBalanceDao;
    }

    @Override
    public List<DailyDailyBalance> selectByDate(Date begin, Date end) {
        HashMap map = new HashMap();
        map.put("begin", begin);
        map.put("end", end);
//		map.put("oilcan", oilcan);
        return dailyDailyBalanceDao.selectByDate(map);
    }

    @Override//指定遇到异常RuntimeException时回滚
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor={Exception.class})
     public int save(DailyDailyBalance dailyDailyBalance) throws Exception {
        int i = dailyDailyBalanceDao.insert(dailyDailyBalance);
        throw new  Exception("RB");
    }
}
