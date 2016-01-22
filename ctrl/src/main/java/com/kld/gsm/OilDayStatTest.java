package com.kld.gsm;

import com.kld.gsm.ATG.dao.AcceptanceOdRegisterDao;
import com.kld.gsm.coord.Context;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-11-12 18:20
 * @Description: 成品油日结存报表查询 ----日结信息 1
 */
public class OilDayStatTest {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(OilDayStatTest.class);

    public static void main(String[] args) {
/*//收到日结信息  查询  成品油日结存报表
//        OilDayStatImpl oilDayStatimpl = Context.getInstance().getBean(OilDayStatImpl.class);
        Date timeDate2 = Timestamp.valueOf("2016-09-23 15:48:36.0");//20:40:13
        logger.info(timeDate2 + "11<<<<<<<<<<<<<<<<");
//        oilDayStatimpl.findByDate(timeDate2);
//  查询所有的班次号   拿到最小的和最大的
        //根据日结号   交接班信息表  拿到班次号   期初  期末
        TeamHotoInforImpl teamHotoInfor = Context.getInstance().getBean(TeamHotoInforImpl.class);
        logger.info(timeDate2 + "22<<<<<<<<<<<<<<<<");

        List<TeamHotoInfor> list = teamHotoInfor.findByDayTime(timeDate2);
        logger.info(timeDate2 + "33<<<<<<<<<<<<<<<<");

        logger.info("list :" + list.size());

        for (int i = 0; i < list.size(); i++) {
            list.get(i).getTeamvouchno();
            logger.info("teamvouchno :{" + list.get(i).getTeamvouchno() + "}");
        }

        //拿到期初  期末 灌存
        DailyTankShiftDao dailyTankShiftDao = Context.getInstance().getBean(DailyTankShiftDao.class);
        //System.out.println("444<<<<<<<<<<<<<<<<<<<<");
        if (list.size() > 0) {
            //System.out.println("555<<<<<<<<<<<<<<<<<<<<");

            //期初灌存
            List<HashMap> min = dailyTankShiftDao.findToOilL(list.get(0).getTeamvouchno());
            for (int i = 0; i < min.size(); i++) {
                Double str = Double.valueOf(min.get(i).get("OilNo").toString());
                //System.out.println("qichu : (str)" + str);
                ////System.out.println("qichu : (integer)"+Integer.parseInt(min.get(i).get("ToOilL")));

                // //System.out.println("qichu : " + min.get(i).get("ToOilL"));
            }

            //期末灌存
            List<HashMap> max = dailyTankShiftDao.findToOilL(list.get(list.size() - 1).getTeamvouchno());
            for (int i = 0; i < max.size(); i++) {
                //System.out.println(max.get(i).get("ToOilL").toString());
//                //System.out.println("qimo : (integer)"+str);
                // / //System.out.println("qimo : (integer)"+Integer.parseInt(max.get(i).get("ToOilL")));

                //  //System.out.println("qimo : (double)"+Double.parseDouble(max.get(i).get("ToOilL")));

                //System.out.println("qimo : " + Double.parseDouble(min.get(i).get("ToOilL").toString()));
            }
        }
        //System.out.println("666<<<<<<<<<<<<<<<<<<<<");*/


//         DailyInforHandler.getInformation(timeDate2);
        //System.out.println("\\\\\\\\\\\\\\\\\\\\");
        getOdRegister();
    }

public static void getOdRegister(){
    //System.out.println("开始查询");
    AcceptanceOdRegisterDao acceptanceOdRegisterDao = Context.getInstance().getBean(AcceptanceOdRegisterDao.class);
    //System.out.println("Crete");
    Map map = new HashMap<String,String>();
    map.put("minshift", "2016092801");
    map.put("maxshift", "2016092801");
    //System.out.println("Begin Select ......");
    List<HashMap> list = acceptanceOdRegisterDao.findByShift(map);

    //System.out.println("-----------------------------");
    //System.out.println("-----------------------------");
    //System.out.println("-----------------------------");
    //System.out.println("-----------------------------");
    //System.out.println("-----------------------------");
    //System.out.println("list.Size（）: "+list.size());
    for (int i = 0; i < list.size(); i++) {
        //System.out.println("--------------------------"+i);
        //System.out.println("oilno : "+list.get(i).get("OilNo"));
        //System.out.println("RealGetLV20 : "+list.get(i).get("RealGetLV20"));
    }

}
}
