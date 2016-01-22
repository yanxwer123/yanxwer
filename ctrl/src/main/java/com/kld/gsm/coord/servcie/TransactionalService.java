package com.kld.gsm.coord.servcie;

import com.kld.gsm.ATG.domain.DailyDailyBalance;
import com.kld.gsm.coord.domain.VouchStock;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-12-21 15:17
 * @Description: 测试Spring事物管理
 */
public interface TransactionalService {
    /**
     *  Sybase 数据库  DailyStationShiftInfo表
     * @param vouchStock
     * @Description  Insert
     * @return
     */
    int save(VouchStock vouchStock) throws Exception;

    /**
     * Mysql 数据库   DailyDailyBalance表
     * @param dailyDailyBalance
     * @Description  Insert
     * @return
     */
    int save(DailyDailyBalance dailyDailyBalance)  throws Exception   ;


}
