package com.kld.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kld.app.service.SysManageDictService;
import com.kld.gsm.ATG.dao.SysManageDictDao;
import com.kld.gsm.ATG.domain.SysManageDict;

@Service("dictService")

public class SysManageDictServiceImpl implements SysManageDictService {

	@Resource
	private SysManageDictDao sysManageDictDao;
	
	@Override
	public List<SysManageDict> selectAll() {
		return sysManageDictDao.selectAll();
	}

	@Override
	public String selectByName(String name) {
		return sysManageDictDao.selectByName(name);
	}

	@Override
	public int selectDictidByName(String name) {
		return sysManageDictDao.selectDictidByName(name);
	}

	@Override
	public List<String> selectSBByDictID(int DicID) {
		return sysManageDictDao.selectSBByDictID(DicID);
	}

	@Override
	public List<String> selectYJLXByDictID() {
		return sysManageDictDao.selectYJLXByDictID();

	}

	@Override
	public String selectBySort(Integer sort) {
		return sysManageDictDao.selectBySort(sort);
	}

	@Override
	public String selectBySort1(Integer sort) {
		return  sysManageDictDao.selectBySort1(sort);
	}

	@Override
	public String selectBySort2(Integer sort) {
		return sysManageDictDao.selectBySort2(sort);
	}

}
