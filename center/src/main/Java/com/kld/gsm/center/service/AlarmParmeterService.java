package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.PreAlarm;
import com.kld.gsm.center.domain.oss_sysmanage_AlarmParameter;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2016/1/15 15:52
 * @Description:
 */
public interface AlarmParmeterService {
    List<PreAlarm>  findByOUCode(String oucode);
    List<PreAlarm>  findByOUCodePage(Map map);
    int insert(oss_sysmanage_AlarmParameter oss_sysmanage_alarmParameter);

}
