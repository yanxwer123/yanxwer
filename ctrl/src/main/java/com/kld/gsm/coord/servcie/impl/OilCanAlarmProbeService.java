package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.dao.AlarmInventoryDao;
import com.kld.gsm.ATG.domain.AlarmInventory;
import com.kld.gsm.ATGDevice.ATGManager;
import com.kld.gsm.ATGDevice.atg_alarm_data_in_t;
import com.kld.gsm.ATGDevice.atg_alarm_data_out_t;
import com.kld.gsm.coord.dao.OilCanInforDao;
import com.kld.gsm.coord.domain.OilCanInfor;
import com.kld.gsm.coord.servcie.IOilCanAlarmProbeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/26 22:22
 * @Description:
 */
@Service
public class OilCanAlarmProbeService implements IOilCanAlarmProbeService {
    @Autowired
    OilCanInforDao oilCanInforDao;
    @Autowired
    AlarmInventoryDao alarmInventoryDao;
    @Override
    public void OilCanAlarmProbeSave() throws Exception {
        List<OilCanInfor> oilCanInforList = oilCanInforDao.selectOilCanInfor();
        List<atg_alarm_data_in_t> alarmData = new ArrayList<atg_alarm_data_in_t>();
        Date date = new Date();
        SimpleDateFormat sd = new SimpleDateFormat();
        for(OilCanInfor o : oilCanInforList){
            atg_alarm_data_in_t alarmDataInT = new atg_alarm_data_in_t();
            alarmDataInT.uOilCanNO=o.getOilcanno();
            alarmDataInT.strDataTime=sd.format(date);
            alarmDataInT.uReqCount=0;
            alarmData.add(alarmDataInT);
        }
        List<atg_alarm_data_out_t> alarmDataOutTList = ATGManager.getAlarm(alarmData);
        //报警类型
        //        2001	高液位报警
        //        2002	高液位预警
        //        2003	低液位预警
        //        2004	低液位报警
        //        2005	高水位预警
        //        2006	高水位报警
        //        2007	高温报警
        //        2008	低温报警
        //        2009	静态液位异常报警
        //报警状态
        //        0	报警开始
        //        1	报警结束
        for(atg_alarm_data_out_t alarmDataOutT : alarmDataOutTList){
            AlarmInventory alarmInventory = new AlarmInventory();
            alarmInventory.setOilcan(alarmDataOutT.uOilCanNO);
            alarmInventory.setAlarmtype(Integer.parseInt(alarmDataOutT.strAlarmType));//报警类型
            if("0".equals(alarmDataOutT.strAlarmState)){//报警开始，新增数据
                alarmInventory.setAlarmdesc(alarmDataOutT.strRemark);
                alarmInventory.setStarttime(sd.parse(alarmDataOutT.strDate + alarmDataOutT.strTime));
                alarmInventoryDao.insertSelective(alarmInventory);
            }else if("1".equals(alarmDataOutT.strAlarmState)){//报警结束，以罐号和报警类型和报警结束时间为空为条件，更新数据
                alarmInventory.setEndtime(sd.parse(alarmDataOutT.strDate + alarmDataOutT.strTime));
                alarmInventoryDao.updateEndTime(alarmInventory);
            }
        }



    }
}
