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
import java.util.HashMap;
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
    public String selectUserIdByRealname(String realname) {
        return sys_userDao.selectUserIdByRealname(realname);
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

    @Override
    public int insertRow(HashMap map) {
        return sys_userDao.insertRow(map);
    }

    @Override
    public int updateRow(HashMap map) {
        return sys_userDao.updateRow(map);
    }

    @Override
    public int delRow(String username) {return sys_userDao.deleteByPrimaryKey(username);}

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
    public List<HashMap<String,Object>> getSysUserPageList(Integer pageNo, Integer pageSize, String id, String username, String realname) {
        if (pageNo != null && pageSize != null) {
            pageNo = pageNo < 1 ? 1 : pageNo;
            int firstRow = (pageNo - 1) * pageSize;
            HashMap hashMap = new HashMap();
            hashMap.put("firstRow", firstRow);
            hashMap.put("pageSize", pageSize);
            hashMap.put("username", username);
            hashMap.put("realname", realname);
            System.out.println("判断之前的id为:" + id);
            if(null!=id  &&!"".equals(id)) {
                hashMap.put("oucode", id + "%");
            }else {
                hashMap.put("oucode","");
            }
            System.out.println("hashMap:" + hashMap.toString());
            return sys_userDao.getSysUserPageList(hashMap);
        }
        return null;
    }

    @Override
    public List<HashMap<String,Object>> getUserList(String id, String username, String realname) {
        HashMap Map = new HashMap();
        Map.put("username", username);
        Map.put("realname", realname);
        if(null!=id && !"".equals(id)) {
            Map.put("oucode", id + "%");
        } else {
            Map.put("oucode", "");
        }
        return sys_userDao.getUserPageList(Map);    }

    @Override
    public List<Sys_user> getApprovalUserList() {
        return sys_userDao.getApprovalUserList();
    }

    @Override
    public int updateByPrimaryKeySelective(Sys_user sys_user) {
        return sys_userDao.updateByPrimaryKeySelective(sys_user);
    }
}
