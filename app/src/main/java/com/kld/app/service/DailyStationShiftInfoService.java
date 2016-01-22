package com.kld.app.service;

import java.util.Date;
import java.util.List;



/**
 * 油站班报
 */
public interface DailyStationShiftInfoService {
	int deleteByPrimaryKey(Integer shift);

    int insert(com.kld.gsm.ATG.domain.DailyStationShiftInfo record);

    int insertSelective(com.kld.gsm.ATG.domain.DailyStationShiftInfo record);

    com.kld.gsm.ATG.domain.DailyStationShiftInfo selectByPrimaryKey(Integer shift);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.DailyStationShiftInfo record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.DailyStationShiftInfo record);
    
    List<com.kld.gsm.ATG.domain.DailyStationShiftInfo> selectByDate(Date begin,Date end);

    List<com.kld.gsm.ATG.domain.DailyPumpDigitShift> queryGMxx(String shift);//获取泵码信息

//    List<com.kld.gsm.ATG.domain.DailyPumpDigitShift> queryFYxx(String shift);//获取付油信息
    
    List<com.kld.gsm.ATG.domain.DailyTankShift> queryYGxx(String shift);//获取油罐信息

	List queryFyxx(String shift, String OilNo);//获取付油信息

	List queryFyxxOilNo(String string);//查询付油信息中的油品种类

    List<com.kld.gsm.ATG.domain.DailyOpotCount> selectFyxx(String shift);//获取付油信息
}
