package com.kld.app.service;

import com.kld.gsm.ATG.domain.SysManageCanInfo;

import java.util.List;

/**
 * Created by ken on 2016/2/2.
 */
public interface YgCheckSerivce {
    //查看油罐信息
    List<SysManageCanInfo> findOilCan();
}
