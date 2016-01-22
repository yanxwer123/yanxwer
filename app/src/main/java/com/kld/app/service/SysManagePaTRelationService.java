package com.kld.app.service;

import java.util.List;

public interface SysManagePaTRelationService {

	 List<com.kld.gsm.ATG.domain.SysManagePaTRelation> selectByOilNo(String oilCan);
}
