package com.kld.app.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kld.app.service.SysProbeService;
import com.kld.gsm.ATG.dao.SysManageProbeParDao;
import com.kld.gsm.ATG.domain.SysManageProbePar;


@Service("probeService")
public class SysProbeServiceImpl implements SysProbeService {

	@Autowired
	private SysManageProbeParDao sysmanageProbeParDao;
	@Override
	public int insert(SysManageProbePar record) {
		// TODO Auto-generated method stub
		return sysmanageProbeParDao.insert(record);
	}
	@Override
	public List<SysManageProbePar> selectAll() {
		// TODO Auto-generated method stub
		return sysmanageProbeParDao.selectAll();
	}
	@Override
	public int deleteByPrimaryKey(String probemodel) {
		// TODO Auto-generated method stub
		return sysmanageProbeParDao.deleteByPrimaryKey(probemodel);
	}

	@Override
	public int update(SysManageProbePar record) {
		return  sysmanageProbeParDao.updateByPrimaryKey(record);
	}

	@Override
	public SysManageProbePar selectModelByProbemodel(String Probemodel) {
		return  sysmanageProbeParDao.selectByPrimaryKey(Probemodel);
	}

	@Override
	public int ExisOilCan(Integer OilCan) {
		HashMap hashMap=new HashMap();
		hashMap.put("OilCan",OilCan);
		return sysmanageProbeParDao.ExisOilCan(hashMap);
	}

	@Override
	public int ExisProbePort(Integer probeport) {
		HashMap hashMap=new HashMap();
		hashMap.put("ProbePort",probeport);
		return sysmanageProbeParDao.ExisProbePort(hashMap);
	}

}
