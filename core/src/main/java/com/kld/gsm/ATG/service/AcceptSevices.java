package com.kld.gsm.ATG.service;

import com.kld.gsm.ATG.domain.*;

import java.util.List;

/*
Created BY niyang
Created Date 2015/11/22
*/
public interface AcceptSevices {
  /*
  * @description 根据出库单号从中心获取实际发油量
  * @param  host 中心地址 ckdid  出库单号\
  * */
 SysmanageRealgiveoil getsjfyl(String host,String ckdid);

 List<AcceptanceDeliveryBills>getUncompletebills();

 SysmanageRealgiveoil getbydeliveryno(String deliveryno);


 /**
  * @description 根据单号从中心获取出货单
  * @param  host 中心地址 delno  出库单号
  * */
 AcceptanceDeliveryBills getDebillByNo(String host,String delno);

 /**
  * @description 传送卸油状态
 * */
 int transodRegStatus(String host,OdregStatus odregStatus);

 int noticeCenterDelbillno(String host,String billno,String nodeno);


/**
 * @description 从中心获取出货单
 * @param  host 中心地址 nodeno 站级系统编码
 * */
  int getbillsfromcenter(String host,String nodeno);
 /**
  * 根据出库单号，获取本地出库单
  * */
 AcceptanceDeliveryBills getbillfromlocal(String deliveryno);


 int sendOdreg(String host,String NodeNo);

 List<AcceptanceOdRegisterInfo> selectAcceptanceOdRegisterInfo(String DeliveryNo);

 List<AcceptanceOdRegisterInfo> selectUncompleteInfo();

}
