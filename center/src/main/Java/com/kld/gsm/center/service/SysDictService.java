package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.SysManageDict;

import java.util.HashMap;
import java.util.List;


/**
 * Created by fangzhun on 2015/12/4.
 */
public interface SysDictService {
    //获取配送建议阀值
    int selectPSYJFZ();
    List<HashMap<String,String>> selectYJLX();
    SysManageDict selectByCode(String code);
    List<HashMap<String,String>> selectByParentId(String parentid);

    List<SysManageDict> selectbyVersion(Integer version);
}
