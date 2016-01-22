package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_monitor_oilgun;
import com.kld.gsm.center.domain.oss_monitor_oilgunKey;
import org.springframework.stereotype.Repository;

@MysqlRepository
public interface oss_monitor_oilgunMapper {
    int deleteByPrimaryKey(oss_monitor_oilgunKey key);

    int insert(oss_monitor_oilgun record);

    int insertSelective(oss_monitor_oilgun record);

    oss_monitor_oilgun selectByPrimaryKey(oss_monitor_oilgunKey key);

    int updateByPrimaryKeySelective(oss_monitor_oilgun record);

    int updateByPrimaryKey(oss_monitor_oilgun record);

    int merge(oss_monitor_oilgun record);
}