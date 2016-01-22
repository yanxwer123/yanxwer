package com.kld.gsm.ATG.dao;


import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.DailyStaticOilGunInverntory;
import com.kld.gsm.ATG.domain.DailyStaticOilGunInverntoryKey;

import java.util.List;

@MySqlRepository
public interface DailyStaticOilGunInverntoryDao {
    int deleteByPrimaryKey(DailyStaticOilGunInverntoryKey key);

    int insert(DailyStaticOilGunInverntory record);

    int insertSelective(DailyStaticOilGunInverntory record);

    DailyStaticOilGunInverntory selectByPrimaryKey(DailyStaticOilGunInverntoryKey key);

    int updateByPrimaryKeySelective(DailyStaticOilGunInverntory record);

    int updateByPrimaryKey(DailyStaticOilGunInverntory record);

    List<DailyStaticOilGunInverntory>selectByid(String id);
}