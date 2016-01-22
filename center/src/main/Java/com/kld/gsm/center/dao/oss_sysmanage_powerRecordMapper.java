package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_sysmanage_powerRecord;
import com.kld.gsm.center.domain.oss_sysmanage_powerRecordKey;
import org.springframework.stereotype.Repository;

@MysqlRepository
public interface oss_sysmanage_powerRecordMapper {
    int deleteByPrimaryKey(oss_sysmanage_powerRecordKey key);

    int insert(oss_sysmanage_powerRecord record);

    int insertSelective(oss_sysmanage_powerRecord record);

    oss_sysmanage_powerRecord selectByPrimaryKey(oss_sysmanage_powerRecordKey key);

    int updateByPrimaryKeySelective(oss_sysmanage_powerRecord record);

    int updateByPrimaryKey(oss_sysmanage_powerRecord record);
}