package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.AcceptanceOdRegisterInfo;
import com.kld.gsm.ATG.domain.AcceptanceOdRegisterInfoKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MySqlRepository
public interface AcceptanceOdRegisterInfoDao {
    int deleteByPrimaryKey(AcceptanceOdRegisterInfoKey key);

    int insert(com.kld.gsm.ATG.domain.AcceptanceOdRegisterInfo record);

    int insertSelective(com.kld.gsm.ATG.domain.AcceptanceOdRegisterInfo record);

    AcceptanceOdRegisterInfo selectByPrimaryKey(AcceptanceOdRegisterInfoKey key);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.AcceptanceOdRegisterInfo record);

    List<AcceptanceOdRegisterInfo> selectById(String DeliveryNo);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.AcceptanceOdRegisterInfo record);

    List<AcceptanceOdRegisterInfo> selectByDeliveryNoDate(Map params);

    List<AcceptanceOdRegisterInfo> selectByTrans(String Trans);

    List<AcceptanceOdRegisterInfo> selectBydeliveryno(String deliveryno);

    int merge(AcceptanceOdRegisterInfo record);

    int updateByManualNo(HashMap map);

    int deleteByDeliveryNo(String billno);

    AcceptanceOdRegisterInfo selectBegin(String billno);

    AcceptanceOdRegisterInfo selectEnd(String billno);


    /**
     * @param shift 油品+班次号
     * @return 卸油量
     */
    List<Map<String,Object>> findDischargeL(String shift);

    List<AcceptanceOdRegisterInfo> selectbycanno(Map map);

}