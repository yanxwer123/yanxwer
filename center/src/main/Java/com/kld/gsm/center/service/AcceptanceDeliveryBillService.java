package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.oss_acceptance_deliveryBill;
import com.kld.gsm.center.domain.oss_alarm_DailyLost;

import javax.servlet.ServletOutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 5 on 2015/11/19.
 */
public interface AcceptanceDeliveryBillService {
    int AddDeliveryBill(List<oss_acceptance_deliveryBill> accdeliveryBillList);
    /**
     * 根据出库单号 查询
     * */
    oss_acceptance_deliveryBill selectBybillno(String billno);

    int updatedeliverybill(oss_acceptance_deliveryBill record);

    List<HashMap<String,Object>> selectDBill(String oiltype,String oucode);

    List<HashMap<String,Object>> selectJHL(String oiltype,String start,String end,String oucode);

    List<HashMap<String,Object>> selectDeliveryBillPage(Integer intPage,Integer intPageSize,String oucode,String deliveryno,String psdId,String startTime,String endTime,String yslx,String startTime1,String endTime1,String yjssts);

    List<HashMap<String,Object>> selectAllDeliveryBillPage(String oucode,String deliveryno,String psdId,String startTime,String endTime,String yslx,String startTime1,String endTime1,String yjssts);

    public void JHYS(List<HashMap<String,Object>> list,String [] titles,ServletOutputStream outputStream);


}
