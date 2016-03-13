package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.*;


import java.util.HashMap;
import java.util.List;

/**
 * Created by xhz on 2015/11/21.
 */
public interface AcceptanceService {
    int GetDeliveryPlan(List<oss_acceptance_deliveryPlan> ossAcceptanceDeliveryPlans);//从省级平台下载配送单表数据
    oss_sysmanage_Station GetStationInfo(String lsgc,String kcgg);
    oss_sys_OrgUnit GetOrgUnit(String nodeno);

    int addOdregAndregInfo(List<AcceptOdRegMain> acceptOdRegMains,String nodeno,String oucode);

    int updateByPrimaryKeySelective(oss_acceptance_odRegister record);

    int delAcceptBydeliverynoandnodeno(String nodeno,String deliveryno);

    oss_acceptance_odRegister selectById(String ManualNo);
    List<HashMap<String,Object>> selectYYS(String oiltype,String start,String end,String oucode);
    //oss_acceptance_deliveryBill selectbybillno(String billno);
    List<HashMap<String,Object>> selectAcceptanceServicePage(Integer intPage,Integer intPageSize,String deliveryno);

    List<HashMap<String,Object>> selectAllAcceptanceServicePage(String deliveryno);


}
