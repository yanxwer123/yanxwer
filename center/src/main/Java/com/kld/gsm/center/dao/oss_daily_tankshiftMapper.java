package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_daily_tankshift;
import com.kld.gsm.center.domain.oss_daily_tankshiftKey;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@MysqlRepository
public interface oss_daily_tankshiftMapper {
    int deleteByPrimaryKey(oss_daily_tankshiftKey key);

    int insert(oss_daily_tankshift record);

    int merge(oss_daily_tankshift record);

    int insertSelective(oss_daily_tankshift record);

    oss_daily_tankshift selectByPrimaryKey(oss_daily_tankshiftKey key);

    int updateByPrimaryKeySelective(oss_daily_tankshift record);

    int updateByPrimaryKey(oss_daily_tankshift record);
    List<HashMap<String,Object>> selectByShift(String shift);
}