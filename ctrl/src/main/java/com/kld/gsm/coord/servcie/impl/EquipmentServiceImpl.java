package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.dao.AlarmEquipmentDao;
import com.kld.gsm.ATG.dao.SysManageCanInfoDao;
import com.kld.gsm.ATG.domain.AlarmEquipment;
import com.kld.gsm.ATG.domain.SysManageCanInfo;
import com.kld.gsm.ATGDevice.ATGManager;
import com.kld.gsm.ATGDevice.atg_failure_data_in_t;
import com.kld.gsm.ATGDevice.atg_failure_data_out_t;
import com.kld.gsm.coord.dao.OilCanInforDao;
import com.kld.gsm.coord.domain.OilCanInfor;
import com.kld.gsm.coord.servcie.IEquipmentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/26 11:29
 * @Description:
 */
@SuppressWarnings("all")
@Service
public class EquipmentServiceImpl implements IEquipmentService {

    Logger logger = Logger.getLogger(EquipmentServiceImpl.class);
    @Autowired
    AlarmEquipmentDao alarmEquipmentDao;
    @Autowired
    SysManageCanInfoDao sysManageCanInfodao;
    @Override
    public void EquipmentSave() throws Exception{
        logger.info("come into EquipmentSave()..");
        //获取所有罐号
        logger.info("oilCanInforDao.selectOilCanInfor()..");
        List<SysManageCanInfo> oilCanInforList = sysManageCanInfodao.selectAll();
//        Date date = new Date();
        Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) - 1);//查询当前时间的前1分钟。
        Date date = cal.getTime();
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        List<atg_failure_data_in_t> failureDataInTList = new ArrayList<atg_failure_data_in_t>();
        for (SysManageCanInfo o : oilCanInforList) {
            atg_failure_data_in_t failureDataInT = new atg_failure_data_in_t();
            failureDataInT.uOilCanNO = o.getOilcan();
            failureDataInT.strDataTime = sd.format(date);
//            failureDataInT.strDataTime = "20150101121212";//测试使用
            failureDataInT.uReqCount = 0;
            logger.info("failureDataInT.uOilCanNO:"+failureDataInT.uOilCanNO);
            logger.info("failureDataInT.strDataTime:"+failureDataInT.strDataTime);
            failureDataInTList.add(failureDataInT);
        }
        logger.info("List<atg_failure_data_in_t> failureDataInTList.size:"+failureDataInTList.size());
        //调用液位仪接口返回设备故障信息
        List<atg_failure_data_out_t> failureDataOutTList = ATGManager.getFailure(failureDataInTList);
        for(atg_failure_data_out_t a : failureDataOutTList){
            logger.info("a.strDeviceType"+a.strDeviceType);
            logger.info("a.strEquipBrand"+a.strEquipBrand);
            logger.info("a.strEquipCode"+a.strEquipCode);
            logger.info("a.strFailureType"+a.strFailureType);
            logger.info("a.strProbeModel"+a.strProbeModel);
            logger.info("a.uOilCanNO"+a.uOilCanNO);
        }
        for(SysManageCanInfo o : oilCanInforList) {//遍历所有的油罐号
            //根据油罐查询是否有报警结束时间
            List<AlarmEquipment> alarmEquipmentList = alarmEquipmentDao.selectEndTime(o.getOilcan());
            logger.info("List<atg_failure_data_out_t> failureDataOutTList:"+failureDataOutTList.size());
            logger.info("alarmEquipmentList.size():"+alarmEquipmentList.size());
            if (alarmEquipmentList!=null&&alarmEquipmentList.size()>0) {//如果报警结束时间不为空，则应该更新
                //如果报警时间不为空，并且液位仪返回的数据不存在当前的罐号则更新报警结束时间
                int count = 0;//计数器，液位仪返回的数据是否包含当前罐号
                for (atg_failure_data_out_t failureDataOutT : failureDataOutTList) {
                    if(failureDataOutT.uOilCanNO==o.getOilcan()) {
                        count ++;//如果包含当前罐号，则计数器加1
                        continue;
                    }
                }
                logger.info("计数器，液位仪返回的数据是否包含当前罐号count:"+count);
                if(count==0) {//如果计数器为0，则说明液位仪返回的数据没有该罐号，可以执行更新操作
                    AlarmEquipment alarmEquipment = new AlarmEquipment();
                    alarmEquipment.setOilcan(o.getOilcan());
                    alarmEquipment.setEndalarmtime(date);
                    logger.info("更新AlarmEquipment，参数:"+alarmEquipment);
                    alarmEquipmentDao.updateEndTime(alarmEquipment);//更新
                }
            }else {//如果报警结束时间为空，则应该新增
                for (atg_failure_data_out_t failureDataOutT : failureDataOutTList) {
                    if(failureDataOutT.uOilCanNO!=o.getOilcan()){
                        continue;
                    }
                    AlarmEquipment alarmEquipment = new AlarmEquipment();
                    atg_failure_data_out_t2alarmEquipment(failureDataOutT, alarmEquipment);
                    alarmEquipmentDao.insertSelective(alarmEquipment);//插入
                }
            }
        }
    }

    public void atg_failure_data_out_t2alarmEquipment(atg_failure_data_out_t failureDataOutT,AlarmEquipment alarmEquipment)throws Exception{
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        alarmEquipment.setOilcan(failureDataOutT.uOilCanNO);//();油罐编号
        alarmEquipment.setStartalarmtime(sd.parse(failureDataOutT.strDate + failureDataOutT.strTime));//();开始报警时间
        //alarmEquipment.setEndAlarmTime();//();结束报警时间
        alarmEquipment.setFailuretype(failureDataOutT.strFailureType);//();故障类型
        alarmEquipment.setEquipcode(failureDataOutT.strEquipCode);//();设备代码
        alarmEquipment.setMalfunctioncode(failureDataOutT.strFailCode);//();故障信息代码
        alarmEquipment.setEquipbrand(failureDataOutT.strEquipBrand);//();设备品牌
        alarmEquipment.setProbemodel(failureDataOutT.strProbeModel);//();探棒型号
        alarmEquipment.setTranstatus("0");//();传输状态
        //alarmEquipment.setShift("123");//	班次号
    }
//    public static void main(String[] args){
//        Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
//        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) - 10);//查询当前时间的前十分钟。
//        Date date = cal.getTime();
//        //System.out.println(date);
//    }
}
