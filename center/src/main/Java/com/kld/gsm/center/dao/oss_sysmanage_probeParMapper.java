package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_sysmanage_probePar;
import com.kld.gsm.center.domain.oss_sysmanage_probeParKey;
import org.springframework.stereotype.Repository;

@MysqlRepository
public interface oss_sysmanage_probeParMapper {
    int deleteByPrimaryKey(oss_sysmanage_probeParKey key);

    int insert(oss_sysmanage_probePar record);

    int insertSelective(oss_sysmanage_probePar record);

    oss_sysmanage_probePar selectByPrimaryKey(oss_sysmanage_probeParKey key);

    int updateByPrimaryKeySelective(oss_sysmanage_probePar record);

    int updateByPrimaryKey(oss_sysmanage_probePar record);
}