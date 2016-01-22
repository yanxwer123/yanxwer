package com.kld.app.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kld.app.service.AlarmOilInContrastService;
import com.kld.gsm.ATG.dao.AlarmOilInContrastDao;
import com.kld.gsm.ATG.domain.AlarmOilInContrast;

@Service("alarmOilInContrastService")
public class AlarmOilInContrastServiceImpl implements AlarmOilInContrastService{

	@Resource
	private AlarmOilInContrastDao alarmOilInContrast;
	public void setAlarmOilInContrast(AlarmOilInContrastDao alarmOilInContrast) {
		this.alarmOilInContrast = alarmOilInContrast;
	}

	@Override
	public int deleteByPrimaryKey(String deliveryno) {
		return alarmOilInContrast.deleteByPrimaryKey(deliveryno);
	}

	@Override
	public int insert(AlarmOilInContrast record) {
		return alarmOilInContrast.insert(record);
	}

	@Override
	public int insertSelective(AlarmOilInContrast record) {
		return alarmOilInContrast.insertSelective(record);
	}

	@Override
	public List<AlarmOilInContrast> selectByDate(Date begin, Date end) {
		HashMap map = new HashMap();
		map.put("begin", begin);
		map.put("end", end);
		return alarmOilInContrast.selectByDate(map);
	}

	@Override
	public AlarmOilInContrast selectByPrimaryKey(String deliveryno) {
		return alarmOilInContrast.selectByPrimaryKey(deliveryno);
	}

	@Override
	public int updateByPrimaryKey(AlarmOilInContrast record) {
		return alarmOilInContrast.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(AlarmOilInContrast record) {
		return alarmOilInContrast.updateByPrimaryKeySelective(record);
	}

}
