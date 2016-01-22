package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_daily_opotCount;
import com.kld.gsm.center.domain.oss_daily_opotCountKey;
import org.springframework.stereotype.Repository;

import java.util.List;

@MysqlRepository
public interface oss_daily_opotCountMapper {
    int deleteByPrimaryKey(oss_daily_opotCountKey key);

    int insert(oss_daily_opotCount record);

    int insertSelective(oss_daily_opotCount record);

    oss_daily_opotCount selectByPrimaryKey(oss_daily_opotCountKey key);

    int updateByPrimaryKeySelective(oss_daily_opotCount record);

    int updateByPrimaryKey(oss_daily_opotCount record);
    List<oss_daily_opotCount> selectFyxx(String shift);
}