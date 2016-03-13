package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.oss_daily_StationShiftInfo;

import javax.servlet.ServletOutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/11/18.
 * 油站班报表
 */
public interface DStationShiftInfoService {
    int AddStationShiftInfo(List<oss_daily_StationShiftInfo> oss_daily_stationShiftInfos);
    List<HashMap<String, Object>> getShiftList(String begin, String end, String oucode);
    List<HashMap<String,Object>> selectPageShiftInfo(Integer page, Integer rows,String begin,String end,String oucode);
    public void YZBB(List<HashMap<String,Object>> list,String [] titles,ServletOutputStream outputStream);
}
