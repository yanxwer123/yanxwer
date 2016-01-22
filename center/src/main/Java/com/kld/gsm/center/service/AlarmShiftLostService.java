package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.oss_alarm_ShiftLost;

import javax.servlet.ServletOutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xhz on 2015/11/17.
 * 交接班损溢预警
 */
public interface AlarmShiftLostService {
    int AddAlarmShiftLost(List<oss_alarm_ShiftLost> alarmShiftLost);
    List<HashMap<String,Object>> selectshiftlostbywhere(String start,String end,String oucode);

    List<HashMap<String,Object>>  querypage(Integer intPage, Integer intPageSize,String oucode,String starttime,String endtime);

    List<HashMap<String,Object>> selectAlarmCount(String start,String oucode);
    public void ExportShiftLost(List<HashMap<String,Object>> list,String [] titles,ServletOutputStream outputStream);
}

