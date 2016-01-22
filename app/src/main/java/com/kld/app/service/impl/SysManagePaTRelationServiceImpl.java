package com.kld.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kld.app.service.SysManagePaTRelationService;
import com.kld.gsm.ATG.dao.SysManagePaTRelationDao;
import com.kld.gsm.ATG.domain.SysManagePaTRelation;

@Service("sysManagePaTRelationService")
public class SysManagePaTRelationServiceImpl implements
		SysManagePaTRelationService {

	@Autowired
	private SysManagePaTRelationDao sysManagePaTRelationDao;
	@Override
	public List<SysManagePaTRelation> selectByOilNo(String oilCan) {
		return sysManagePaTRelationDao.selectByOilNo(oilCan);
	}

}
