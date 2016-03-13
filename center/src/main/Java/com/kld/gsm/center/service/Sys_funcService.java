package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.Sys_func;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jw on 2015/7/30.
 */
public interface Sys_funcService {


    int insertRow(HashMap map);

    int updateRow(HashMap map);

    int delRow(String funccode);

    List<Sys_func> getFuncList(Map<String, Object> map);

    List<Sys_func> selectBycode(String parentcode);


    List<HashMap<String,Object>>  getCatalogList(Integer intPage,Integer intPageSize);

    List<HashMap<String,Object>> getCatalogAllList();

    List<Sys_func> selectAll();


}
