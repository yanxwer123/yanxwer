package com.kld.gsm.ATG.service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2015/12/13 16:18
 * @Description:
 */
public interface ISysManageOilType {
    List<com.kld.gsm.ATG.domain.SysManageOilType> selectByInUse();
}
