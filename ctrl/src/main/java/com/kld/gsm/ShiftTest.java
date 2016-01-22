package com.kld.gsm;

import com.kld.gsm.ATG.dao.DailyStationShiftInfoDao;
import com.kld.gsm.ATG.domain.DailyDailyBalance;
import com.kld.gsm.ATG.domain.DailyStationShiftInfo;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.domain.TeamHoto;
import com.kld.gsm.coord.domain.VouchStock;
import com.kld.gsm.coord.servcie.TeamHotoService;
import com.kld.gsm.coord.servcie.impl.TransactionalServiceImpl;
import org.junit.Test;
import org.slf4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-12-07 17:28
 * @Description:
 */
public class ShiftTest {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(ShiftTest.class);

    public static void main(String[] args) throws Exception {
        TransactionalServiceImpl transacServiceImpl = Context.getInstance().getBean(TransactionalServiceImpl.class);

        VouchStock vouchStock = new VouchStock();
        vouchStock.setOilno("12");
        vouchStock.setMacno(13);
        vouchStock.setTtc(14);
        vouchStock.setTakedate(new Date());
        vouchStock.setOilgunno("15");
        vouchStock.setOilcanno(16);
        vouchStock.setOilno("17");
        vouchStock.setOpetime(new Date());
        vouchStock.setEmptycubage(1.1);
        vouchStock.setTotalheight(2.2);
        vouchStock.setWaterheight(3.3);
        vouchStock.setOiltemp(4.4);
        vouchStock.setWaterbulk(5.5);
        vouchStock.setApparentdensity(6.6);
        vouchStock.setStanddensity(8.8);
        vouchStock.setStockdate("2015-12-21");
        vouchStock.setStocktime("16:14:25");
        vouchStock.setOilcubage(0.0);
        vouchStock.setOilcubage(11.1);
        vouchStock.setOilstandcubage(22.2);
        vouchStock.setTeamvouchno("33.3");
        vouchStock.setTranflag("44.4");
        vouchStock.setRemark("55.5");
        transacServiceImpl.save(vouchStock);


        DailyDailyBalance dailyDailyBalance = new DailyDailyBalance();
        dailyDailyBalance.setOilno("1");
        dailyDailyBalance.setAccountdate(new Date());
        transacServiceImpl.save(dailyDailyBalance);
 //
//        DailyStationShiftInfoDao dailyStationShiftInfoDao = Context.getInstance().getBean(DailyStationShiftInfoDao.class);
//
//        DailyStationShiftInfo dailyStationShiftInfo = new DailyStationShiftInfo();
//        dailyStationShiftInfo.setShift(1);
//        dailyStationShiftInfo.setSuccessor("接班人");
//        dailyStationShiftInfo.setSucceedtime(new Date());
//        dailyStationShiftInfo.setShiftoperator("交班人");
//        dailyStationShiftInfo.setShifttime(new Date());
//        dailyStationShiftInfo.setTranstatus("1");
//        dailyStationShiftInfoDao.insert(dailyStationShiftInfo);
//         //System.out.println("插入成功OK");
//        getTeamHoto();
    }

    public static void getTeamHoto() {
        TeamHotoService teamHotoService = Context.getInstance().getBean(TeamHotoService.class);
        DailyStationShiftInfoDao dailyStationShiftInfoDao = Context.getInstance().getBean(DailyStationShiftInfoDao.class);

        System.out.print("班结同步.....");
        TeamHoto teamHoto = teamHotoService.findByTeamVouchNo("2016092301");
        if (teamHoto != null) {
            //System.out.println("Sybase  TeamHoto List：" + teamHoto);
            DailyStationShiftInfo dailyStationShiftInfo = new DailyStationShiftInfo();
            dailyStationShiftInfo.setShift(Integer.valueOf(teamHoto.getTeamvouchno()));
            dailyStationShiftInfo.setSuccessor(teamHoto.getToteamer());
            dailyStationShiftInfo.setShiftoperator(teamHoto.getHoteamer());
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            try {
                dailyStationShiftInfo.setShifttime(sd.parse(teamHoto.getTakedate()));
            } catch (ParseException e) {
                //System.out.println(teamHoto.getTakedate() + "Date转换异常");
            }
            dailyStationShiftInfo.setTranstatus(teamHoto.getTransflag());
            dailyStationShiftInfoDao.insert(dailyStationShiftInfo);
        } else {
            //System.out.println("you look : " + teamHoto);
        }
    }

    @Test
    public void test() throws ParseException {
        String str = "2015-12-07";
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Date a = sd.parse(str);//();开始报警时间
        //System.out.println(a);


    }
}
