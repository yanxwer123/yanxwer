package com.kld.app.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kld.app.service.DailyOilDailyRecordService;
import com.kld.gsm.ATG.dao.DailyOilDailyRecordDao;
import com.kld.gsm.ATG.domain.DailyOilDailyRecord;

@Service("dailyOilDailyRecordService")
public class DailyOilDailyRecordServiceImpl implements DailyOilDailyRecordService {

	@Resource
	private DailyOilDailyRecordDao dailyOilDailyRecordDao;
	public void setDailyOilDailyRecordDao(
			DailyOilDailyRecordDao dailyOilDailyRecordDao) {
		this.dailyOilDailyRecordDao = dailyOilDailyRecordDao;
	}
	
	public List<DailyOilDailyRecord> selectByDate(Date begin, Date end) {
		HashMap map = new HashMap();
		map.put("begin", begin);
		map.put("end", end);
		return dailyOilDailyRecordDao.selectByDate(map);
	}

}
