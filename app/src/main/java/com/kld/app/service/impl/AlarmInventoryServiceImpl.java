package com.kld.app.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kld.app.service.AlarmInventoryService;
import com.kld.gsm.ATG.dao.AlarmInventoryDao;
import com.kld.gsm.ATG.domain.AlarmInventory;
import com.kld.gsm.ATG.domain.AlarmInventoryKey;

@Service("alarmInventoryService")
public class AlarmInventoryServiceImpl implements AlarmInventoryService {

    @Resource
    private AlarmInventoryDao alarmInventoryDao;
	@Override
	public int deleteByPrimaryKey(AlarmInventoryKey key) {
		return alarmInventoryDao.deleteByPrimaryKey(key);
	}

	public void setAlarmInventoryDao(AlarmInventoryDao alarmInventoryDao) {
		this.alarmInventoryDao = alarmInventoryDao;
	}

	@Override
	public int insert(AlarmInventory record) {
		return alarmInventoryDao.insert(record);
	}

	@Override
	public int insertSelective(AlarmInventory record) {
		return alarmInventoryDao.insertSelective(record);
	}

	@Override
	public AlarmInventory selectByPrimaryKey(AlarmInventoryKey key) {
		return alarmInventoryDao.selectByPrimaryKey(key);
	}

	@Override
	public int updateByPrimaryKey(AlarmInventory record) {
		return alarmInventoryDao.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(AlarmInventory record) {
		return alarmInventoryDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<HashMap<String,Object>> selectByDate(Date begin, Date end, String AlarmType) {
		HashMap map = new HashMap();
		map.put("begin", begin);
		map.put("end", end);
		map.put("alarmtype", AlarmType);
		return alarmInventoryDao.selectByDate(map);
	}

	@Override
	public List<HashMap<String, Object>> selectByDateLastMouth() {
		return alarmInventoryDao.selectByDateLastMouth();
	}

}
