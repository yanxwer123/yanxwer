package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_acceptance_deliveryBill;

import java.util.HashMap;
import java.util.List;

@MysqlRepository
public interface oss_acceptance_deliveryBillMapper {
    int deleteByPrimaryKey(String deliveryno);

    int insert(oss_acceptance_deliveryBill record);

    int insertSelective(oss_acceptance_deliveryBill record);

    oss_acceptance_deliveryBill selectByPrimaryKey(String deliveryno);

    int updateByPrimaryKeySelective(oss_acceptance_deliveryBill record);

    int updateByPrimaryKey(oss_acceptance_deliveryBill record);

    List<oss_acceptance_deliveryBill> selectByNodeNoandTrans(String nodeno,String trans);

    List<HashMap<String,Object>> selectDBill(HashMap map);
    List<HashMap<String,Object>> selectJHL(HashMap map);
    List<HashMap<String,Object>>  selectDeliveryBillPage(HashMap map);
    List<HashMap<String,Object>>  selectAllDeliveryBillPage(HashMap map);

}