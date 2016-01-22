package com.kld.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kld.app.service.SysCubageOptionService;
import com.kld.gsm.ATG.dao.SysManageCubageInfoDao;
import com.kld.gsm.ATG.domain.SysManageCubageInfo;
import com.kld.gsm.ATG.domain.SysManageCubageInfoKey;

@Service("sysCubageOptionService")
public class SysCubageOptionServiceImpl implements SysCubageOptionService {
	@Resource
	private SysManageCubageInfoDao sysManageCubageInfoDao;	
	
	@Override
	public List<SysManageCubageInfo> selectByKey(SysManageCubageInfoKey key) {
		return sysManageCubageInfoDao.selectByKey(key);
	}

}
