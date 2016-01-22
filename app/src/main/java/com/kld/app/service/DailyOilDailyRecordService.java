package com.kld.app.service;

import java.util.Date;
import java.util.List;

public interface DailyOilDailyRecordService {
	
	List<com.kld.gsm.ATG.domain.DailyOilDailyRecord> selectByDate(Date begin, Date end);
	
}
