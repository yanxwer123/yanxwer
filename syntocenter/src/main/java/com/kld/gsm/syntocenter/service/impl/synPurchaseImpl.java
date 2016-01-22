package com.kld.gsm.syntocenter.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.kld.gsm.ATG.domain.AcceptanceDeliveryBills;
import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.syntocenter.service.synPurchase;
import com.kld.gsm.syntocenter.util.action;
import com.kld.gsm.syntocenter.util.httpClient;
import com.kld.gsm.syntocenter.util.param;
import com.kld.gsm.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/*
Created BY niyang
Created Date 2015/11/19
*/
@SuppressWarnings("all")
@Service
public class synPurchaseImpl implements synPurchase {
    @Autowired
    private com.kld.gsm.ATG.dao.AcceptanceDeliveryBillDao acceptanceDeliveryBillDao;

    @Autowired
    private com.kld.gsm.ATG.dao.MonitorOilgunMapper monitorOilgunMapper;

    public int deliveryBillLost() {
        action ac=new action();
        String path=ac.getUri("resource.services.RCYX.AddTradeAccount");
        //获取站级数据
        List<AcceptanceDeliveryBills> AcceptanceDeliveryBillLst= acceptanceDeliveryBillDao.selectByTrans("0");
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,AcceptanceDeliveryBillLst,hm);
            for (AcceptanceDeliveryBills item:AcceptanceDeliveryBillLst){
                item.setTranstatus("1");
                acceptanceDeliveryBillDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    public int GetDeilveByNodeNo() {
        action ac=new action();
        String path=ac.getUri("resource.services.accept.getbillsbynodeno");
        Map<String,String> hm=new param().getparam();
        //发送请求
        httpClient client=new httpClient();
        try {
            String jsonresult=client.request(path,null,hm);
            JsonMapper jm=new JsonMapper();
            JavaType js=jm.createCollectionType(List.class,AcceptanceDeliveryBills.class);
            List<AcceptanceDeliveryBills> billes=jm.fromJson(jsonresult,js);
            for (AcceptanceDeliveryBills item:billes)
            acceptanceDeliveryBillDao.merge(item);

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    public int insertlist(List<MonitorOilgun> guns) {
      monitorOilgunMapper.insertList(guns);
        return 0;
    }
}
