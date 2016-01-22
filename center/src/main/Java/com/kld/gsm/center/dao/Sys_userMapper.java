package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.Sys_user;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@MysqlRepository
public interface Sys_userMapper {
    int deleteByPrimaryKey(String username);

    int insert(Sys_user record);

    int insertSelective(Sys_user record);

    Sys_user selectByPrimaryKey(String username);

    int updateByPrimaryKeySelective(Sys_user record);

    int updateByPrimaryKey(Sys_user record);

    List<Sys_user> getSysUserList(Map<String,Object> map);

    List<Sys_user> getSysUserListByRolecode(@Param("rolecode")String rolecode);

    Sys_user selectUserMoreInfo(String username);

    List<Sys_user> getApprovalUserList();
}