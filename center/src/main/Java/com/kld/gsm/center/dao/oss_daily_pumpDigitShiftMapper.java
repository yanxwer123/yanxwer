package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_daily_pumpDigitShift;
import com.kld.gsm.center.domain.oss_daily_pumpDigitShiftKey;
import org.springframework.stereotype.Repository;

import java.util.List;

@MysqlRepository
public interface oss_daily_pumpDigitShiftMapper {
    int deleteByPrimaryKey(oss_daily_pumpDigitShiftKey key);

    int insert(oss_daily_pumpDigitShift record);

    int insertSelective(oss_daily_pumpDigitShift record);

    oss_daily_pumpDigitShift selectByPrimaryKey(oss_daily_pumpDigitShiftKey key);

    int updateByPrimaryKeySelective(oss_daily_pumpDigitShift record);

    int updateByPrimaryKey(oss_daily_pumpDigitShift record);
    List<oss_daily_pumpDigitShift> selectByShift(String shift);
}