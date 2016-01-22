package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATGDevice.*;
import com.kld.gsm.coord.servcie.ILiquidService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/23 14:39
 * @Description:
 */
public class LiquidServiceImpl implements ILiquidService {
    @Override
    public String startLiquid(List<atg_startliquid_data_in_t> data) {
        return ATGManager.startLiquid(data);
    }

    @Override
    public List<atg_liquidreport_data_out_t> stopLiquid(List<atg_stopliquid_data_in_t> data) {
        return ATGManager.stopLiquid(data);
    }

    @Override
    public List<atg_liquidreport_data_out_t> liquidReport(List<atg_liquidreport_data_in_t> data) {
        return ATGManager.liquidReport(data);
    }
}
