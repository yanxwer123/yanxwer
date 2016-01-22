package com.kld.app.service;


import com.kld.gsm.ATG.domain.SysManageIquidInstrument;

/**
 * Created by 1 on 2015/10/26.
 */
public interface IIquidInstrumentService {
    int insertSelective(SysManageIquidInstrument record);
    String getLastAdjustTime();
}
