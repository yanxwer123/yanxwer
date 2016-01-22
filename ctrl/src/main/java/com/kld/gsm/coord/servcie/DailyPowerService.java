package com.kld.gsm.coord.servcie;

import com.kld.gsm.ATG.domain.DailyPowerRecord;
import com.kld.gsm.ATG.domain.DailyPowerRecordKey;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="yanxwer@163.com">yanxiaowei</a>
 * @version 1.0
 * @CreationTime: 2015/11/24 15:17
 * @Description:液位仪开关记录
 */
public interface DailyPowerService {
    DailyPowerRecord selectByPrimaryKey(DailyPowerRecordKey key);
    int insertSelective(DailyPowerRecord record);
}
