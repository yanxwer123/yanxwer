package com.kld.gsm.ATG.service;

import com.kld.gsm.ATG.domain.*;

import java.util.List;

/*
Created BY niyang
Created Date 2015/11/22
*/
public interface SysmanageService {
    //获取容积表
    boolean GetCubgeByNodeNo(String host,String nodeno);

    int GetCubgeByNodeNobackInt(String host,String nodeno);

    //获取报警参数
    boolean GetAlarmPar(String host,String nodeno);

    int GetAlarmParbackInt(String host,String nodeno);

    SysManageDepartment getdeptinfo();

    List<SysManageCubage> selectCubageInused();

    List<SysManageCanInfo> getCaninfos();
}
