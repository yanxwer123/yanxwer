package com.kld.app.service;

import java.util.List;

import com.kld.gsm.ATG.domain.SysManageDict;
import com.kld.gsm.ATG.domain.SysManagePaTRelation;

public interface SysManageDictService {
	List<SysManageDict> selectAll();
	String selectByName(String name);
	int selectDictidByName(String name);
	List<String> selectSBByDictID(int DicID);
	List<String> selectYJLXByDictID();
	String selectBySort(Integer sort);
	String selectBySort1(Integer sort);
	String selectBySort2(Integer sort);


}
