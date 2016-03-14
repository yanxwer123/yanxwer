package com.kld.app.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.kld.gsm.ATG.domain.AcceptanceOdRegisterInfo;
import com.kld.gsm.ATG.domain.AcceptanceOdRegisterInfoKey;

public interface IAcceptanceOdRegisterInfoService {

    int deleteByPrimaryKey(AcceptanceOdRegisterInfoKey key);

    int insert(AcceptanceOdRegisterInfo record);

    int insertSelective(AcceptanceOdRegisterInfo record);

    AcceptanceOdRegisterInfo selectByPrimaryKey(AcceptanceOdRegisterInfoKey key);

    int updateByPrimaryKeySelective(AcceptanceOdRegisterInfo record);

    int updateByPrimaryKey(AcceptanceOdRegisterInfo record);
    
    List<AcceptanceOdRegisterInfo> selectByDeliveryNoDate(String deliveryNo, Date xyrqq, Date xyrqz);

    int merge(AcceptanceOdRegisterInfo record);

    List<AcceptanceOdRegisterInfo> selectbydeliveryno(String deliveryno);

    AcceptanceOdRegisterInfo selectendtime(String manualno);

    AcceptanceOdRegisterInfo selectbegintime(String manualno);

    List<AcceptanceOdRegisterInfo> selecbycanno(Integer canno,Date st,Date et);

    String findByOilCan(String oilcan,Date begintime,Date endtime);

    String findByOilNo(String oilno,Date begintime,Date endtime);

}
