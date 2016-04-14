package com.kld.gsm.syntocenter.service.impl;

import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.syntocenter.service.synAlarm;
import com.kld.gsm.syntocenter.util.Station;
import com.kld.gsm.syntocenter.util.action;
import com.kld.gsm.syntocenter.util.httpClient;
import com.kld.gsm.syntocenter.util.param;
import com.kld.gsm.util.JsonMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
Created BY xhz
Created Date 2015/11/18
*/
@SuppressWarnings("all")
@Service
public class synAlarmImpl implements synAlarm {
    private static final Logger LOG = Logger.getLogger("synAlarmImpl");
    @Autowired
    private com.kld.gsm.ATG.dao.AlarmDailyLostDao alarmDailyLostDao;

    @Override
    public int synDailyLost() {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.Alarm.AddDayLost");
        //获取站级数据
        List<AlarmDailyLost>  alarmDailyLosts= alarmDailyLostDao.selectByTrans("0");
        if (alarmDailyLosts==null||alarmDailyLosts.size()==0){
            return 0;
        }
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,alarmDailyLosts,hm);
            for (AlarmDailyLost item:alarmDailyLosts){
                item.setTranstatus("1");
                alarmDailyLostDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
    //交接班损溢预警
  @Autowired
    private com.kld.gsm.ATG.dao.AlarmShiftLostDao alarmShiftLostDao;
    @Override
    public int synShiftLost() {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.Alarm.AddShiftLost");
        //获取站级数据
        List<AlarmShiftLost>  alarmShiftLosts= alarmShiftLostDao.selectByTrans("0");
        if (alarmShiftLosts==null||alarmShiftLosts.size()==0){
            return 0;
        }
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        Result result;
        try {
            String js=client.request(path,alarmShiftLosts,hm);
            result=new JsonMapper().fromJson(js,Result.class);
            if (result!=null&&result.isResult()) {
                for (AlarmShiftLost item : alarmShiftLosts) {
                    item.setTranstatus("1");
                    alarmShiftLostDao.updateByPrimaryKey(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
    //库存预警
    @Autowired
    private com.kld.gsm.ATG.dao.AlarmInventoryDao alarmInventoryDao;
    @Override
    public int synInventory() {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.Alarm.AddInventory");
        //获取站级数据
        List<AlarmInventory>  alarmInventory= alarmInventoryDao.selectByTrans("0");
        if(alarmInventory==null||alarmInventory.size()==0){
            return 0;
        }
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        Result result;
        try {
            String js=client.request(path, alarmInventory, hm);
            LOG.info(js);
            result=new JsonMapper().fromJson(js,Result.class);
            if(result!=null&&result.isResult()) {
                for (AlarmInventory item : alarmInventory) {
                    item.setTranstatus("1");
                    alarmInventoryDao.updateByPrimaryKey(item);
                }
            }
        } catch (Exception e) {
           LOG.error(e.getMessage());
            return 0;
        }
        return 1;
    }
    //枪出罐出对比
    @Autowired
    private com.kld.gsm.ATG.dao.AlarmGaTContrastDao alarmGaTContrastDao;
    @Override
    public int synGaTContrast() {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.Alarm.AddGaTContrast");
        //获取站级数据
        List<AlarmGaTContrast>  alarmInventory= alarmGaTContrastDao.selectByTrans("0");
        if (alarmInventory==null||alarmInventory.size()==0){
            return 0;
        }
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        Result result;
        try {
            String js=client.request(path,alarmInventory,hm);
            result=new JsonMapper().fromJson(js,Result.class);
            if (result!=null&&result.isResult()) {
                for (AlarmGaTContrast item : alarmInventory) {
                    item.setTranstatus("1");
                    alarmGaTContrastDao.updateByPrimaryKey(item);
                }
            }
        } catch (Exception e) {
           LOG.error("枪出罐出"+e.getMessage(),e);
            return 0;
        }
        return 1;
    }
    //测漏表
    @Autowired
    private com.kld.gsm.ATG.dao.AlarmMeasureLeakDao alarmMeasureLeakDao;
    @Override
    public int synMeasureLeak() {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.Alarm.AddMeasureLeak");
        //获取站级数据
        List<AlarmMeasureLeak>  alarmMeasureLeaks= alarmMeasureLeakDao.selectByTrans("0");
        if (alarmMeasureLeaks==null||alarmMeasureLeaks.size()==0){
            return 0;
        }
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,alarmMeasureLeaks,hm);
            for (AlarmMeasureLeak item:alarmMeasureLeaks){
                item.setTranstatus("1");
                alarmMeasureLeakDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
   //脱销预警
   @Autowired
   private com.kld.gsm.ATG.dao.AlarmSaleOutDao alarmSaleOutDao;
    @Override
    public int synSaleOut() {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.Alarm.AddSaleOut");
        //获取站级数据
        List<AlarmSaleOut>  alarmSaleOuts= alarmSaleOutDao.selectByTrans("0");
        if (alarmSaleOuts==null||alarmSaleOuts.size()==0){
            return 0;
        }
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        Result result;
        try {
            String js=client.request(path,alarmSaleOuts,hm);
            LOG.info(js);
            result=new JsonMapper().fromJson(js,Result.class);
            if (result!=null&&result.isResult()) {
                for (AlarmSaleOut item : alarmSaleOuts) {
                    item.setTranstatus("1");
                    alarmSaleOutDao.updateByPrimaryKey(item);
                }
            }
        } catch (Exception e) {
            LOG.error("脱销报警："+e.getMessage(),e);
            return 0;
        }
        return 1;
    }
    //设备故障表
    @Autowired
    private com.kld.gsm.ATG.dao.AlarmEquipmentDao alarmEquipmentDao;
    @Override
    public int synEquipment() {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.Alarm.AddEquipment");
        //获取站级数据
        List<AlarmEquipment>  alarmEquipments= alarmEquipmentDao.selectByTrans("0");

        if (alarmEquipments==null||alarmEquipments.size()==0){
            return 0;
        }
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        Result result;
        try {
            String js=client.request(path,alarmEquipments,hm);
            result=new JsonMapper().fromJson(js,Result.class);
            if (result!=null&&result.isResult()) {
                for (AlarmEquipment item : alarmEquipments) {
                    item.setTranstatus("1");
                    alarmEquipmentDao.updateByPrimaryKey(item);
                }
            }
        } catch (Exception e) {
            LOG.error("设备故障:"+e.getMessage(),e);
            return 0;
        }
        return 1;
    }
    //进油损耗对比表
    @Autowired
    private com.kld.gsm.ATG.dao.AlarmOilInContrastDao alarmOilInContrastDao;
    @Override
    public int synOilInContrast() {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.Alarm.AddOilInContrast");
        //获取站级数据
        List<AlarmOilInContrast>  alarmEquipments= alarmOilInContrastDao.selectByTrans("0");
        if(alarmEquipments==null||alarmEquipments.size()==0){
            return 0;
        }
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        Result result;
        try {
            String js=client.request(path,alarmEquipments,hm);
            result=new JsonMapper().fromJson(js,Result.class);
            if (result!=null&&result.isResult()) {
                for (AlarmOilInContrast item : alarmEquipments) {
                    item.setTranstatus("1");
                    alarmOilInContrastDao.updateByPrimaryKey(item);
                }
            }
        } catch (Exception e) {
            LOG.error("synOilInContrast:"+e.getMessage(),e);
            return 0;
        }
        return 1;
    }
}
