package com.kld.app.service;


import com.kld.gsm.ATG.domain.SysManageIquidInstrument;

/**
 * Created by 1 on 2015/10/26.
 */
public interface IquidService {

	int insert(com.kld.gsm.ATG.domain.SysManageIquidInstrument record);
	//查询设备型号
	String selectSBXH();
	SysManageIquidInstrument selectLast();

	int updateByPrimaryKey(SysManageIquidInstrument sysManageIquidInstrument);
}
