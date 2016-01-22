package com.kld.gsm.coord.servcie;


import com.kld.gsm.ATGDevice.atg_stock_data_out_t;

import java.util.List;

/**
 * Created by yinzhiguang on 2015/11/8.
 */
public interface ISaleOutAlarmService {
    void saleOutAlarmSave(List<atg_stock_data_out_t> outList) throws Exception;
    List<Integer> selectAllOilCanNos();
}
