package com.kld.app.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.kld.gsm.ATG.dao.*;
import com.kld.gsm.ATG.domain.DailyOpotCount;
import org.springframework.stereotype.Service;

import com.kld.app.service.DailyStationShiftInfoService;
import com.kld.app.springcontext.Context;
import com.kld.gsm.ATG.domain.DailyStationShiftInfo;
import com.kld.gsm.ATG.domain.DailyTankShift;

/**
 * Created by 1 on 2015/10/26.
 */
@Service("dailyStationShiftInfoService")
public class DailyStationShiftInfoServiceImpl implements DailyStationShiftInfoService {
    @Resource
    private DailyStationShiftInfoDao dailyStationShiftInfoDao;

    @Resource
    private DailyPumpDigitShiftDao dailyPumpDigitShiftDao;
    
    @Resource
    private DailyTankShiftDao dailyTankShiftDao;
    
    @Resource
    private DailyTradeAccountDao dailyTradeAccountDao;

	@Resource
	private DailyOpotCountDao dailyOpotCountDao;

	public static void main(String[] args) {
		DailyStationShiftInfoService iquidInstrumentService =
				(DailyStationShiftInfoService) (Context.getInstance().getBean("dailyStationShiftInfoService"));
		DailyStationShiftInfo info = new DailyStationShiftInfo();
//		info.setId(1);//id
		info.setShift(20151114);//班次
		info.setShiftoperator("交班人1");
		info.setShifttime(new Date());
		info.setSucceedtime(new Date());
		info.setSuccessor("接班人1");
		info.setTranstatus("y");
//		iquidInstrumentService.insert(info);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			List<DailyStationShiftInfo> xx = iquidInstrumentService.selectByDate(sdf.parse("2008-07-10"), sdf.parse("2016-07-10"));
			//////System.out.println(xx.get(0).getShift());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	 	//////System.out.println(sdf.format(iquidInstrumentService.selectByPrimaryKey(20151114).getSucceedtime()));
	}


	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int insert(DailyStationShiftInfo record) {
		// TODO Auto-generated method stub
		return dailyStationShiftInfoDao.insert(record);
	}


	@Override
	public int insertSelective(DailyStationShiftInfo record) {
		// TODO Auto-generated method stub
		return dailyStationShiftInfoDao.insertSelective(record);
	}


	@Override
	public DailyStationShiftInfo selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return dailyStationShiftInfoDao.selectByPrimaryKey(id);
	}


	@Override
	public int updateByPrimaryKeySelective(DailyStationShiftInfo record) {
		// TODO Auto-generated method stub
		return dailyStationShiftInfoDao.updateByPrimaryKeySelective(record);
	}


	@Override
	public int updateByPrimaryKey(DailyStationShiftInfo record) {
		// TODO Auto-generated method stub
		return dailyStationShiftInfoDao.updateByPrimaryKey(record);
	}

	@Override
	public List<DailyStationShiftInfo> selectByDate(Date begin, Date end) {
		HashMap map = new HashMap();
		map.put("begin", begin);
		map.put("end", end);
		return dailyStationShiftInfoDao.selectByDate(map);
	}

	@Override
	public List<com.kld.gsm.ATG.domain.DailyPumpDigitShift> queryGMxx(String shift) {
		// TODO Auto-generated method stub
		return dailyPumpDigitShiftDao.selectByShift(shift);
	}


//	@Override
//	public List<DailyTankShift> queryFYxx(String shift) {
//		return dailyTankShiftDao.selectByShift(shift);
//	}


	@Override
	public List<DailyTankShift> queryYGxx(String shift) {
		return dailyTankShiftDao.selectByShift(shift);
	}


	@Override
	public List queryFyxx(String shift,String OilNo) {
		// TODO Auto-generated method stub
		return dailyTradeAccountDao.selectFyxx(shift, OilNo);
	}


	@Override
	public List queryFyxxOilNo(String bc) {
		// TODO Auto-generated method stub
		return dailyTradeAccountDao.queryFyxxOilNo(bc);
	}

	@Override
	public List<DailyOpotCount> selectFyxx(String shift) {
		return dailyOpotCountDao.selectFyxx(shift);
	}


}
