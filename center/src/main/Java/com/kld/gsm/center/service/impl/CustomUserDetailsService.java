/**
 * 58.com Inc.
 * Copyright (c) 2005-2015 All Rights Reserved.
 */
package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.UserAuthorityPointDao;
import com.kld.gsm.center.domain.Sys_user;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


public class CustomUserDetailsService implements UserDetailsService {

    @Resource
    private UserAuthorityPointDao userAuthorityPointDao;

    @Resource
    private  UserServiceImpl userServiceImpl;
    
    static final Log LOG = LogFactory.getLog(CustomUserDetailsService.class.getName());
    
    public static HashMap<String,List<HashMap<String,String>>> authorityName = new HashMap<String,List<HashMap<String,String>>>();
    
    public static HashMap<String,List<HashMap<String,String>>> authorityFuncUrl = new HashMap<String,List<HashMap<String,String>>>();
    
    /** 
     * @see UserDetailsService#loadUserByUsername(String)
     */
    @Override
    public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
        UserDetails user = null;
        try {
            Sys_user sysUser = userServiceImpl.querySysUserByUserName(arg0);
            if(sysUser!=null){
                List<HashMap<String,Object>> userAuthorityPoint = userAuthorityPointDao.getauthorityPointListByUserId(arg0);
//                //System.out.println("结果集条数==========================================" + userAuthorityPoint.size());
//                //System.out.println("结果集1条==========================================" + userAuthorityPoint.get(0));
//                //System.out.println("结果集N条=========================================="+userAuthorityPoint.get(userAuthorityPoint.size() - 1));
                user = new User(arg0, sysUser.getPwd() ,true, true, true, true, getAuthorities(userAuthorityPoint));
//                //System.out.println("username=========================================="+user.getUsername());
//                //System.out.println("userpass=========================================="+user.getPassword());
//                //System.out.println("userauthoritysize==========================================" + user.getAuthorities().size());
                getFuncUrl(userAuthorityPoint, arg0);

//                //System.out.println("funcName==========================================" + authorityName.get(user.getUsername()));
//                //System.out.println("funcUrlMap==========================================" + authorityFuncUrl.get(user.getUsername()));
            }
  
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Error authority user");  
            throw new UsernameNotFoundException("Error authority user");
        }  
  
        return user;  
    }

    @SuppressWarnings("deprecation")
    public Collection<GrantedAuthority> getAuthorities(List<HashMap<String,Object>> access) {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        if(access!=null) {
            for(HashMap<String,Object> point : access){
//                //System.out.println("权限=========================================="+point.toString());
                authList.add(new GrantedAuthorityImpl((String)point.get("funccode")));
            }
        }
        return authList;  
    }  
    
    private void getFuncUrl(List<HashMap<String,Object>> userAuthorityPoint,String userName){
        List<HashMap<String,String>> funcUrlMap = new ArrayList<HashMap<String,String>>();
        HashMap<String, String> tempMap = new HashMap<String, String>();
        List<String> funcCode = new ArrayList<String>();
        List<HashMap<String,String>> funcName = new ArrayList<HashMap<String,String>>();
        //左侧一级栏目
        for(HashMap<String,Object> point : userAuthorityPoint){
            if(String.valueOf("1000000").equals(String.valueOf(point.get("parentcode")))) {
                funcCode.add(String.valueOf(point.get("funccode")));
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("FuncName", (String) point.get("name"));
                map.put("ImageUrl", (String) point.get("picurl"));
                funcName.add(map);
                tempMap.put(String.valueOf(point.get("funccode")), (String) point.get("name"));
            }
        }
        authorityName.put(userName, funcName);
        //左侧二级栏目
        for(String f : funcCode){
//            //System.out.println("一级栏目=========================================="+f);
            for(HashMap<String,Object> point : userAuthorityPoint){
//                //System.out.println("小类别=========================================="+point.toString());
                if(f.equals(String.valueOf(point.get("parentcode")))){
                    String name = tempMap.get(point.get("parentcode"));
                    HashMap<String,String> nameAndUrl = new HashMap<String,String>();
                    nameAndUrl.put("TargetURL", (String)point.get("targeturl"));
                    nameAndUrl.put("FuncName", (String)point.get("name"));
                    nameAndUrl.put("ParentFuncName", name);
                    funcUrlMap.add(nameAndUrl);
                }
            }
        }
        authorityFuncUrl.put(userName, funcUrlMap);
    }
}
