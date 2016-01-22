package com.kld.app.service;

import com.kld.gsm.ATG.domain.SysManageCanInfo;
import com.kld.gsm.ATG.domain.SysManageOilGunInfo;

import java.util.List;

/**
 * Created by fangzhun on 2015/11/26.
 */
public interface SysManageCanInfoService {
    List<SysManageCanInfo> selectAll();

    /**
     * @description 根据油品编码获取，相应的罐 ny
     */
    List<SysManageCanInfo> selectByOilNo(String oilNo);

    /**
     * @decription 根据罐号 获取油枪列表
     */
    List<SysManageOilGunInfo> selectGunsByCanNo(String CanNo);

    /**
     * @param oilno 油品
     * @return 油罐号
     */
    List<String>  findByOilcan(String oilno);

    SysManageCanInfo selectbycanno(Integer canno);


}
