package com.kld.gsm.ATG.service;

import com.kld.gsm.ATG.domain.MonitorTimeInventory;
import com.kld.gsm.ATGDevice.atg_stock_data_out_t;
import com.kld.gsm.MacLog.MacLogInfo;

import java.util.List;

/**
 * Created by fangzhun on 2015/11/21.
 */
public interface DailyRunning {
    //付油量分类统计表（按付油类型）
    int synOpotCountLost(String host);
    //付油数量统计交接表
    int opoCountLost(String host);
    //付油量分类日结表
    int opotDailyStatementLost(String host);
    //加油站油品分罐保管登记帐
    int OilTankRegisterLost(String host);
    //日平衡表
    int DailyBalanceLost(String host);
    //油站班报表
    int StationShiftInfoLost(String host);
    //油罐交接表
    int tankshiftLost(String host);
    //泵码交接表
    int pumpDigitShiftLost(String host);
    //泵码日结表
    int oildaybillLost(String host);
    //静态罐存
    int tankoilLost(String host);

    /*
   Created by fangzhun on 2015/11/20.
   油站班报表名oss_daily_stationshiftinfo
   付油量分类统计表名oss_daily_opotcount
   泵码交接表名oss_daily_pumpdigitshift
   油罐交接表oss_daily_tankshift
   付油数量统计交接表oss_daily_opocount
   */
    void AddClassKnotData(String host);

    //添加枪实时状态
    int addMacLogInfo(String host,List<MacLogInfo> MacLogInfoLst);
    //灌实时状态
    int addMonitorTimeInventoryInfo(String host,List<atg_stock_data_out_t> stockdataLst);
}
