package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.oss_sysmanage_AlarmParameterMapper;
import com.kld.gsm.center.dao.oss_sysmanage_OilGunInfoMapper;
import com.kld.gsm.center.domain.PreAlarm;
import com.kld.gsm.center.domain.oss_sysmanage_AlarmParameter;
import com.kld.gsm.center.service.AlarmParmeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
@Service
public class AlarmParmeterServiceImpl implements AlarmParmeterService {
    @Autowired
    private oss_sysmanage_OilGunInfoMapper oss_sysmanage_oilGunInfoMapper;
    @Autowired
    private oss_sysmanage_AlarmParameterMapper sysmanage_alarmParameterMapper;

    @Override
    public List<PreAlarm> findByOUCode(String oucode) {

        List<PreAlarm> gasGunManages = new ArrayList<PreAlarm>();
        PreAlarm preAlarm;
        List<oss_sysmanage_AlarmParameter> oss_sysmanage_alarmParameters = sysmanage_alarmParameterMapper.findByOUCode(oucode);
        for (oss_sysmanage_AlarmParameter alarm : oss_sysmanage_alarmParameters) {
            //赋值并查询出油品类型
            String oilcan=String.valueOf(alarm.getOilcan());
            String nodeno=alarm.getNodeno();
            HashMap selectMap = new HashMap();
            selectMap.put("oilcan", oilcan);
            selectMap.put("nodeno", nodeno);
            String oiltype = oss_sysmanage_oilGunInfoMapper.findoiltype(selectMap);
            preAlarm = new PreAlarm();
            preAlarm.setOilcan(alarm.getOilcan());
            preAlarm.setOiltype(oiltype == null ? "未匹配" : oiltype);
            preAlarm.setLowprealarm(alarm.getLowprealarm());// 低液位预警
            preAlarm.setLowalarm(alarm.getLowalarm()); //低液位报警
            preAlarm.setHighprealarm(alarm.getHighprealarm()); //高液位预警
            preAlarm.setHighalarm(alarm.getHighalarm()); //高液位报警
            preAlarm.setWateralarm(alarm.getWateralarm()); //水杂报警
            preAlarm.setHightempalarm(alarm.getHightempalarm());//高温报警
            preAlarm.setLowtempalarm(alarm.getLowtempalarm()); //低温报警
            preAlarm.setLastoptime(alarm.getLastoptime());//上次设置时间
            preAlarm.setOptime(alarm.getOptime()); //操作时间
            preAlarm.setTranstatus(alarm.getTranstatus());// 传输状态
            preAlarm.setOucode(alarm.getOucode());
            //高水位报警 不与数据库交互
            preAlarm.setWaterhightalarm(alarm.getWaterhightalarm());
            //盗油报警 不与数据库交互
            preAlarm.setStealoilalarm(alarm.getStealoilalarm());
            //渗油报警 不与数据库交互
            preAlarm.setLeakoilalarm(alarm.getLeakoilalarm());
            //漏油报警 不与数据库交互
            preAlarm.setLeakageoilalarm(alarm.getLeakageoilalarm());
            gasGunManages.add(preAlarm);
        }


        return gasGunManages;
    }


    @Override
    public List<PreAlarm> findByOUCodePage(Map map) {
        int pageNo = Integer.parseInt(String.valueOf(map.get("intPage")));
        int pageSize = Integer.parseInt(String.valueOf(map.get("intPageSize")));

        pageNo = pageNo < 1 ? 1 : pageNo;
        int firstRow = (pageNo - 1) * pageSize;
        HashMap hashMap = new HashMap();
        hashMap.put("firstRow", firstRow);
        hashMap.put("pageSize", pageSize);
        hashMap.put("id", map.get("id"));

        List<PreAlarm> gasGunManages = new ArrayList<PreAlarm>();
        PreAlarm preAlarm;
        List<oss_sysmanage_AlarmParameter> oss_sysmanage_alarmParameters = sysmanage_alarmParameterMapper.findByOUCodePage(hashMap);
        for (oss_sysmanage_AlarmParameter alarm : oss_sysmanage_alarmParameters) {
            String oilcan=String.valueOf(alarm.getOilcan());
            String nodeno=alarm.getNodeno();
            HashMap selectMap = new HashMap();
            selectMap.put("oilcan", oilcan);
            selectMap.put("nodeno", nodeno);
            //赋值并查询出油品类型
            String oiltype = oss_sysmanage_oilGunInfoMapper.findoiltype(selectMap);
            preAlarm = new PreAlarm();
            preAlarm.setOilcan(alarm.getOilcan());
            preAlarm.setOiltype(oiltype == null ? "未匹配" : oiltype);
            preAlarm.setLowprealarm(alarm.getLowprealarm());// 低液位预警
            preAlarm.setLowalarm(alarm.getLowalarm()); //低液位报警
            preAlarm.setHighprealarm(alarm.getHighprealarm()); //高液位预警
            preAlarm.setHighalarm(alarm.getHighalarm()); //高液位报警
            preAlarm.setWateralarm(alarm.getWateralarm()); //水杂报警
            preAlarm.setHightempalarm(alarm.getHightempalarm());//高温报警
            preAlarm.setLowtempalarm(alarm.getLowtempalarm()); //低温报警
            preAlarm.setLastoptime(alarm.getLastoptime());//上次设置时间
            preAlarm.setOptime(alarm.getOptime()); //操作时间
            preAlarm.setTranstatus(alarm.getTranstatus());// 传输状态
            preAlarm.setOucode(alarm.getOucode());
            //高水位报警 不与数据库交互
            preAlarm.setWaterhightalarm(alarm.getWaterhightalarm());
            //盗油报警 不与数据库交互
            preAlarm.setStealoilalarm(alarm.getStealoilalarm());
            //渗油报警 不与数据库交互
            preAlarm.setLeakoilalarm(alarm.getLeakoilalarm());
            //漏油报警 不与数据库交互
            preAlarm.setLeakageoilalarm(alarm.getLeakageoilalarm());
            gasGunManages.add(preAlarm);
        }


        return gasGunManages;
    }

    @Override
    public int insert(oss_sysmanage_AlarmParameter oss_sysmanage_alarmParameter) {
        return  sysmanage_alarmParameterMapper.insert(oss_sysmanage_alarmParameter);
    }
}
