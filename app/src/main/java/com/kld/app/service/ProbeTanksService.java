package com.kld.app.service;

import java.util.List;

import com.kld.gsm.ATG.domain.SysManagePaTRelation;

public interface ProbeTanksService {
	List<SysManagePaTRelation> selectAll();
}
