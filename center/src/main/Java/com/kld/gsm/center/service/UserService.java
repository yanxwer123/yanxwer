package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.Sys_user;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Created BY niyang
Created Date 2015/11/23
*/


public interface UserService {
    ResultMsg findSysUserByUserName(String userName) ;

    Sys_user querySysUserByUserName(String userName);

    String selectUserIdByRealname(String realname);

    Sys_user selectUserMoreInfo(String username);

    List<Sys_user> getSysUserList(Map<String,Object> map);

    int insert(Sys_user user);

    int insertRow(HashMap map);

    int updateRow(HashMap map);

    int delRow(String username);

    ResultMsg insertWithTrans(Sys_user user) throws Exception;
    int update(Sys_user user);

    List<Sys_user> getSysUserListByRolecode(String rolecode);

    List<HashMap<String,Object>> getSysUserPageList(Integer intPage,Integer intPageSize,String id,String username,String realname);

    List<HashMap<String,Object>> getUserList(String id,String username,String realname);

    List<Sys_user> getApprovalUserList();

    int updateByPrimaryKeySelective(Sys_user sys_user);
}
