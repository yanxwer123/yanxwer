package com.kld.app.service;

import java.util.Date;
import java.util.List;

import com.kld.gsm.ATG.domain.AlarmShiftLostKey;
import com.kld.gsm.ATG.domain.SysManageOilType;

public interface AlarmShiftLostService {

    int deleteByPrimaryKey(AlarmShiftLostKey key);
    SysManageOilType selectByPrimaryKey(String var1);

    int insert(com.kld.gsm.ATG.domain.AlarmShiftLost record);

    int insertSelective(com.kld.gsm.ATG.domain.AlarmShiftLost record);

    com.kld.gsm.ATG.domain.AlarmShiftLost selectByPrimaryKey(AlarmShiftLostKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.AlarmShiftLost record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.AlarmShiftLost record);
    
    List<com.kld.gsm.ATG.domain.AlarmShiftLost> selectByDate(Date begin, Date end,String oilcanno);

}
