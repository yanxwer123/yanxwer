package com.kld.gsm.coord.servcie;

import com.kld.gsm.ATGDevice.atg_liquidreport_data_in_t;
import com.kld.gsm.ATGDevice.atg_liquidreport_data_out_t;
import com.kld.gsm.ATGDevice.atg_startliquid_data_in_t;
import com.kld.gsm.ATGDevice.atg_stopliquid_data_in_t;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/23 13:43
 * @Description:
 */
public interface ILiquidService {
    /**
     * 启动静态液位异常测试
     *
     * @param data
     * @return
     */
    String startLiquid(List<atg_startliquid_data_in_t> data);
    /**
     * 停止静态液位异常测试
     *
     * @param data
     * @return
     */
    List<atg_liquidreport_data_out_t> stopLiquid(List<atg_stopliquid_data_in_t> data);
    /**
     * 静态液位异常测试报告
     * @param data
     * @return
     */
    List<atg_liquidreport_data_out_t> liquidReport(List<atg_liquidreport_data_in_t> data);
}
