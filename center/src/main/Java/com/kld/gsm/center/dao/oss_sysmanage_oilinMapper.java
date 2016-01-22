package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_sysmanage_oilin;
import com.kld.gsm.center.domain.oss_sysmanage_oilinKey;
import org.springframework.stereotype.Repository;

@MysqlRepository
public interface oss_sysmanage_oilinMapper {
    int deleteByPrimaryKey(oss_sysmanage_oilinKey key);

    int insert(oss_sysmanage_oilin record);

    int insertSelective(oss_sysmanage_oilin record);

    oss_sysmanage_oilin selectByPrimaryKey(oss_sysmanage_oilinKey key);

    int updateByPrimaryKeySelective(oss_sysmanage_oilin record);

    int updateByPrimaryKey(oss_sysmanage_oilin record);
}