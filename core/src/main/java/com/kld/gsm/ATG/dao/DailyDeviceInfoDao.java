package com.kld.gsm.ATG.dao;


import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.DailyDeviceInfo;
@MySqlRepository
public interface DailyDeviceInfoDao {
    int deleteByPrimaryKey(Integer oilcanno);

    int insert(DailyDeviceInfo record);

    int insertSelective(DailyDeviceInfo record);

    DailyDeviceInfo selectByPrimaryKey(Integer oilcanno);

    int updateByPrimaryKeySelective(DailyDeviceInfo record);

    int updateByPrimaryKey(DailyDeviceInfo record);

}