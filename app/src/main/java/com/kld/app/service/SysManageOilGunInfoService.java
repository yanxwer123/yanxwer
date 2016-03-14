package com.kld.app.service;

import com.kld.gsm.ATG.domain.SysManageOilGunInfo;


import java.util.List;

/**
 * Created by ken on 2016/2/3.
 */
public interface SysManageOilGunInfoService {
    List<SysManageOilGunInfo> findByOilCanNo(String canno);}
