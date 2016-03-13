package com.kld.gsm.ATG.dao;


import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.AcceptanceDeliveryBills;

import java.util.List;

/**
 * Created by chen on 2015/11/7.
 */
@MySqlRepository
public interface AcceptanceDeliveryBillDao {

    AcceptanceDeliveryBills selectAcceptanceDeliveryBill(String DeliveryNo);
	
    AcceptanceDeliveryBills selectByPrimaryKey(String DeliveryNo);
    int updateByPrimaryKey(AcceptanceDeliveryBills adBills);
    List<AcceptanceDeliveryBills> selectByTrans(String stauts);
    int merge(AcceptanceDeliveryBills record);
    List<AcceptanceDeliveryBills> selectByIsComplete(String iscomplete);
    List<AcceptanceDeliveryBills> selectAll();

    int deleteByPrimaryKey(String deliveryno);

    int insert(AcceptanceDeliveryBills record);

    int insertSelective(AcceptanceDeliveryBills record);


    int updateByPrimaryKeySelective(AcceptanceDeliveryBills record);




}
