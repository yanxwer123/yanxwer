package com.kld.gsm.ATG.service;

import com.kld.gsm.ATG.domain.SysManageDict;

import java.util.List;

/*
Created BY niyang
Created Date 2015/11/21
*/
public interface SysManageDic {
    SysManageDict GetByKey(Integer dicno);

    SysManageDict GetByCode(String code);


    List<SysManageDict> SelectAll();

    int getVersion();

    /**
     * @decription 同步字典
     * */
    int synDicFromCenter(String host);

    /**
     * @decription 根据父级编码，查询字典
     * @return 字典列表
     * */
    List<SysManageDict>getByParentCode(String parentcode);

}
