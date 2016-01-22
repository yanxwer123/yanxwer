package com.kld.gsm.coord.servcie;


import com.kld.gsm.ATG.domain.AcceptanceOdRegisterInfo;
import com.kld.gsm.coord.domain.OilCanIndeTail;

/**
 * Created by chen on 2015/11/6.
 */
public interface OilcanindetailService {

    int insertOilcanindetail(OilCanIndeTail oilCanIndeTail );
    int updateOilcanindetail(String DeliveryNo);
}
