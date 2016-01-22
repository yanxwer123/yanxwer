package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.dao.AlarmEquipmentDao;
import com.kld.gsm.ATG.dao.SysManageCanInfoDao;
import com.kld.gsm.ATG.domain.AlarmEquipment;
import com.kld.gsm.ATG.domain.SysManageCanInfo;
import com.kld.gsm.ATGDevice.ATGManager;
import com.kld.gsm.ATGDevice.atg_failure_data_in_t;
import com.kld.gsm.ATGDevice.atg_failure_data_out_t;
import com.kld.gsm.coord.servcie.IEquipmentService;
import com.kld.gsm.coord.timertask.TimeTaskPar;
import com.kld.gsm.coord.utils.ComparatorUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

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
public class EquipmentNewServiceImpl implements IEquipmentService {

    Logger logger = Logger.getLogger(EquipmentNewServiceImpl.class);
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
        //查询当前时间的前TimeTaskPar.get("sbgzjgsj")*1000分钟。
        //cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) - 1);
        Date date = cal.getTime();
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sd2 = new SimpleDateFormat("yyyyMMdd000000");
        logger.info("测试时间：" + sd2.format(date));
        List<atg_failure_data_in_t> failureDataInTList = new ArrayList<atg_failure_data_in_t>();
        logger.info("开始循环oilCanInforList，长度是："+oilCanInforList.size());
        for (SysManageCanInfo o : oilCanInforList) {
            try {
                atg_failure_data_in_t failureDataInT = new atg_failure_data_in_t();
                failureDataInT.uOilCanNO = o.getOilcan();
                String MaxTime = alarmEquipmentDao.selectMaxTime(failureDataInT.uOilCanNO);
                if (null == MaxTime || "".equals(MaxTime)) {
                    failureDataInT.strDataTime = sd2.format(date);
                } else {
                    failureDataInT.strDataTime = sd.format(sd1.parse(MaxTime));
                }
                failureDataInT.uReqCount = 0;
                logger.info("failureDataInT.uOilCanNO:" + failureDataInT.uOilCanNO);
                logger.info("failureDataInT.strDataTime:" + failureDataInT.strDataTime);
                failureDataInTList.add(failureDataInT);
            }catch (Exception e){
                logger.error("循环oilCanInforList:"+e.getMessage());
            }
        }
        logger.info("List<atg_failure_data_in_t> failureDataInTList.size:"+failureDataInTList.size());
        //调用液位仪接口返回设备故障信息
        List<atg_failure_data_out_t> failureDataOutTList = ATGManager.getFailure(failureDataInTList);
        logger.info("get failurelist size() "+failureDataOutTList.size());
        //根据date、time排序，小的时间在前，之后的更新操作不用判断时间
        ComparatorUtils comparator=new ComparatorUtils();
        Collections.sort(failureDataOutTList, comparator);
        logger.info("failureDataOutTList.size（）："+failureDataOutTList.size());
        for(atg_failure_data_out_t failureDataOutT : failureDataOutTList){
            //1：结束报警，0：报警开始
            if("1".equals(failureDataOutT.strAlarmState)){//更新结束时间
                AlarmEquipment alarmEquipment = new AlarmEquipment();
                alarmEquipment.setOilcan(failureDataOutT.uOilCanNO);
                alarmEquipment.setEndalarmtime(sd.parse(failureDataOutT.strDate + failureDataOutT.strTime));
                alarmEquipment.setFailuretype(failureDataOutT.strFailureType);
                logger.info("failureDataOutT，参数:" + failureDataOutT.toString2());
                logger.info("更新AlarmEquipment，参数:" + alarmEquipment);
                alarmEquipmentDao.updateEndTime(alarmEquipment);//更新
            }else if("0".equals(failureDataOutT.strAlarmState)){//插入新的故障记录
                logger.info("插入新的故障记录...");
                logger.info("新的故障记录failureDataOutT，参数:" + failureDataOutT.toString2());
                AlarmEquipment alarmEquipment = new AlarmEquipment();
                atg_failure_data_out_t2alarmEquipment(failureDataOutT, alarmEquipment);
                logger.info("待插入AlarmEquipment，参数:" + alarmEquipment);
                String failureType = alarmEquipmentDao.isExisit(alarmEquipment);
                logger.info("查询的失败类型failureType:"+failureType+"~~~");
                //如果已经存在（相同的罐号、开始时间、故障类型类型、结束时间为空），则跳过
                if(!"".equals(alarmEquipment.getFailuretype())&&alarmEquipment.getFailuretype().equals(failureType)){
                    logger.warn("已经存在（相同的罐号、开始时间、故障类型类型、结束时间为空）,跳过");
                    continue;
                }
                logger.info("开始插入设备故障");
                alarmEquipmentDao.insertSelective(alarmEquipment);//插入
                logger.info("结束插入设备故障");
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
