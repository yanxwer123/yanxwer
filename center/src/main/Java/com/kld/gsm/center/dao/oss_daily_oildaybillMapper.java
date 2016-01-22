package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_daily_oildaybill;
import com.kld.gsm.center.domain.oss_daily_oildaybillKey;
import org.springframework.stereotype.Repository;

@MysqlRepository
public interface oss_daily_oildaybillMapper {
    int deleteByPrimaryKey(oss_daily_oildaybillKey key);

    int insert(oss_daily_oildaybill record);

    int insertSelective(oss_daily_oildaybill record);

    oss_daily_oildaybill selectByPrimaryKey(oss_daily_oildaybillKey key);

    int updateByPrimaryKeySelective(oss_daily_oildaybill record);

    int updateByPrimaryKey(oss_daily_oildaybill record);
}