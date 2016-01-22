package com.kld.gsm.coord.servcie;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2015/11/12 10:42
 * @Description:油品进货验收
 */
public interface OilPurchaseAcceptanceService {

    int selectAndInsert(String DeliveryNo,int oprno,String id);
    int update(String DeliveryNo,int Oilcan,String id);
    int updateNo(String DeliveryNo,String manualno);
}
