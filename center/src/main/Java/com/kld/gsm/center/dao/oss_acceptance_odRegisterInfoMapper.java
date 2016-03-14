package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_acceptance_odRegisterInfo;
import com.kld.gsm.center.domain.oss_acceptance_odRegisterInfoKey;

import java.util.HashMap;
import java.util.List;

@MysqlRepository
public interface oss_acceptance_odRegisterInfoMapper {
    int deleteByPrimaryKey(oss_acceptance_odRegisterInfoKey key);

    int insert(oss_acceptance_odRegisterInfo record);

    int insertSelective(oss_acceptance_odRegisterInfo record);

    oss_acceptance_odRegisterInfo selectByPrimaryKey(oss_acceptance_odRegisterInfoKey key);

    int updateByPrimaryKeySelective(oss_acceptance_odRegisterInfo record);

    int updateByPrimaryKey(oss_acceptance_odRegisterInfo record);

    int merge(oss_acceptance_odRegisterInfo record);

    int deleteByDeliveryNo(String dno);

    List<HashMap<String,Object>> selectAcceptanceServicePage(HashMap map);

    List<HashMap<String,Object>> selectAllAcceptanceServicePage(String deliveryno);

}