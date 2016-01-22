package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.DailyOpoCount;
import com.kld.gsm.ATG.domain.DailyOpotDailyStatement;
import com.kld.gsm.ATG.domain.DailyOpotDailyStatementKey;

import java.util.List;

@MySqlRepository
public interface DailyOpotDailyStatementDao {
    int deleteByPrimaryKey(DailyOpotDailyStatementKey key);

    int insert(com.kld.gsm.ATG.domain.DailyOpotDailyStatement record);

    int insertSelective(com.kld.gsm.ATG.domain.DailyOpotDailyStatement record);

    com.kld.gsm.ATG.domain.DailyOpotDailyStatement selectByPrimaryKey(DailyOpotDailyStatementKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.DailyOpotDailyStatement record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.DailyOpotDailyStatement record);

    List<DailyOpotDailyStatement> selectByTrans(String stauts);
}