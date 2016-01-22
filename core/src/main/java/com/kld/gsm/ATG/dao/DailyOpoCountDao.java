package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.DailyOpoCount;
import com.kld.gsm.ATG.domain.DailyOpoCountKey;

import java.util.List;
import java.util.Map;

@MySqlRepository
public interface DailyOpoCountDao {
    int deleteByPrimaryKey(DailyOpoCountKey key);

    int insert(com.kld.gsm.ATG.domain.DailyOpoCount record);

    int insertSelective(com.kld.gsm.ATG.domain.DailyOpoCount record);

    com.kld.gsm.ATG.domain.DailyOpoCount selectByPrimaryKey(DailyOpoCountKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.DailyOpoCount record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.DailyOpoCount record);
    List<DailyOpoCount> selectByTrans(String stauts);

    int updateByShift(Map<String,String> map);

}