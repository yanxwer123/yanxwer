package com.kld.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.kld.gsm.ATG.domain.SysManageCanInfo;
import org.springframework.stereotype.Service;

import com.kld.app.service.MonitorTimeInventoryService;
import com.kld.app.service.SysManageDictService;
import com.kld.app.springcontext.Context;
import com.kld.gsm.ATG.dao.MonitorTimeInventoryDao;
import com.kld.gsm.ATG.dao.SysManageOilGunInfoDao;
import com.kld.gsm.ATG.dao.SysManageCanInfoDao;
import com.kld.gsm.ATG.domain.MonitorTimeInventory;
import com.kld.gsm.ATG.domain.SysManageDict;
import com.kld.gsm.ATG.domain.SysManageOilGunInfo;

/**
 * Created by 1 on 2015/10/26.
 */
@Service("monitorTimeInventoryService")
public class MonitorTimeInventoryServiceImpl implements MonitorTimeInventoryService {
    @Resource
    private MonitorTimeInventoryDao monitorTimeInventoryDao;

    @Resource
    private SysManageCanInfoDao sysManageCanInfoDao;
    
    @Resource
    private SysManageOilGunInfoDao sysManageOilGunInfoDao;
    
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(MonitorTimeInventory record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(MonitorTimeInventory record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MonitorTimeInventory selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return monitorTimeInventoryDao.selectBeginDataByCanNo(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MonitorTimeInventory record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(MonitorTimeInventory record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getTimeInventoryList(Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MonitorTimeInventory> querySdkc(HashMap map) {
		return monitorTimeInventoryDao.querySdkc(map);
	}
    
	
	@Override
	public List<SysManageCanInfo> selectAll() {
		// TODO Auto-generated method stub
		return sysManageCanInfoDao.selectAll();
	}
	
	@Override
	public List<SysManageOilGunInfo> selectAllOilGun() {
		// TODO Auto-generated method stub
		return sysManageOilGunInfoDao.selectAllOilGun();
	}
	
	public List<MonitorTimeInventory> selectAllOilCanInfoByOilNo(String oilNo) {
        return this.monitorTimeInventoryDao.selectAllOilCanInfoByOilNo(oilNo);
    }

    @Override
    public MonitorTimeInventory selectBeginDataByCanNo(Integer oilCan) {
        return this.monitorTimeInventoryDao.selectBeginDataByCanNo(oilCan);
    }
	
	public static void main(String[] args) {
//		MonitorTimeInventoryService dailyStationShiftInfoService =
//				(MonitorTimeInventoryService) (Context.getInstance().getBean("monitorTimeInventoryService"));
//		List<SysManageTankInfo> list =dailyStationShiftInfoService.selectAll();	
//		for (SysManageTankInfo sysManageTankInfo : list) {
//			////System.out.println("sysManageTankInfo"+sysManageTankInfo.getOilcan());
//		}
//		MonitorTimeInventory info = dailyStationShiftInfoService.selectByPrimaryKey(1);
//		////System.out.println(info.getStoretime());
		//字典数据
		SysManageDictService dictService =(SysManageDictService) (Context.getInstance().getBean("dictService"));
    	List<SysManageDict> list=dictService.selectAll();
    	for (SysManageDict sysManageDict : list) {
    		////System.out.println(sysManageDict);
		}
	}

	

}
