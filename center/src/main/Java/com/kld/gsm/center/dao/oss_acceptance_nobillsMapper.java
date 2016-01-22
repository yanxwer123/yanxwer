package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_acceptance_nobills;


@MysqlRepository
public interface oss_acceptance_nobillsMapper {
    int deleteByPrimaryKey(String deliveryno);

    int insert(oss_acceptance_nobills record);

    int insertSelective(oss_acceptance_nobills record);

    oss_acceptance_nobills selectByPrimaryKey(String deliveryno);

    int updateByPrimaryKeySelective(oss_acceptance_nobills record);

    int updateByPrimaryKey(oss_acceptance_nobills record);

    int merge(oss_acceptance_nobills record);
}