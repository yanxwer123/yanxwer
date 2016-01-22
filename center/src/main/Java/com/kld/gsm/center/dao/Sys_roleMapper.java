package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.Sys_role;
@MysqlRepository
public interface Sys_roleMapper {
    int insert(Sys_role record);

    int insertSelective(Sys_role record);
}