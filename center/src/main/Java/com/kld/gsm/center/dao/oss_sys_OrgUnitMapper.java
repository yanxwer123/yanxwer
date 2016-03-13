package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_sys_OrgUnit;

import java.util.HashMap;
import java.util.List;

@MysqlRepository
public interface oss_sys_OrgUnitMapper {
    int deleteByPrimaryKey(String nodeno);

    int insert(oss_sys_OrgUnit record);

    int insertSelective(oss_sys_OrgUnit record);

    oss_sys_OrgUnit selectByPrimaryKey(String nodeno);

    int updateByPrimaryKeySelective(oss_sys_OrgUnit record);

    int updateByPrimaryKey(oss_sys_OrgUnit record);

    oss_sys_OrgUnit selectByOUCode(String oucode);
    List<oss_sys_OrgUnit> selectByPOUCode(String parentoucode);
    List<HashMap<String,Object>> selectOUInfo(HashMap map);

    String selectMaxOucodeByParentOucode(String oucode);
    
}