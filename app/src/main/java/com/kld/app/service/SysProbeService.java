package com.kld.app.service;

import java.util.List;

import com.kld.gsm.ATG.domain.SysManageProbePar;

public interface SysProbeService {
	int insert(SysManageProbePar record);
	
	List<SysManageProbePar> selectAll();
	
	int deleteByPrimaryKey(String probemodel);

	int update(SysManageProbePar record);

	SysManageProbePar selectModelByProbemodel(String Probemodel);

	int ExisOilCan(Integer OilCan);

	int ExisProbePort(Integer probeport);

}
