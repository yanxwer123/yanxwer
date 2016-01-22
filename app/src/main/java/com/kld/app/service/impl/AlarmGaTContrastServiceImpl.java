package com.kld.app.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kld.app.service.AlarmGaTContrastService;
import com.kld.gsm.ATG.dao.AlarmGaTContrastDao;
import com.kld.gsm.ATG.domain.AlarmGaTContrast;
import com.kld.gsm.ATG.domain.AlarmGaTContrastKey;

@Service("alarmGaTContrastService")
public class AlarmGaTContrastServiceImpl implements AlarmGaTContrastService {

	@Resource
	private AlarmGaTContrastDao alarmGaTContrastDao;
	public void setAlarmGaTContrastDao(AlarmGaTContrastDao alarmGaTContrastDao) {
		this.alarmGaTContrastDao = alarmGaTContrastDao;
	}
	@Override
	public int deleteByPrimaryKey(AlarmGaTContrastKey key) {
		return alarmGaTContrastDao.deleteByPrimaryKey(key);
	}
	@Override
	public int insert(AlarmGaTContrast record) {
		return alarmGaTContrastDao.insert(record);
	}
	@Override
	public int insertSelective(AlarmGaTContrast record) {
		return alarmGaTContrastDao.insertSelective(record);
	}
	@Override
	public List<AlarmGaTContrast> selectByDate(Date begin, Date end,
			String oilcan) {
		HashMap map = new HashMap();
		map.put("begin", begin);
		map.put("end", end);
		map.put("oilcan", oilcan);
		return alarmGaTContrastDao.selectByDate(map);
	}
	@Override
	public AlarmGaTContrast selectByPrimaryKey(AlarmGaTContrastKey key) {
		return alarmGaTContrastDao.selectByPrimaryKey(key);
	}
	@Override
	public int updateByPrimaryKey(AlarmGaTContrast record) {
		return alarmGaTContrastDao.updateByPrimaryKey(record);
	}




	@Override
	public int updateByPrimaryKeySelective(AlarmGaTContrast record) {
		return alarmGaTContrastDao.updateByPrimaryKeySelective(record);
	}

}
