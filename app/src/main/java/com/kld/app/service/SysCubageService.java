package com.kld.app.service;

import java.util.List;
import java.util.Map;

import com.kld.gsm.ATG.domain.SysManageCubage;
import com.kld.gsm.ATG.domain.SysManageCubageInfo;
import com.kld.gsm.ATG.domain.SysManageCubageInfoKey;

public interface SysCubageService {
	List<SysManageCubage> selectAll();
	
	List<SysManageCubage> selectByKey(int key);

	List<SysManageCubage>selectByKeyAll(int key);

	List<SysManageCubageInfo> selectCubageInfo(SysManageCubageInfoKey sysManageCubageInfoKey);

	List<SysManageCubageInfo> selectCubageInfoByPar(Map map);
}
