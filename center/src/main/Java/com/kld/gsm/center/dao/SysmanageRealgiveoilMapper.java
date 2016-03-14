package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.SysmanageRealgiveoil;

@MysqlRepository
public interface SysmanageRealgiveoilMapper {
    int deleteByPrimaryKey(String ckdid);

    int insert(SysmanageRealgiveoil record);

    int insertSelective(SysmanageRealgiveoil record);

    SysmanageRealgiveoil selectByPrimaryKey(String ckdid);

    int updateByPrimaryKeySelective(SysmanageRealgiveoil record);

    int updateByPrimaryKey(SysmanageRealgiveoil record);
}