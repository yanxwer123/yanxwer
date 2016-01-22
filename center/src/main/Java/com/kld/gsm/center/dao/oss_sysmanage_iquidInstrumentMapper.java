package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_sysmanage_iquidInstrument;
import com.kld.gsm.center.domain.oss_sysmanage_iquidInstrumentKey;
import org.springframework.stereotype.Repository;

@MysqlRepository
public interface oss_sysmanage_iquidInstrumentMapper {
    int deleteByPrimaryKey(oss_sysmanage_iquidInstrumentKey key);

    int insert(oss_sysmanage_iquidInstrument record);

    int insertSelective(oss_sysmanage_iquidInstrument record);

    oss_sysmanage_iquidInstrument selectByPrimaryKey(oss_sysmanage_iquidInstrumentKey key);

    int updateByPrimaryKeySelective(oss_sysmanage_iquidInstrument record);

    int updateByPrimaryKey(oss_sysmanage_iquidInstrument record);

    int merge(oss_sysmanage_iquidInstrument record);
}