package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.oss_sysmanage_department;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2016/1/14 13:57
 * @Description:
 */
public interface SysDepartmentService {
    /**
     *
     * @param oucode 单位编码
     * @return 单位信息
     */
    oss_sysmanage_department findByOUCode(String oucode);
}
