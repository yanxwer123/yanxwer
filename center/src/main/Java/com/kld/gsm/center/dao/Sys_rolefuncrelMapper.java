package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.Sys_rolefuncrel;
import com.kld.gsm.center.domain.Sys_rolefuncrelKey;
import org.apache.ibatis.annotations.Param;
@MysqlRepository
public interface Sys_rolefuncrelMapper {
    int deleteByPrimaryKey(Sys_rolefuncrelKey key);

    int insert(Sys_rolefuncrel record);

    int insertSelective(Sys_rolefuncrel record);

    Sys_rolefuncrel selectByPrimaryKey(Sys_rolefuncrelKey key);

    int updateByPrimaryKeySelective(Sys_rolefuncrel record);

    int updateByPrimaryKey(Sys_rolefuncrel record);

    int deleteRoleFuncByRolecode(@Param("rolecode")String rolecode);


}