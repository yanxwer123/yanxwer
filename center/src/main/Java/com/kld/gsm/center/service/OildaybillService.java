package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.oss_daily_oildaybill;

import java.util.List;

/**
 * Created by xhz on 2015/11/18
 *  泵码日结表.
 */
public interface OildaybillService {
    int AddOildaybill(List<oss_daily_oildaybill> oss_daily_oildaybills);
}
