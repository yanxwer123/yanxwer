package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.domain.oss_acceptance_deliveryBill;
import com.kld.gsm.center.domain.oss_acceptance_nobills;
import com.kld.gsm.center.domain.oss_acceptance_odRegister;
import com.kld.gsm.center.domain.oss_acceptance_odRegisterInfo;
import com.kld.gsm.center.service.OilDownService;
import org.springframework.stereotype.Service;
import com.kld.gsm.center.dao.*;
import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Administrator on 2015/11/20.
 */
/*
Created BY niyang
Created Date 2015/11/20
*/
    @Service
public class OilDownServiceImpl implements OilDownService {
    @Resource
    private oss_acceptance_odRegisterMapper ossAcceptanceOdRegisterMapper;
    /*
    * 卸油登记表
    * */
    public int addOilDown(List<oss_acceptance_odRegister> ossAcceptanceOdRegisterInfos) {
        for (oss_acceptance_odRegister item:ossAcceptanceOdRegisterInfos){
            item.setTranstatus("0");
            ossAcceptanceOdRegisterMapper.merge(item);
        }
        return 1;
    }
    @Resource
    private oss_acceptance_odRegisterInfoMapper ossAcceptanceOdRegisterInfoMapper;
    public int addOilDownInfo(List<oss_acceptance_odRegisterInfo> oss_acceptance_odRegisterInfos) {
        for (oss_acceptance_odRegisterInfo item:oss_acceptance_odRegisterInfos){
            item.setTranstatus("0");
            ossAcceptanceOdRegisterInfoMapper.merge(item);
        }
        return 1;
    }

    @Resource
    private oss_acceptance_nobillsMapper ossAcceptanceNobillsMapper;
    public int addnobill(List<oss_acceptance_nobills> ossAcceptanceNobillses) {
        for (oss_acceptance_nobills item:ossAcceptanceNobillses){
            item.setTranstatus("0");
            ossAcceptanceNobillsMapper.merge(item);
        }
        return 1;
    }

    @Resource
    private oss_acceptance_deliveryBillMapper ossAcceptanceDeliveryBillMapper;

    /*
    * 添加出库单（湖南调用）
    * */
    public int addOutBills(List<oss_acceptance_deliveryBill> ossAcceptanceDeliveryBills) {
        return 0;
    }

    /*
    *根据nodeNo获取出库单
    * */
    public List<oss_acceptance_deliveryBill> getOutBillsByNodeNo(String nodeno) {

      List<oss_acceptance_deliveryBill> bills=ossAcceptanceDeliveryBillMapper.selectByNodeNoandTrans(nodeno,"0");
        for (oss_acceptance_deliveryBill item:bills){
            try {
                if (item.getTostationname()==null){
                    item.setTostationname("");
                }
                if (item.getFromdepotname()==null){
                    item.setFromdepotname("");
                }
                if (item.getCarno()==null){
                    item.setCarno("");
                }
                item.setTostationname(URLEncoder.encode(item.getTostationname(), "UTF-8"));
                item.setFromdepotname(URLEncoder.encode(item.getFromdepotname(), "UTF-8"));
                item.setCarno(URLEncoder.encode(item.getCarno(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
      return bills;
    }

    /*
    * 根据单号获取出库单(没有使用)
    * */
    public oss_acceptance_deliveryBill getOutBillByNo(String No) {
        return null;
    }
}
