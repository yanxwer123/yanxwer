package com.kld.gsm.ATG.dao;

import java.util.List;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.DailyDailyBaseEquipment;

@MySqlRepository
public interface DailyDailyBaseEquipmentDao {
	
	 List<DailyDailyBaseEquipment> selectByOilNo(String oilno);
}
