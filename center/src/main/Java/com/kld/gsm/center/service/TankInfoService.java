package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.oss_sysmanage_TankInfo;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2016-01-04 17:56
 * @Description:
 */
public interface TankInfoService {
    /**
     *
     * @return 返回所有的油罐
     */
    List<oss_sysmanage_TankInfo> findAll();

    /**
     *
     * @param oilno 油品编号
     * @return 油品名称
     */
    String findOilName(String oilno);
}
