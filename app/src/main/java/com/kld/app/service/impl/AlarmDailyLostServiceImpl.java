package com.kld.app.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.kld.gsm.ATG.dao.SysManageOilTypeDao;
import org.springframework.stereotype.Service;

import com.kld.app.service.AlarmDailyLostService;
import com.kld.gsm.ATG.dao.AlarmDailyLostDao;
import com.kld.gsm.ATG.domain.AlarmDailyLost;
import com.kld.gsm.ATG.domain.AlarmDailyLostKey;

@Service("alarmDailyLostService")
public class AlarmDailyLostServiceImpl implements AlarmDailyLostService {

	@Resource
	private AlarmDailyLostDao alarmDailyLostDao;
	public void setAlarmShiftLostDao(AlarmDailyLostDao alarmShiftLostDao) {
		this.alarmDailyLostDao = alarmShiftLostDao;
	}

	@Override
	public int deleteByPrimaryKey(AlarmDailyLostKey key) {
		return alarmDailyLostDao.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(AlarmDailyLost record) {
		return alarmDailyLostDao.insert(record);
	}

	@Override
	public int insertSelective(AlarmDailyLost record) {
		return alarmDailyLostDao.insert(record);
	}

	@Override
	public List<AlarmDailyLost> selectByDate(Date begin, Date end) {
		HashMap map = new HashMap();
		map.put("begin", begin);
		map.put("end", end);
		return alarmDailyLostDao.selectByDate(map);
	}

	@Override
	public List<AlarmDailyLost> selectByOilNo(String oilno,Date begin, Date end) {
		HashMap map = new HashMap();
		map.put("oilno",oilno);
		map.put("begin", begin);
		map.put("end", end);
		return alarmDailyLostDao.selectByOilNo(map);
		//return null;

	}

	@Override
	public AlarmDailyLost selectByPrimaryKey(AlarmDailyLostKey key) {
		return alarmDailyLostDao.selectByPrimaryKey(key);
	}

	@Override
	public int updateByPrimaryKey(AlarmDailyLost record) {
		return alarmDailyLostDao.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(AlarmDailyLost record) {
		return alarmDailyLostDao.updateByPrimaryKeySelective(record);
	}

	@Resource
	private SysManageOilTypeDao sysManageOilTypeDao;
	@Override
	public String selectOilNo(String oilNo){
		return sysManageOilTypeDao.selectOilNo(oilNo);
	}
}
