package com.kld.app.service;

import java.util.List;

import com.kld.gsm.ATG.domain.SysManageAlarmParameter;

public interface AlmService {
	 List<SysManageAlarmParameter> selectAll();
	 SysManageAlarmParameter selectByPrimaryKey(Integer oilcan);
	 int updateByPrimaryKey(SysManageAlarmParameter record);
}
