package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.AlarmDailyLost;
import com.kld.gsm.ATG.domain.DailyOpotCountKey;

import java.util.List;

@MySqlRepository
public interface DailyOpotCountDao {
    int deleteByPrimaryKey(DailyOpotCountKey key);

    int insert(com.kld.gsm.ATG.domain.DailyOpotCount record);

    int insertSelective(com.kld.gsm.ATG.domain.DailyOpotCount record);

    com.kld.gsm.ATG.domain.DailyOpotCount selectByPrimaryKey(DailyOpotCountKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.DailyOpotCount record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.DailyOpotCount record);

    List<com.kld.gsm.ATG.domain.DailyOpotCount> selectByTrans(String stauts);

    List<com.kld.gsm.ATG.domain.DailyOpotCount> selectFyxx(String shift);
}