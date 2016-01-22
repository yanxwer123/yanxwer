package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.oss_alarm_DailyLost;

import javax.servlet.ServletOutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 日结损溢预警表
 */
public interface AlarmDailyLostService {
    int AddDailyLost(List<oss_alarm_DailyLost> alarmDailyLostList);
    List<HashMap<String,Object>> selectDailyLost(HashMap<String,Object> map);
    List<HashMap<String,Object>> selectPageDailyLost(Integer page, Integer rows,String begin,String end,String oucode);
    public void ExportDailyLost(List<HashMap<String,Object>> list,String [] titles,ServletOutputStream outputStream);
}
