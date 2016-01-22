package com.kld.app.service;

import com.kld.gsm.ATG.domain.SysManageCanInfo;

import java.util.List;

/**
 * Created by fangzhun on 2015/11/26.
 */
public interface SysManageTankInfoService {
    List<SysManageCanInfo>  selectAll();
}
