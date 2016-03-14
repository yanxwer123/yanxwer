package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.GasGunManage;
import com.kld.gsm.center.domain.oss_sysmanage_OilGunInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2016/1/14 17:52
 * @Description:
 */
public interface OilGunInfoService {
    List<GasGunManage> findByOUCode(String oucode);
    List<GasGunManage> findByOUCodePage(Map map);
}
