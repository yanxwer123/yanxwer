package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.SysmanageRealgiveoil;

@MySqlRepository
public interface SysmanageRealgiveoilDao {
    int deleteByPrimaryKey(String ckdid);

    int insert(SysmanageRealgiveoil record);

    int insertSelective(SysmanageRealgiveoil record);

    SysmanageRealgiveoil selectByPrimaryKey(String ckdid);

    int updateByPrimaryKeySelective(SysmanageRealgiveoil record);

    int updateByPrimaryKey(SysmanageRealgiveoil record);
}