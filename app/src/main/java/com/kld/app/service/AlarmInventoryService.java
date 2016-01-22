package com.kld.app.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.kld.gsm.ATG.domain.AlarmInventoryKey;

//库存预警
public interface AlarmInventoryService {
    int deleteByPrimaryKey(AlarmInventoryKey key);

    int insert(com.kld.gsm.ATG.domain.AlarmInventory record);

    int insertSelective(com.kld.gsm.ATG.domain.AlarmInventory record);

    com.kld.gsm.ATG.domain.AlarmInventory selectByPrimaryKey(AlarmInventoryKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.AlarmInventory record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.AlarmInventory record);
    
    List<HashMap<String,Object>> selectByDate(Date begin, Date end,String AlarmType);

    List<HashMap<String,Object>> selectByDateLastMouth();

}
