package com.kld.gsm.ATG.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.AlarmEquipment;
import com.kld.gsm.ATG.domain.AlarmEquipmentKey;
@MySqlRepository
public interface AlarmEquipmentDao {
    int deleteByPrimaryKey(AlarmEquipmentKey key);

    int insert(com.kld.gsm.ATG.domain.AlarmEquipment record);

    int insertSelective(com.kld.gsm.ATG.domain.AlarmEquipment record);

    com.kld.gsm.ATG.domain.AlarmEquipment selectByPrimaryKey(AlarmEquipmentKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.AlarmEquipment record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.AlarmEquipment record);
    
    List<com.kld.gsm.ATG.domain.AlarmEquipment> selectByDate(HashMap map);
    List<com.kld.gsm.ATG.domain.AlarmEquipment> selectByTrans(String stauts);
    List<com.kld.gsm.ATG.domain.AlarmEquipment> selectEndTime(int oilcanno);
    int updateEndTime(AlarmEquipment alarmEquipment);
    String selectMaxTime(int oilcanno);
    String isExisit(com.kld.gsm.ATG.domain.AlarmEquipment record);
}