package com.kld.gsm.ATG.service;


import com.kld.gsm.ATG.domain.AlarmInventory;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="yanxwer@163.com">yanxiaowei</a>
 * @version 1.0
 * @CreationTime: 2015/11/24 20:58
 * @Description:
 */
public interface OilCanAlarmService {
    int insert(AlarmInventory parameter);
    int updateByPrimaryKey(AlarmInventory record);
    AlarmInventory getByNo(int oilcan);
}
