package com.kld.gsm.syntocenter.service;


import com.kld.gsm.ATG.domain.SysManageCanInfo;

import java.util.List;

/*
Created BY niyang
Created Date 2015/11/19
*/
public interface synSysManage {
    // /sys/addalarmpar
    boolean synAlarmPar();

    // /sys/addcub
    boolean syncub();

   // /sys/addcubinfo
    boolean syncubinfo();

    // /sys/adddept
    boolean syndept();

    // /sys/addiq
    boolean syniq();

    // /sys/addoilgun
    boolean synoilgun();

    // /sys/addoilmac
    boolean  synoilmac();

    // /sys/addoiltype
    boolean synoiltype();

    // /sys/addpatrel
    boolean synpatrel();

    // /sys/addprobebar
    boolean synprobebar();

    // /sys/addtankinfo
    boolean syntankinfo();

    // /sys/addtimesaleout
    boolean syntimesaleout();

    // /sys/adduplist
    boolean synuplist();

    //boolean upLoad();
    List<SysManageCanInfo> getCaninfos();
}
