package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.oss_alarm_measureLeak;

import javax.servlet.ServletOutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 测漏表
 */
public interface AlarmMeasureLeakService {
    int AddMeasureLeak(List<oss_alarm_measureLeak> oss_alarm_measureLeaks);
    List<HashMap<String,Object>> selectmeasurebywhere(String start,String end,String oucode);

    List<HashMap<String,Object>>  querypage(Integer intPage, Integer intPageSize,String oucode,String starttime,String endtime);
    public void ExportMeasureLeak(List<HashMap<String,Object>> list,String [] titles,ServletOutputStream outputStream);
}
