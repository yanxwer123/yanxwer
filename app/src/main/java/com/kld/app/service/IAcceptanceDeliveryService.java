package com.kld.app.service;

import java.util.List;

import com.kld.gsm.ATG.domain.*;

public interface IAcceptanceDeliveryService {
    int deleteByPrimaryKey(String deliveryno);

    int insert(AcceptanceDeliveryBills record);

    int insertSelective(AcceptanceDeliveryBills record);

    AcceptanceDeliveryBills selectByPrimaryKey(String deliveryno);

    int updateByPrimaryKeySelective(AcceptanceDeliveryBills record);

    int updateByPrimaryKey(AcceptanceDeliveryBills record);

    List<AcceptanceDeliveryBills> selectAll();

    /**
     * @param  iscomplete 是否完成状态
     * @description 根据完成状态获取本地出库单
     * */
    List<AcceptanceDeliveryBills> getbillsfromLocalByIsComplete(String iscomplete);

    /**
     * @description:废弃卸油登记单and明细
     * @param billno 出库单号
     * */
    int deleteOdandOdinfos(String billno);

    /**
     * @description 根据主键获取无货单验收表
    * */
    AcceptanceNoBills getNobillBykey(String no);


    /**
     * @description 根据油品编码获取无货单的验收登记表
     * */
    List<AcceptanceOdRegister> getNobillOds(String OilNo);


    /**
     * 保存无货单记录
     * */
    int insertNobills(AcceptanceNoBills noBill);


}
