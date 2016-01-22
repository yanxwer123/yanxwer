package com.kld.gsm.coord.servcie;

import com.kld.gsm.ATG.domain.AlarmGaTContrast;
import com.kld.gsm.ATGDevice.atg_stock_data_out_t;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/23 16:30
 * @Description:油机状态数据
 */
public interface IOilMacStautsDataService {
    /**
     * 查询所有的油罐编号
     * @return
     */
    List<Integer> selectAllOilCanNos();

    void OilMacStautsDataSave(List<atg_stock_data_out_t> stockDataOutList,List<AlarmGaTContrast> alarmGaTContrastList) throws Exception;

    List<Integer> selectOilGunByOilCanNo(int oilCanNo);
}
