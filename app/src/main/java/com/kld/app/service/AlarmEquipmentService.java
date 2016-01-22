package com.kld.app.service;

import java.util.Date;
import java.util.List;

import com.kld.gsm.ATG.domain.AlarmEquipmentKey;

//设备故障
public interface AlarmEquipmentService {
    int deleteByPrimaryKey(AlarmEquipmentKey key);

    int insert(com.kld.gsm.ATG.domain.AlarmEquipment record);

    int insertSelective(com.kld.gsm.ATG.domain.AlarmEquipment record);

    com.kld.gsm.ATG.domain.AlarmEquipment selectByPrimaryKey(AlarmEquipmentKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.AlarmEquipment record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.AlarmEquipment record);

    List<com.kld.gsm.ATG.domain.AlarmEquipment> selectByDate(Date begin, Date end,String failureType);
}
