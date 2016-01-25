package com.kld.app.service;

import com.kld.gsm.ATG.domain.DailyTankShift;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-12-30 15:45
 * @Description:
 */
public interface CheckSerivce {
    //查看创建时间最新的期初库存 (DailyBalance)
    Double findRealStock(String oilno);
    //查询班次号,油品编号 条件：未日结 已班结(DailyTradeAccount)
    String findLastShift();
    //根据班次号查询油罐交接表  根据油品分组
    List<DailyTankShift> findByShift(String shift);

    /**
     *
     * @param shift 油品+班次号
     * @return 卸油量
     */
    List<Map<String,Object>>   findDischargeL(String shift);

    //已班结未日结的付油量
    List<Map<String,Object>> getLiter(String shift);
    List<Map<String,Object>> getLiterByAccountDate();


    /**
     *
     * @param OilNo 油品编号
     * @return 油品名称
     */
    String selectOilNo(String OilNo);
}
