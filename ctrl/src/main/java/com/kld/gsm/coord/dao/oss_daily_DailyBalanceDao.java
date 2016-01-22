package com.kld.gsm.coord.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.common.base.BaseDao;
import com.kld.gsm.ATG.domain.DailyDailyBalance;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-11-13 11:45
 * @Description:
 */
@MySqlRepository
public interface oss_daily_DailyBalanceDao extends BaseDao<DailyDailyBalance,Long> {
    @Override
    void save(DailyDailyBalance dailyDailyBalance);
}
