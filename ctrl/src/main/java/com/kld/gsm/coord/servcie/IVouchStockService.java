package com.kld.gsm.coord.servcie;

import com.kld.gsm.ATG.common.base.BaseService;
import com.kld.gsm.coord.domain.VouchStock;

/**
 * Created by yinzhiguang on 2015/11/5.
 */
public interface IVouchStockService extends BaseService<VouchStock,Long> {
    int insert(VouchStock vouchStock);
}
