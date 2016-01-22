package com.kld.app.service.impl;

import com.kld.app.service.DailyDailyBaseEquipmentService;
import com.kld.app.springcontext.Context;
import com.kld.gsm.ATG.dao.DailyDeviceInfoDao;
import com.kld.gsm.ATG.dao.DailyDeviceInfoDetailDao;
import com.kld.gsm.ATG.domain.DailyDailyBaseEquipment;
import com.kld.gsm.ATG.domain.DailyDeviceInfo;
import com.kld.gsm.ATG.domain.DailyDeviceInfoDetail;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@SuppressWarnings("all")
@Service("dailyDailyBaseEquipmentService")
public class DailyDailyBaseEquipmentServiceImpl implements DailyDailyBaseEquipmentService {
	@Resource
	 DailyDeviceInfoDao dailyDeviceInfoDao;
	@Resource
	 DailyDeviceInfoDetailDao dailyDeviceInfoDetailDao;


	@Override
	public DailyDeviceInfo selectByPrimaryKey(Integer oilcanno) {
		DailyDeviceInfo dailyDeviceInfo =dailyDeviceInfoDao.selectByPrimaryKey(oilcanno);
		return dailyDeviceInfo;
	}

	@Override
	public List<DailyDailyBaseEquipment> findByOilCanNo(String oilcan) {
		List<DailyDailyBaseEquipment> list =dailyDeviceInfoDetailDao.findByOilCanNo(oilcan);
		return list;
	}

	@Override
	public List<DailyDailyBaseEquipment> findAll() {
		List<DailyDailyBaseEquipment> list =dailyDeviceInfoDetailDao.findAll();
		return list;
	}

	@Override
	public DailyDeviceInfoDetail findByNO(String oilcanno) {
		DailyDeviceInfoDetail  dailyDeviceInfoDetail =dailyDeviceInfoDetailDao.findByNO(oilcanno);
		return dailyDeviceInfoDetail;
	}

	@Test
	public void a(){
		DailyDailyBaseEquipmentService dailyDailyBaseEquipmentService = Context.getInstance().getBean(DailyDailyBaseEquipmentService.class);
  			//////System.out.println("要查询所有");
		DailyDeviceInfoDetail	dailyDeviceInfoDetail   = dailyDailyBaseEquipmentService.findByNO("2");
		//////System.out.println("【"+dailyDeviceInfoDetail.getProbeno()+"】");

	}
}
