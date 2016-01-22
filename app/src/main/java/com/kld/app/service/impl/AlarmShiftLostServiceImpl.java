package com.kld.app.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.kld.gsm.ATG.dao.SysManageOilTypeDao;
import com.kld.gsm.ATG.domain.SysManageOilType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kld.app.service.AlarmShiftLostService;
import com.kld.gsm.ATG.dao.AlarmShiftLostDao;
import com.kld.gsm.ATG.domain.AlarmShiftLost;
import com.kld.gsm.ATG.domain.AlarmShiftLostKey;

@Service("alarmShiftLostService")
public class AlarmShiftLostServiceImpl implements AlarmShiftLostService {
	@Autowired
	SysManageOilTypeDao sysManageOilTypeDao;
	@Resource
	private AlarmShiftLostDao alarmShiftLostDao;


	public void setAlarmShiftLostDao(AlarmShiftLostDao alarmShiftLostDao) {
		this.alarmShiftLostDao = alarmShiftLostDao;
	}

	@Override
	public int deleteByPrimaryKey(AlarmShiftLostKey key) {
		return alarmShiftLostDao.deleteByPrimaryKey(key);
	}

	@Override
	public SysManageOilType selectByPrimaryKey(String var1) {
		return sysManageOilTypeDao.selectByPrimaryKey(var1);
	}

	@Override
	public int insert(AlarmShiftLost record) {
		return alarmShiftLostDao.insert(record);
	}

	@Override
	public int insertSelective(AlarmShiftLost record) {
		return alarmShiftLostDao.insert(record);
	}

	@Override
	public List<AlarmShiftLost> selectByDate(Date begin, Date end, String oilcanno) {
		HashMap map = new HashMap();
		map.put("begin", begin);
		map.put("end", end);
		map.put("oilcanno", oilcanno);
		return alarmShiftLostDao.selectByDate(map);
	}

	@Override
	public AlarmShiftLost selectByPrimaryKey(AlarmShiftLostKey key) {
		return alarmShiftLostDao.selectByPrimaryKey(key);
	}

	@Override
	public int updateByPrimaryKey(AlarmShiftLost record) {
		return alarmShiftLostDao.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(AlarmShiftLost record) {
		return alarmShiftLostDao.updateByPrimaryKeySelective(record);
	}

}
