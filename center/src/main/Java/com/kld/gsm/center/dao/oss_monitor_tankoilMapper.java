package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_monitor_tankoil;
import com.kld.gsm.center.domain.oss_monitor_tankoilKey;
import org.springframework.stereotype.Repository;

@MysqlRepository
public interface oss_monitor_tankoilMapper {
    int deleteByPrimaryKey(oss_monitor_tankoilKey key);

    int insert(oss_monitor_tankoil record);

    int insertSelective(oss_monitor_tankoil record);

    oss_monitor_tankoil selectByPrimaryKey(oss_monitor_tankoilKey key);

    int updateByPrimaryKeySelective(oss_monitor_tankoil record);

    int updateByPrimaryKey(oss_monitor_tankoil record);

    int merge(oss_monitor_tankoil record);
}