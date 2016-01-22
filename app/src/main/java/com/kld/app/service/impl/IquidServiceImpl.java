package com.kld.app.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kld.gsm.ATG.dao.SysManageIquidInstrumentDao;
import com.kld.gsm.ATG.domain.SysManageIquidInstrument;
import com.kld.app.service.IquidService;

/**
 * Created by 1 on 2015/10/26.
 */
@Service("iquidService")
public class IquidServiceImpl implements IquidService {
    @Resource
    private SysManageIquidInstrumentDao sysManageIquidInstrumentDao;

	@Override
	public int insert(SysManageIquidInstrument record) {
		// TODO Auto-generated method stub
		return sysManageIquidInstrumentDao.insert(record);
	}

	@Override
	public String selectSBXH() {
		SysManageIquidInstrument pIquid=sysManageIquidInstrumentDao.selectSBXH();
		return pIquid.getMactype();
	}

	@Override
	public SysManageIquidInstrument selectLast() {
	 return 	sysManageIquidInstrumentDao.selectLast();
	}

	@Override
	public int updateByPrimaryKey(SysManageIquidInstrument sysManageIquidInstrument) {
		return 	sysManageIquidInstrumentDao.updateByPrimaryKey(sysManageIquidInstrument);
	}

}
