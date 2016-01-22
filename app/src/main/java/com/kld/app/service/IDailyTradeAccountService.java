package com.kld.app.service;

import java.util.Date;
import java.util.Map;

public interface IDailyTradeAccountService {
    public Map selectDuringSales(Date begin, Date end);
}
