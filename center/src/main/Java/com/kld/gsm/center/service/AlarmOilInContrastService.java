package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.oss_alarm_OilInContrast;

import javax.servlet.ServletOutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 进油损耗对比表
 */
public interface AlarmOilInContrastService {
    int AddOilInContrast(List<oss_alarm_OilInContrast> oss_alarm_oilInContrasts );
    List<HashMap<String,Object>> selectoilincontrastbywhere(String start,String end,String oucode);

    List<HashMap<String,Object>>  querypage(Integer intPage, Integer intPageSize,String oucode,String starttime,String endtime);
    public void ExportOilInContrast(List<HashMap<String,Object>> list,String [] titles,ServletOutputStream outputStream);
}
