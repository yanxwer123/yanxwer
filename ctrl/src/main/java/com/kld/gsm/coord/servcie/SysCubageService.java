package com.kld.gsm.coord.servcie;

import com.kld.gsm.ATG.domain.SysManageCubage;
import com.kld.gsm.ATG.domain.SysManageCubageInfo;
import com.kld.gsm.ATG.domain.SysManageCubageInfoKey;

import java.util.List;
import java.util.Map;

public interface SysCubageService {
	List<SysManageCubage> selectAll();
	
	List<SysManageCubage> selectByKey(int key);

	List<SysManageCubage>selectByKeyAll(int key);

	List<SysManageCubageInfo> selectCubageInfo(SysManageCubageInfoKey sysManageCubageInfoKey);

	List<SysManageCubageInfo> selectCubageInfoByPar(Map map);
}
