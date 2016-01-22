package com.kld.app.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kld.app.service.AlarmMeasureLeakService;
import com.kld.gsm.ATG.dao.AlarmMeasureLeakDao;
import com.kld.gsm.ATG.domain.AlarmMeasureLeak;
import com.kld.gsm.ATG.domain.AlarmMeasureLeakKey;

@Service("alarmMeasureLeakService")
public class AlarmMeasureLeakServiceImpl implements AlarmMeasureLeakService{

	@Resource
	private AlarmMeasureLeakDao alarmMeasureLeakDao;

	public void setAlarmMeasureLeakDao(AlarmMeasureLeakDao alarmMeasureLeakDao) {
		this.alarmMeasureLeakDao = alarmMeasureLeakDao;
	}

	@Override
	public int deleteByPrimaryKey(AlarmMeasureLeakKey key) {
		return alarmMeasureLeakDao.deleteByPrimaryKey(key);
	}


	@Override
	public List<AlarmMeasureLeak> selectByOilcan(Integer oilcanno) {
		return alarmMeasureLeakDao.selectByOilcan(oilcanno);
	}

	@Override
	public List<AlarmMeasureLeak> selecthasStartByOilcan(Integer oilcanno) {
		return alarmMeasureLeakDao.selecthasStartByOilcan(oilcanno);
	}

	@Override
	public int updateEndDate(HashMap map1) {
		return alarmMeasureLeakDao.updateEndDate(map1);
	}

	@Override
	public int insert(AlarmMeasureLeak record) {
		return alarmMeasureLeakDao.insert(record);
	}

	@Override
	public int insertSelective(AlarmMeasureLeak record) {
		return alarmMeasureLeakDao.insertSelective(record);
	}

	@Override
	public List<AlarmMeasureLeak> selectByDate(Date begin, Date end,
			String oilcan) {
		HashMap map = new HashMap();
		map.put("begin", begin);
		map.put("end", end);
		map.put("oilcan", oilcan);
		return alarmMeasureLeakDao.selectByDate(map);
	}

	@Override
	public AlarmMeasureLeak selectByPrimaryKey(AlarmMeasureLeakKey key) {
		return alarmMeasureLeakDao.selectByPrimaryKey(key);
	}

	@Override
	public int updateByPrimaryKey(AlarmMeasureLeak record) {
		return alarmMeasureLeakDao.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(AlarmMeasureLeak record) {
		return alarmMeasureLeakDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public AlarmMeasureLeak selectinfoByCanNo(Integer canno) {
		return alarmMeasureLeakDao.selectinfoBycanno(canno);
	}
}
