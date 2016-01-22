package com.kld.app.service;

import com.kld.gsm.ATG.domain.DailyDailyBaseEquipment;
import com.kld.gsm.ATG.domain.DailyDeviceInfo;
import com.kld.gsm.ATG.domain.DailyDeviceInfoDetail;

import java.util.List;

public interface DailyDailyBaseEquipmentService {
    DailyDeviceInfo selectByPrimaryKey(Integer oilcanno);
    List<DailyDailyBaseEquipment> findByOilCanNo(String oilcan);
    List<DailyDailyBaseEquipment> findAll();
    DailyDeviceInfoDetail findByNO(String oilcanno);
}
