package com.kld.app.service;

import com.kld.gsm.ATG.domain.SysManageCubage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/30 18:59
 * @Description:
 */
public interface ISetCapacityTableService {
    int updateByPrimaryKeySelective(SysManageCubage record);
}
