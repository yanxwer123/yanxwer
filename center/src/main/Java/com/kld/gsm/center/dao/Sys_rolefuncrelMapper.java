package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.Sys_rolefuncrel;
import com.kld.gsm.center.domain.Sys_rolefuncrelKey;
import com.kld.gsm.center.domain.Sys_userrole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MysqlRepository
public interface Sys_rolefuncrelMapper {
    int deleteByPrimaryKey(Sys_rolefuncrelKey key);

    int insert(Sys_rolefuncrel record);

    int insertSelective(Sys_rolefuncrel record);

    Sys_rolefuncrel selectByPrimaryKey(Sys_rolefuncrelKey key);

    int updateByPrimaryKeySelective(Sys_rolefuncrel record);

    int updateByPrimaryKey(Sys_rolefuncrel record);

    int deleteRoleFuncByRolecode(@Param("rolecode")String rolecode);

    List<Sys_rolefuncrel> selectAll();

    List<Sys_rolefuncrel>  selectByrolecode(String rolecode);

    List<Sys_rolefuncrel>  selectRCBycode(String delfunccode);

    int insertList(List<Sys_rolefuncrel> list);

    List<Sys_rolefuncrel> selectRCBylist(List<Sys_userrole> sysUserRole);

}