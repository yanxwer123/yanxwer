package com.kld.app.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kld.app.service.AlarmSaleOutService;
import com.kld.gsm.ATG.dao.AlarmSaleOutDao;
import com.kld.gsm.ATG.domain.AlarmSaleOut;
import com.kld.gsm.ATG.domain.AlarmSaleOutKey;

@Service("alarmSaleOutService")
public class AlarmSaleOutServiceImpl implements AlarmSaleOutService {

	@Resource
	private AlarmSaleOutDao alarmSaleOutDao;
	public void setAlarmSaleOutDao(AlarmSaleOutDao alarmSaleOutDao) {
		this.alarmSaleOutDao = alarmSaleOutDao;
	}

	@Override
	public int deleteByPrimaryKey(AlarmSaleOutKey key) {
		return alarmSaleOutDao.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(AlarmSaleOut record) {
		return alarmSaleOutDao.insert(record);
	}

	@Override
	public int insertSelective(AlarmSaleOut record) {
		return alarmSaleOutDao.insert(record);
	}

	@Override
	public List<AlarmSaleOut> selectByDate(Date begin,Date end) {
		HashMap map = new HashMap();
		map.put("begin", begin);
		map.put("end", end);
		return alarmSaleOutDao.selectByDate(map);
	}

	@Override
	public AlarmSaleOut selectByPrimaryKey(AlarmSaleOutKey key) {
		return alarmSaleOutDao.selectByPrimaryKey(key);
	}

	@Override
	public int updateByPrimaryKey(AlarmSaleOut record) {
		return alarmSaleOutDao.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(AlarmSaleOut record) {
		return alarmSaleOutDao.updateByPrimaryKeySelective(record);
	}

}
