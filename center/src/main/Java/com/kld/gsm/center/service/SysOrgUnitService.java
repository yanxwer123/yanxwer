package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.oss_sys_OrgUnit;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/11/19.
 */
/*
Created BY niyang
Created Date 2015/11/19
*/
public interface SysOrgUnitService {

    oss_sys_OrgUnit GetOrgByNodeNo(String nodeNo);
    oss_sys_OrgUnit selectByOUCode(String oucode);
    List<oss_sys_OrgUnit> selectByPOUCode(String parentoucode);
    List<HashMap<String,Object>> selectOUInfo(String oucode);
}
