package com.kld.gsm.center.service.impl;


/*
Created BY niyang
Created Date 2015/11/23
*/

import com.kld.gsm.center.dao.Sys_userMapper;
import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.Sys_user;
import com.kld.gsm.center.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service("userService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UserServiceImpl implements UserService {

    @Resource
    private Sys_userMapper sys_userDao;


    @Override
    public ResultMsg findSysUserByUserName(String userName) {
        ResultMsg ResultMsg=new ResultMsg();
        List<Sys_user> orgUsers= null;
        sys_userDao.deleteByPrimaryKey("dad");
        //userMapperDao.queryOrgUserByUserID(userID);
        if(orgUsers.size()>0){
            ResultMsg.setResult(true);
            ResultMsg.setData(orgUsers);
        }else{
            ResultMsg.setResult(false);
        }
        return ResultMsg;
    }

    @Override
    public Sys_user querySysUserByUserName(String userName) {
        return sys_userDao.selectByPrimaryKey(userName);
    }

    @Override
    public Sys_user selectUserMoreInfo(String username){return sys_userDao.selectUserMoreInfo(username);}

    @Override
    public List<Sys_user> getSysUserList(Map<String,Object> map){
        return sys_userDao.getSysUserList(map);
    }

    @Override
    public int insert(Sys_user user){
        return sys_userDao.insert(user);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    @Override
    public ResultMsg insertWithTrans(Sys_user user) throws Exception{
        ResultMsg res = new ResultMsg();
        int r = insert(user);
        if(r>0){

            res.setResult(true);
            res.setMsg("添加成功！");
            res.setData(user.getUsername());
        }else {
            res.setResult(false);
            res.setMsg("创建账户失败");
        }
        return  res;
    }

    @Override
    public int update(Sys_user user) {
        return sys_userDao.updateByPrimaryKey(user);
    }
    @Override
    public List<Sys_user> getSysUserListByRolecode(String rolecode){
        return sys_userDao.getSysUserListByRolecode(rolecode);
    }

    @Override
    public List<Sys_user> getApprovalUserList() {
        return sys_userDao.getApprovalUserList();
    }

    @Override
    public int updateByPrimaryKeySelective(Sys_user sys_user) {
        return sys_userDao.updateByPrimaryKeySelective(sys_user);
    }
}
