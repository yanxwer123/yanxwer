package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.dao.SysManageCubageDao;
import com.kld.gsm.ATG.dao.SysManageCubageInfoDao;
import com.kld.gsm.ATG.domain.SysManageCubage;
import com.kld.gsm.ATG.domain.SysManageCubageInfo;
import com.kld.gsm.ATG.domain.SysManageCubageInfoKey;
import com.kld.gsm.coord.servcie.SysCubageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("sysCubageService")
@SuppressWarnings("all")
public class SysCubageServiceImpl implements SysCubageService {

	@Resource
	private SysManageCubageDao sysManageCubageDao;
	@Resource
	private SysManageCubageInfoDao sysManageCubageInfoDao;
	
	@Override
	public List<SysManageCubage> selectAll() {
		return sysManageCubageDao.selectAll();
	}

	@Override
	public List<SysManageCubage> selectByKey(int key) {
		// TODO Auto-generated method stub
		return sysManageCubageDao.selectByKey(key);
	}

	@Override
	public List<SysManageCubage> selectByKeyAll(int key) {
		return sysManageCubageDao.selectByKeyAll(key);
	}

	@Override
	public List<SysManageCubageInfo> selectCubageInfo(SysManageCubageInfoKey sysManageCubageInfoKey) {
		return sysManageCubageInfoDao.selectCubageInfo(sysManageCubageInfoKey);
	}
	@Override
	public List<SysManageCubageInfo> selectCubageInfoByPar(Map map) {
		return sysManageCubageInfoDao.selectCubageInfoByPar(map);
	}

}
