package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_acceptance_odRegister;
import com.kld.gsm.center.domain.oss_acceptance_odRegisterKey;

import java.util.HashMap;
import java.util.List;

@MysqlRepository
public interface oss_acceptance_odRegisterMapper {
    int deleteByPrimaryKey(oss_acceptance_odRegisterKey key);

    int insert(oss_acceptance_odRegister record);

    int insertSelective(oss_acceptance_odRegister record);

    oss_acceptance_odRegister selectByPrimaryKey(oss_acceptance_odRegisterKey key);

    int updateByPrimaryKeySelective(oss_acceptance_odRegister record);

    int updateByPrimaryKey(oss_acceptance_odRegister record);

    int merge(oss_acceptance_odRegister record);

    int deletebybillnoAndnodeno(String bill,String nodeno);

    List<HashMap<String,Object>> selectYYS(HashMap map);
}