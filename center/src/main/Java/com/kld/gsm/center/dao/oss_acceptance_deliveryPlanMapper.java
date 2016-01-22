package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_acceptance_deliveryPlan;
import com.kld.gsm.center.domain.oss_acceptance_deliveryPlanKey;
@MysqlRepository
public interface oss_acceptance_deliveryPlanMapper {
    int deleteByPrimaryKey(oss_acceptance_deliveryPlanKey key);

    int insert(oss_acceptance_deliveryPlan record);

    int insertSelective(oss_acceptance_deliveryPlan record);

    oss_acceptance_deliveryPlan selectByPrimaryKey(oss_acceptance_deliveryPlanKey key);

    int updateByPrimaryKeySelective(oss_acceptance_deliveryPlan record);

    int updateByPrimaryKey(oss_acceptance_deliveryPlan record);

    int merge(oss_acceptance_deliveryPlan record);
}