package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.AlarmInventory;
import com.kld.gsm.ATG.domain.AlarmInventoryKey;

import java.util.HashMap;
import java.util.List;
@MySqlRepository
public interface AlarmInventoryDao {
    int deleteByPrimaryKey(AlarmInventoryKey key);

    int insert(com.kld.gsm.ATG.domain.AlarmInventory record);

    int insertSelective(com.kld.gsm.ATG.domain.AlarmInventory record);

    com.kld.gsm.ATG.domain.AlarmInventory selectByPrimaryKey(AlarmInventoryKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.AlarmInventory record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.AlarmInventory record);
    
    List<HashMap<String,Object>> selectByDate(HashMap map);

    List<com.kld.gsm.ATG.domain.AlarmInventory> selectByTrans(String stauts);

    AlarmInventory getByNo();

    int updateEndTime(AlarmInventory alarmInventory);

    public List<HashMap<String, Object>> selectByDateLastMouth();


    //查询所有 没有结束报警的记录
    List<AlarmInventory> findBeginAlarm();
}