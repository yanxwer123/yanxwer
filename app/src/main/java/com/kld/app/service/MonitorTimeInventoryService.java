package com.kld.app.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kld.gsm.ATG.domain.MonitorTimeInventory;
import com.kld.gsm.ATG.domain.SysManageCanInfo;
import com.kld.gsm.ATG.domain.SysManageOilGunInfo;
import org.joda.time.DateTime;

/**
 * Created by 1 on 2015/10/26.
 */
public interface MonitorTimeInventoryService {
	int deleteByPrimaryKey(Integer id);

    int insert(MonitorTimeInventory record);

    int insertSelective(MonitorTimeInventory record);

    MonitorTimeInventory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MonitorTimeInventory record);

    int updateByPrimaryKey(MonitorTimeInventory record);

    String getTimeInventoryList(Map map);

	List<MonitorTimeInventory> querySdkc(HashMap map);
	
	List<SysManageCanInfo> selectAll();
	
	List<SysManageOilGunInfo> selectAllOilGun();
	
    MonitorTimeInventory selectBeginDataByCanNo(Integer oilCan);


}
