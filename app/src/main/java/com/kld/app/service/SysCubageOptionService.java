package com.kld.app.service;

import java.util.List;

import com.kld.gsm.ATG.domain.SysManageCubageInfo;
import com.kld.gsm.ATG.domain.SysManageCubageInfoKey;

public interface SysCubageOptionService {
	List<SysManageCubageInfo> selectByKey(SysManageCubageInfoKey key);
}
