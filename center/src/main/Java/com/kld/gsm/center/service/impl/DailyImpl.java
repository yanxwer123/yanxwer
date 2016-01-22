package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.oss_daily_remoteinventoryMapper;
import com.kld.gsm.center.dao.oss_monitor_oilgunMapper;
import com.kld.gsm.center.dao.oss_monitor_tankoilMapper;
import com.kld.gsm.center.domain.GaTContrast;
import com.kld.gsm.center.domain.*;
import com.kld.gsm.center.domain.hn.HNMonitor_Oilgun;
import com.kld.gsm.center.domain.hn.HNMonitor_Tankoil;
import com.kld.gsm.center.domain.hn.JTGC;
import com.kld.gsm.center.domain.oss_alarm_GaTContrast;
import com.kld.gsm.center.domain.oss_daily_remoteinventory;
import com.kld.gsm.center.service.Daily;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2015/12/9.
 */
@Service("Daily")
public class DailyImpl implements Daily {
    @Resource
    private oss_daily_remoteinventoryMapper ossDailyRemoteinventoryMapper;
    @Resource
    private oss_monitor_tankoilMapper ossMonitorTankoilMapper;
    @Resource
    private oss_monitor_oilgunMapper ossMonitorOilgunMapper;

    @Override
    public ResultMsg selectRemoteInfo(Integer page, Integer rows, String begin, String end, String oucode) {
        if (page != null && rows != null) {
            page = page < 1 ? 1 : page;
            int firstRow = (page - 1) * rows;
            HashMap hashMap = new HashMap();
            hashMap.put("firstRow", firstRow);
            hashMap.put("pageSize", rows);
            hashMap.put("begin",begin);
            Date date=new Date();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");


            try {
                date=fmt.parse(end);
                Calendar   calendar   =   new   GregorianCalendar();
                calendar.setTime(date);
                calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
                end=fmt.format(calendar.getTime());

            }
            catch (Exception e)
            {
                end=end;
                //date=fmt.format(date);
            }
            hashMap.put("end",end);
            hashMap.put("oucode", oucode + "%");
            List<oss_daily_remoteinventory> list = ossDailyRemoteinventoryMapper.selectRemoteInfo(hashMap);
            ResultMsg resultJson=new ResultMsg();
            resultJson.setResult(true);
            resultJson.setRows(list);
            HashMap map = new HashMap();
            map.put("begin", begin);
            map.put("end", end);
            map.put("oucode", oucode + "%");
            resultJson.setTotal(ossDailyRemoteinventoryMapper.selectRemoteAllInfo(map).size());
            resultJson.setData(list);
            return resultJson;
        }
        return null;
    }

    @Override
    public ResultMsg selectRemoteAllInfo(String begin, String end, String oucode) {
        HashMap hashMap = new HashMap();
        hashMap.put("begin", begin);
        hashMap.put("end", end);
        hashMap.put("oucode", oucode + "%");
        List<oss_daily_remoteinventory> list = ossDailyRemoteinventoryMapper.selectRemoteInfo(hashMap);
        ResultMsg resultJson=new ResultMsg();
        resultJson.setResult(true);
        resultJson.setRows(list);
        resultJson.setTotal(list.size());
        resultJson.setData(list);
        return resultJson;
    }

    @Override
    public String selectOilName(String oilcan, String oucode) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("oilcan", oilcan);
        map.put("oucode",oucode);
        return ossDailyRemoteinventoryMapper.selectOilName(map);
    }

    @Override
    public List<oss_monitor_tankoil> addJTGC(List<JTGC> jtgcs,String nodeno) {
        List<oss_monitor_tankoil> tanks=new ArrayList<oss_monitor_tankoil>();
        for (JTGC item:jtgcs){
            HNMonitor_Tankoil tankoilhn;
            tankoilhn=item.getTankoil();
            List<HNMonitor_Oilgun> oilguns=item.getOilgunLst();
            oss_monitor_tankoil tankoil=new oss_monitor_tankoil();
            tankoil.setTranstatus("0");
            tankoil.setNodeno(nodeno);
            tankoil.setHeighttotal(tankoilhn.getHeighttotal());
            tankoil.setMeasuretime(tankoilhn.getMeasuretime());
            tankoil.setOilcan(tankoilhn.getOilcan());
            tankoil.setStandardl(tankoilhn.getStandardl());
            tankoil.setTemperature(tankoilhn.getTemperature());
            tankoil.setOill(tankoilhn.getOill());
            tankoil.setWaterheight(tankoilhn.getWaterheight());
            tankoil.setWaterl(tankoilhn.getWaterl());
            tankoil.setId(tankoilhn.getId());
            ossMonitorTankoilMapper.merge(tankoil);
            for (HNMonitor_Oilgun item2:oilguns){
                oss_monitor_oilgun oilgun=new oss_monitor_oilgun();
                oilgun.setId(item2.getId());
                oilgun.setGunno(item2.getGunno());
                oilgun.setNodeno(nodeno);
                oilgun.setPumpup(item2.getPumpup());
                oilgun.setOilcan(item2.getOilcan());
                ossMonitorOilgunMapper.merge(oilgun);
            }
            tanks.add(tankoil);
        }
        return tanks;
    }

    @Override
    public int Updatetankoil(List<oss_monitor_tankoil> records) {
        for (oss_monitor_tankoil item:records){
            ossMonitorTankoilMapper.merge(item);
        }
        return 1;
    }
}
