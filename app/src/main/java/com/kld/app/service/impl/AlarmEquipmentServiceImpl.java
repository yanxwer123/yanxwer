package com.kld.app.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kld.app.service.AlarmEquipmentService;
import com.kld.gsm.ATG.dao.AlarmEquipmentDao;
import com.kld.gsm.ATG.domain.AlarmEquipment;
import com.kld.gsm.ATG.domain.AlarmEquipmentKey;

@Service("alarmEquipmentService")
public class AlarmEquipmentServiceImpl implements AlarmEquipmentService{

    @Resource
    private AlarmEquipmentDao alarmEquipmentDao;
    
	public void setAlarmEquipmentDao(AlarmEquipmentDao alarmEquipmentDao) {
		this.alarmEquipmentDao = alarmEquipmentDao;
	}

	@Override
	public int deleteByPrimaryKey(AlarmEquipmentKey key) {
		return alarmEquipmentDao.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(AlarmEquipment record) {
		return alarmEquipmentDao.insert(record);
	}

	@Override
	public int insertSelective(AlarmEquipment record) {
		return alarmEquipmentDao.insertSelective(record);
	}

	@Override
	public List<AlarmEquipment> selectByDate(Date begin,Date end,String failureType) {
		HashMap map = new HashMap();
		map.put("begin", begin);
		map.put("end", end);
		map.put("failureType", failureType);
		return alarmEquipmentDao.selectByDate(map);
	}

	@Override
	public AlarmEquipment selectByPrimaryKey(AlarmEquipmentKey key) {
		return alarmEquipmentDao.selectByPrimaryKey(key);
	}

	@Override
	public int updateByPrimaryKey(AlarmEquipment record) {
		return alarmEquipmentDao.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(AlarmEquipment record) {
		return alarmEquipmentDao.updateByPrimaryKeySelective(record);
	}

}
