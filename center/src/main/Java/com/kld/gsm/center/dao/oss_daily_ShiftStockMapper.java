package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_daily_ShiftStock;
import com.kld.gsm.center.domain.oss_daily_ShiftStockKey;
import org.springframework.stereotype.Repository;

@MysqlRepository
public interface oss_daily_ShiftStockMapper {
    int deleteByPrimaryKey(oss_daily_ShiftStockKey key);

    int insert(oss_daily_ShiftStock record);

    int insertSelective(oss_daily_ShiftStock record);

    oss_daily_ShiftStock selectByPrimaryKey(oss_daily_ShiftStockKey key);

    int updateByPrimaryKeySelective(oss_daily_ShiftStock record);

    int updateByPrimaryKey(oss_daily_ShiftStock record);
}