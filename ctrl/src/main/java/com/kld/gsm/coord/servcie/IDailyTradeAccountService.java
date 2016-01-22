package com.kld.gsm.coord.servcie;


import com.kld.gsm.ATG.domain.DailyTradeAccount;

import java.util.List;

/**
 * Created by yinzhiguang on 2015/11/4.
 */
public interface IDailyTradeAccountService {
    int insert(DailyTradeAccount dailyTradeAccount);
    int update(DailyTradeAccount dailyTradeAccount);
    List<DailyTradeAccount> findNotRecieved();
}
