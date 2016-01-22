package com.kld.app.service;

import java.util.List;

import com.kld.gsm.ATG.domain.SysManagePaTRelation;

public interface SysProbeTanksService {
	List<SysManagePaTRelation> selectAll();
	int insert(SysManagePaTRelation record);
}
