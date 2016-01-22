package com.kld.gsm.center.util;

import com.kld.gsm.center.domain.oss_sys_OrgUnit;
import com.kld.gsm.center.service.SysOrgUnitService;
import com.kld.gsm.center.service.impl.SysOrgUnitServiceImpl;

/*
Created BY niyang
Created Date 2015/11/19
*/
public class sysOrgUnit {

   public String GetOuCodeByNodeNo(String NodeNo){
       String oucode="";
      /* SysOrgUnitService syso=new SysOrgUnitServiceImpl();
       oss_sys_OrgUnit item=syso.GetOrgByNodeNo(NodeNo);
       if (item!=null){
          oucode=item.getOucode();
       }*/
       return oucode;

    }
}
