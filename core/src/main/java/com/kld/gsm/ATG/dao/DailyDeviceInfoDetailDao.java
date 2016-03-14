package com.kld.gsm.ATG.dao;


import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.DailyDailyBaseEquipment;
import com.kld.gsm.ATG.domain.DailyDeviceInfoDetail;
import com.kld.gsm.ATG.domain.DailyDeviceInfoDetailKey;

import java.util.List;
@MySqlRepository
public interface DailyDeviceInfoDetailDao {
    int deleteByPrimaryKey(DailyDeviceInfoDetailKey key);

    int insert(DailyDeviceInfoDetail record);

    int insertSelective(DailyDeviceInfoDetail record);

    DailyDeviceInfoDetail selectByPrimaryKey(DailyDeviceInfoDetailKey key);

    int updateByPrimaryKeySelective(DailyDeviceInfoDetail record);

    int updateByPrimaryKey(DailyDeviceInfoDetail record);
    List<DailyDailyBaseEquipment>  findByOilCanNo(String oilcanno);
    List<DailyDailyBaseEquipment> findAll();
    DailyDeviceInfoDetail  findByNO(String oilcan);
    int deleteByOilCanNo(Integer oilcanno);

}