package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import java.util.List;
import com.kld.gsm.ATG.domain.AcceptanceDeliveryBills;
@MySqlRepository
public interface AcceptanceDeliveryDao {
    int deleteByPrimaryKey(String deliveryno);

    int insert(AcceptanceDeliveryBills record);

    int insertSelective(AcceptanceDeliveryBills record);

    AcceptanceDeliveryBills selectByPrimaryKey(String deliveryno);

    int updateByPrimaryKeySelective(AcceptanceDeliveryBills record);

    int updateByPrimaryKey(AcceptanceDeliveryBills record);
    
    List<AcceptanceDeliveryBills> selectAll();
}