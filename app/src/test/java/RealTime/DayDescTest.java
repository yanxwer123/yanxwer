package RealTime;

import com.kld.app.service.AlarmDailyLostService;
import com.kld.app.springcontext.Context;
import com.kld.gsm.ATG.domain.AlarmDailyLost;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Administrator on 2015/11/26.
 */
public class DayDescTest {
    @Test
    public void getHashMap(){

        Date date = Timestamp.valueOf("2015-09-02 19:08:47");
        Date dates = Timestamp.valueOf("2016-11-26 19:08:47");
        AlarmDailyLostService alarmDailyLostService =
                (AlarmDailyLostService) (Context.getInstance().getBean("alarmDailyLostService"));
        System.out.println(date);
        System.out.println(dates);
        List<AlarmDailyLost> dailyLostList =alarmDailyLostService.selectByDate(date, dates);



        Map<String, List<AlarmDailyLost>> alarmDailyLostMap = new HashMap<String, List<AlarmDailyLost>>();
         List list1 = new ArrayList();
        List list2 = new ArrayList();
        List list3 = new ArrayList();

        for (int i = 0; i < dailyLostList.size(); i++) {

             if(dailyLostList.get(i).getOilno().equals("1.0")) {
                 list1.add(dailyLostList.get(i));
                alarmDailyLostMap.put(dailyLostList.get(i).getOilno(),list1);
            }
            if(dailyLostList.get(i).getOilno().equals("2.0")){
                list2.add(dailyLostList.get(i));
                alarmDailyLostMap.put(dailyLostList.get(i).getOilno(),list2);
            }
            if(dailyLostList.get(i).getOilno().equals("3.0")){
                list3.add(dailyLostList.get(i));
                alarmDailyLostMap.put(dailyLostList.get(i).getOilno(),list3);
            }
        }



    }
}
