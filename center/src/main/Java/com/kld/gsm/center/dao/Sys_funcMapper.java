package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.Sys_func;

import java.util.List;
import java.util.Map;
@MysqlRepository
public interface Sys_funcMapper {
    int deleteByPrimaryKey(String funccode);

    int insert(Sys_func record);

    int insertSelective(Sys_func record);

    Sys_func selectByPrimaryKey(String funccode);

    int updateByPrimaryKeySelective(Sys_func record);

    int updateByPrimaryKey(Sys_func record);

    List<Sys_func> getFuncList(Map<String,Object> map);
}