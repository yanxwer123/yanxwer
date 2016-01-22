package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.SysManageDepartment;

import java.util.List;

@MySqlRepository
public interface SysManageDepartmentDao {
    int deleteAll();

    int deleteByPrimaryKey(String sinopecnodeno);


    int insert(SysManageDepartment record);

    int insertSelective(SysManageDepartment record);

    SysManageDepartment selectByPrimaryKey(String sinopecnodeno);

    int updateByPrimaryKeySelective(SysManageDepartment record);

    int updateByPrimaryKey(SysManageDepartment record);

    List<SysManageDepartment> selectByTrans(String trans);

    List<SysManageDepartment> selectfirst();
}