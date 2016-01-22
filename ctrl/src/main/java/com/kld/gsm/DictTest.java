package com.kld.gsm;

import com.kld.gsm.ATG.dao.AlarmShiftLostDao;
import com.kld.gsm.ATG.dao.SysManageDictDao;
import com.kld.gsm.ATG.domain.AlarmShiftLost;
import com.kld.gsm.ATG.domain.SysManageDict;
import com.kld.gsm.coord.Context;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-12-08 18:40
 * @Description:
 */
public class DictTest {
    public static void main(String[] args) {
       // String code = "rjsyycz";//日结损益率异常值
//
//        SysManageDictDao sysManageDictDao = Context.getInstance().getBean(SysManageDictDao.class);
//        SysManageDict sysManageDict = sysManageDictDao.selectByCode(code);
//        //System.out.println("----------------Value" + sysManageDict.getValue());
//        //插入到oss_alarm_dailylost日结损溢预警表
//        AlarmDailyLost alarmDailyLost = new AlarmDailyLost();
//        alarmDailyLost.setAccountdate(new Date());
//        alarmDailyLost.setOilno("123");
//        alarmDailyLost.setDarlyankstock(1.1);
//        alarmDailyLost.setDeliveryno("1");
//        alarmDailyLost.setReceivel(1.1);
//        alarmDailyLost.setTodayout(11.1);
//        alarmDailyLost.setTodayendstock(1.1);
//        alarmDailyLost.setRealstock(1.1);
//        alarmDailyLost.setCost(1.1);
//        alarmDailyLost.setCostsent(1.1);
//        alarmDailyLost.setCreatetime(new Date());
//        alarmDailyLost.setTranstatus("0");
//        AlarmDailyLostDao alarmDailyLostDao = Context.getInstance().getBean(AlarmDailyLostDao.class);
//        alarmDailyLostDao.insert(alarmDailyLost);
a();

    }

    public static void a() {
        SysManageDictDao sysManageDictDao = Context.getInstance().getBean(SysManageDictDao.class);

        String code = "bjsyycz";//班结损益率异常值
        SysManageDict sysManageDict = sysManageDictDao.selectByCode(code);
        //System.out.println("value :" +sysManageDict.getValue());
        //插入数据到交接班损益预警表中
        AlarmShiftLost alarmShiftLost = new AlarmShiftLost();
       // alarmShiftLost.setProfitLossRatio(1.1);
        alarmShiftLost.setShift("1113");
        alarmShiftLost.setOilcanno(13);
        alarmShiftLost.setStartoilheight(1.1);
        alarmShiftLost.setStartoill(1.1);
        alarmShiftLost.setEndoilheight(1.1);
        alarmShiftLost.setEndoill(1.1);
        alarmShiftLost.setAcutalendoill(1.1);
        alarmShiftLost.setEndwaterheight(1.1);
        alarmShiftLost.setEndwaterl(1.1);
        alarmShiftLost.setEndtemperature(null);//交班温度不赋值
        alarmShiftLost.setOildischarge(1.1);
        alarmShiftLost.setSale(1.1);
        alarmShiftLost.setInventory(1.1);
        alarmShiftLost.setLoss(1.1);
        alarmShiftLost.setState(null); //状态不赋值
        alarmShiftLost.setShifttime(new Date());
        alarmShiftLost.setTranstatus("1");
        alarmShiftLost.setProfitLossRatio(1.1);
        AlarmShiftLostDao alarmShiftLostDao = Context.getInstance().getBean(AlarmShiftLostDao.class);
        //System.out.println("----------------");
        //System.out.println("----------------");
        //System.out.println("----------------");
        //System.out.println("----------------");

        alarmShiftLostDao.insert(alarmShiftLost);
        //System.out.println("----------------");
        //System.out.println("----------------");
        //System.out.println("----------------");
        //System.out.println("----------------");

    }

}
