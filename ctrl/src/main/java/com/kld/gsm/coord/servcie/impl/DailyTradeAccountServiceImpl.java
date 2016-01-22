package com.kld.gsm.coord.servcie.impl;


import com.kld.gsm.ATG.dao.DailyTradeAccountDao;
import com.kld.gsm.ATG.domain.DailyTradeAccount;
import com.kld.gsm.coord.servcie.IDailyTradeAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
@Service
public class DailyTradeAccountServiceImpl implements IDailyTradeAccountService {
    @Autowired
    public DailyTradeAccountDao dailyTradeAccountDao;

    @Override
    public int insert(DailyTradeAccount dailyTradeAccount) {
        // return 0;
        return dailyTradeAccountDao.insertSelective(dailyTradeAccount);
    }

    @Override
    public int update(DailyTradeAccount dailyTradeAccount) {
        return dailyTradeAccountDao.updateIsRecieved(dailyTradeAccount);
    }

    @Override
    public List<DailyTradeAccount> findNotRecieved() {
        return dailyTradeAccountDao.findNotRecieved();
    }


    public List<DailyTradeAccount> findAll() {

        return new ArrayList<DailyTradeAccount>();
        //return dailyTradeAccountDao.findAll();
    }
}
