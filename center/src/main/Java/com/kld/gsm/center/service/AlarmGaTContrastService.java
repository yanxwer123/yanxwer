package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.oss_alarm_GaTContrast;

import javax.servlet.ServletOutputStream;
import java.util.HashMap;
import java.util.List;


/**
 * Created by xhz on 2015/11/18.
 * 枪出罐出对比
 */
public interface AlarmGaTContrastService {
    int AddGaTContrastService(List<oss_alarm_GaTContrast> oss_alarm_gaTContrastList);
    List<HashMap<String,Object>> selectGatInfo(Integer page, Integer rows,String begin,String end,String oucode,String result);
    List<HashMap<String,Object>> selectGatAllInfo(String begin,String end,String oucode,String result);
    int selectCountAllInfo(String begin,String end,String oucode,String result);
    public void ExportGatContrast(List<HashMap<String,Object>> list,String [] titles,ServletOutputStream outputStream);
}
