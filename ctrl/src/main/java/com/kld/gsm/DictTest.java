package com.kld.gsm;

import com.kld.gsm.ATG.dao.AlarmShiftLostDao;
import com.kld.gsm.ATG.dao.DailyTradeInventoryDao;
import com.kld.gsm.ATG.dao.SysManageDictDao;
import com.kld.gsm.ATG.domain.AlarmShiftLost;
import com.kld.gsm.ATG.domain.SysManageDict;
import com.kld.gsm.coord.Context;
import org.springframework.beans.factory.annotation.Autowired;

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
    @Autowired
    private DailyTradeInventoryDao dailyTradeInventoryDao;
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
//a();

    }

    public void a() {

        //dailyTradeInventoryDao.insert();

    }

}
