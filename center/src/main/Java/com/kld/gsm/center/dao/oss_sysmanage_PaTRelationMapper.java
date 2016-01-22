package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_sysmanage_PaTRelation;
import com.kld.gsm.center.domain.oss_sysmanage_PaTRelationKey;
import org.springframework.stereotype.Repository;

@MysqlRepository
public interface oss_sysmanage_PaTRelationMapper {
    int deleteByPrimaryKey(oss_sysmanage_PaTRelationKey key);

    int insert(oss_sysmanage_PaTRelation record);

    int insertSelective(oss_sysmanage_PaTRelation record);

    oss_sysmanage_PaTRelation selectByPrimaryKey(oss_sysmanage_PaTRelationKey key);

    int updateByPrimaryKeySelective(oss_sysmanage_PaTRelation record);

    int updateByPrimaryKey(oss_sysmanage_PaTRelation record);

    int merge(oss_sysmanage_PaTRelation record);
}