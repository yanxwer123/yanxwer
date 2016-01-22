package com.kld.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kld.app.service.AlmService;
import com.kld.gsm.ATG.dao.SysManageAlarmParameterDao;
import com.kld.gsm.ATG.domain.SysManageAlarmParameter;


@Service("almService")
public class AlmServiceImpl implements AlmService {

	@Resource
	private SysManageAlarmParameterDao sysManageAlarmParameterDao;
	
	@Override
	public List<SysManageAlarmParameter> selectAll() {
		// TODO Auto-generated method stub
		return sysManageAlarmParameterDao.selectAll();
	}

	@Override
	public SysManageAlarmParameter selectByPrimaryKey(Integer oilcan) {
		// TODO Auto-generated method stub
		return sysManageAlarmParameterDao.selectByPrimaryKey(oilcan);
	}

	@Override
	public int updateByPrimaryKey(SysManageAlarmParameter record) {
		// TODO Auto-generated method stub
		return sysManageAlarmParameterDao.updateByPrimaryKey(record);
	}

}
