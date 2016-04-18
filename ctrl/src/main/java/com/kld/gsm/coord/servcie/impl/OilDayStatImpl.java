package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.dao.*;
import com.kld.gsm.ATG.domain.AlarmDailyLost;
import com.kld.gsm.ATG.domain.DailyDailyBalance;
import com.kld.gsm.ATG.domain.SysManageDict;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.dao.OilCanInforDao;
import com.kld.gsm.coord.dao.OilDayStatDao;
import com.kld.gsm.coord.domain.OilDayStat;
import com.kld.gsm.coord.domain.TeamHotoInfor;
import com.kld.gsm.coord.servcie.OilDayStatService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/11/3 12:23
 * @Description: 成品油日结存报表(oildaystat)
 */
@SuppressWarnings("all")
@Service
public class OilDayStatImpl implements OilDayStatService {
    @Autowired
    private OilDayStatDao oilDayStatDao;
    @Autowired
    private OilCanInforDao oilCanInforDao;
    @Autowired
    DailyDailyBalanceDao dailyDailyBalanceDao;
    @Autowired
    DailyPumpDigitShiftDao dailyPumpDigitShiftDao;
    @Autowired
    DailyTankShiftDao dailyTankShiftDao;
    @Autowired
    DailyTradeAccountDao dailyTradeAccountDao;
    @Autowired
    DailyOpoCountDao dailyOpoCountDao;
    @Autowired
    AcceptanceOdRegisterDao acceptanceOdRegisterDao;
    @Autowired
    SysManageDictDao sysManageDictDao;
    @Autowired
    AlarmDailyLostDao alarmDailyLostDao;
    private static final Logger log = LoggerFactory.getLogger(OilDayStatImpl.class);
    String minTeamVouchno ;
    String maxTeamVouchno ;

    @Override
    public void findByDate(Date date) {
        log.info("-------daystatimpi 开始查询..." + date);
        //System.out.println("-------daystatimpi 开始查询..." + date);
//        try {
//
//            OilDayStat oilDayStat = oilDayStatDao.findByDate(date);
//            if (oilDayStat == null) {
//                //System.out.println("空的");
//            } else {
//                //System.out.println(oilDayStat.toString());
//            }
//        } catch (Exception e) {
//            //System.out.println("异常啦"+e);
//        }

        //todo 处理日结信息
        //根据日期 查询Sybase各个表
        // 拼装各个表的信息 组合成DailyDailyBalance对象(多个) 分别存入Mysql
        //TODO 存放日平衡表对象
        List<DailyDailyBalance> dailyList = new ArrayList<DailyDailyBalance>();
        // TODO 查询到其中一个表的数据遍历放入
        OilDayStat o = new OilDayStat();
        String s = o.getSelectAllSql("oildaystat");
        String sql = s+" where accountdate = '"+date+"'";
        log.info("oilDayStatDao.findByDate1的sql="+sql);
        List<OilDayStat> oilDayStat = oilDayStatDao.findByDate1(sql);
        //System.out.println("select :" + oilDayStat);
        log.info("select :" + oilDayStat);
        //List<DailyOilTankRegister> oilCanlEdgerList =oilCanlEdgerService.findByDate(accountDate);

        //根据日结号   交接班信息表  拿到班次号   期初  期末
        TeamHotoInforImpl teamHotoInfor = Context.getInstance().getBean(TeamHotoInforImpl.class);
        log.info("日结获取交接班信息表 期初和期末的班次[Sybase]");
        List<TeamHotoInfor> list = teamHotoInfor.findByDayTime(date);
        log.info("TeamHotoInfor[size]:" + list.size());
        //System.out.println("-----------------TeamHotoInfor---");
        for(TeamHotoInfor teamHoto :list){
            //System.out.println(teamHoto);
        }
        //System.out.println("-----------------TeamHotoInfor---");
        //根据 有班次号  拿到期初  期末 灌存
       // DailyTankShiftDao dailyTankShiftDao = Context.getInstance().getBean(DailyTankShiftDao.class);


//        Map map = new HashMap();
//        map.put("shift", "20160905%");
        //油罐交接表拿到交接班的油品总油量

        // String CanRealL = dailyTankShiftDao.findByShift(map);
        // String CanRealL = "100";

        double realstock = 0;
        double darlyankstock = 0;
        for (OilDayStat ods : oilDayStat) {
            DailyDailyBalance dailyDailyBalance = new DailyDailyBalance();

            dailyDailyBalance.setOilno(ods.getOilno());//油品编码
            log.info("油品编码:" + ods.getOilno());
            dailyDailyBalance.setDeliveryno(String.valueOf(ods.getGetbillno()));//期间进货_出库单号
            log.info("deliveryno"+String.valueOf(ods.getGetbillno()));
            dailyDailyBalance.setTodayout(BigDecimal.valueOf(ods.getDaysaleliter()));//期间付出
            // dailyDailyBalance.setLoss(BigDecimal.valueOf(ods.getDayindeliter())); //损耗量
            dailyDailyBalance.setCreatetime(new Date());//生成时间
            dailyDailyBalance.setAccountdate(date);//结账日期
            if (list.size() > 0) {
                //根据最小班次取期初库存
                List<HashMap> min = dailyTankShiftDao.findToOilL(list.get(0).getTeamvouchno());
                for (int i = 0; i < min.size(); i++) {
                    //System.out.println("qichu : " + BigDecimal.valueOf(Double.valueOf(min.get(i).get("ToOilL").toString())));
                    if (dailyDailyBalance.getOilno().equals(min.get(i).get("OilNo"))) {
                        dailyDailyBalance.setDarlyankstock(BigDecimal.valueOf(Double.valueOf(min.get(i).get("ToOilL").toString())));//期初罐存（账存）
                        darlyankstock = Double.valueOf(min.get(i).get("ToOilL").toString());
                        break;
                    }
                }

                //根据最大班次取期末库存
                List<HashMap> max = dailyTankShiftDao.findToOilL(list.get(list.size() - 1).getTeamvouchno());
                for (int i = 0; i < max.size(); i++) {
                    //System.out.println("qimo : " + BigDecimal.valueOf(Double.parseDouble(max.get(i).get("HoOilL").toString())));
                    if (dailyDailyBalance.getOilno().equals(max.get(i).get("OilNo"))) {
                        dailyDailyBalance.setRealstock(BigDecimal.valueOf(Double.parseDouble(max.get(i).get("HoOilL").toString())));//实测库存
                        realstock = Double.parseDouble(max.get(i).get("HoOilL").toString());
                        break;
                    }
                }

                //region 拿到最小班次和最大班次 重新赋值期间进货_进货数量L
                  minTeamVouchno = list.get(0).getTeamvouchno();
                  maxTeamVouchno = list.get(list.size() - 1).getTeamvouchno();

                acceptanceOdRegisterDao = Context.getInstance().getBean(AcceptanceOdRegisterDao.class);
                log.info("Min Shift:" + minTeamVouchno);
                log.info("Max Shift:" + maxTeamVouchno);

                Map map = new HashMap<String, String>();

                map.put("minshift", minTeamVouchno);
                map.put("maxshift", maxTeamVouchno);
                //System.out.println("Begin Select ......");
                log.info("开始查询  Dao:" + acceptanceOdRegisterDao);
                List<HashMap> findByShiftList = acceptanceOdRegisterDao.findByShift(map);

                dailyDailyBalance.setReceivel(BigDecimal.valueOf(0));

                if (findByShiftList.size()>0) {

                    log.info("findByShiftList.Size（）: " + findByShiftList.size());

                    for (int i = 0; i < findByShiftList.size(); i++) {
                        log.info("--------------------------" + i);
                        log.info("oilno : " + findByShiftList.get(i).get("OilNo"));
                        log.info("RealGetLV20 : " + findByShiftList.get(i).get("RealGetLV20"));

                        if (dailyDailyBalance.getOilno().equals(findByShiftList.get(i).get("OilNo"))) {
                            log.info("Get Shift Receivel:" + findByShiftList.get(i).get("RealGetL"));
                            dailyDailyBalance.setReceivel(BigDecimal.valueOf((Double) (findByShiftList.get(i).get("RealGetL")==null?"0":findByShiftList.get(i).get("RealGetL"))));//期间进货_进货数量L
                            break;
                        }
                    }

                }
                log.info("Set Shift Receivel:" + dailyDailyBalance.getReceivel());
                //endregion

            }

            //region 期末库存 = 期初罐存 + 进货 - 付出
            //判断是否为空
            if(dailyDailyBalance.getDarlyankstock()==null)
            {
                dailyDailyBalance.setDarlyankstock(BigDecimal.valueOf(0));
            }
            if(dailyDailyBalance.getTodayout()==null)
            {
                dailyDailyBalance.setTodayout(BigDecimal.valueOf(0));
            }
            if(dailyDailyBalance.getRealstock()==null)
            {
                dailyDailyBalance.setRealstock(BigDecimal.valueOf(0));
            }
            double lastStock = dailyDailyBalance.getDarlyankstock().add(dailyDailyBalance.getReceivel()).subtract(dailyDailyBalance.getTodayout()).doubleValue();

            dailyDailyBalance.setTodaystock(BigDecimal.valueOf(lastStock));//期末库存
            //endregion

            //region损耗量
            BigDecimal loss = dailyDailyBalance.getTodaystock().subtract(dailyDailyBalance.getRealstock());
            dailyDailyBalance.setLoss(loss);
            //endregion

            //region 损耗率  v 损耗率=损耗量/期间付出        百分比乘以100，2位小数
            if (ods.getDaysaleliter() < 0.000001) {
                dailyDailyBalance.setLosssent(BigDecimal.valueOf(100));
            } else {
                dailyDailyBalance.setLosssent(BigDecimal.valueOf(round(loss, ods.getDaysaleliter(), 4) * 100));
            }
            //endregion
            dailyDailyBalance.setTranstatus("0");
            log.info("日结开始：" + dailyDailyBalance.toString());
            dailyList.add(dailyDailyBalance);

            //region
            //endregion
            //查询oss_sysmanage_Dict的日结损益率异常值
            String code = "rjsyycz";//日结损益率异常值
            /*Double Realstock = ods.getDayindeliter() / Double.valueOf(lastStock);
            log.info("ods.getDayindeliter():" + ods.getDayindeliter());
            log.info("lastStock:"+lastStock);
            log.info("realstock:"+Realstock.toString());*/
            SysManageDictDao sysManageDictDao = Context.getInstance().getBean(SysManageDictDao.class);
            SysManageDict sysManageDict = sysManageDictDao.selectByCode(code);

            if (Double.valueOf(sysManageDict.getValue()) < Double.parseDouble(dailyDailyBalance.getLosssent().toString())) {
                //插入到oss_alarm_dailylost日结损溢预警表


                AlarmDailyLost alarmDailyLost = new AlarmDailyLost();
                alarmDailyLost.setAccountdate(ods.getAccountdate());
                alarmDailyLost.setOilno(ods.getOilno());
                alarmDailyLost.setDarlyankstock(darlyankstock);
                //log.info("Billno:"+ods.getGetbillno());
                alarmDailyLost.setDeliveryno(ods.getGetbillno());
                alarmDailyLost.setReceivel(ods.getDayinoiliter());
                alarmDailyLost.setTodayout(ods.getDaysaleliter());
                alarmDailyLost.setTodayendstock(lastStock);

                alarmDailyLost.setRealstock(realstock);
                DecimalFormat decimalFormat = new DecimalFormat("######0.00");
                alarmDailyLost.setCost(Double.valueOf(decimalFormat.format(lastStock - realstock)));//损耗量 = 期末库存 - 实测库存
                if(ods.getDaysaleliter()>1) //付油量小于1 没有计算的意义
                {
                    //损耗率 按百分比保存
                    alarmDailyLost.setCostsent(Double.valueOf(decimalFormat.format((lastStock - realstock) / ods.getDaysaleliter()*100)));//损耗量-期间付出
                }
                else
                {
                    alarmDailyLost.setCostsent(Double.valueOf(100));
                }
                alarmDailyLost.setCreatetime(new Date());
                alarmDailyLost.setTranstatus("0");
                AlarmDailyLostDao alarmDailyLostDao = Context.getInstance().getBean(AlarmDailyLostDao.class);
                log.info("日结损溢数据:"+alarmDailyLost.toString());
                alarmDailyLostDao.insert(alarmDailyLost);

            }
            log.info("日结损溢计算结束");
        }
        log.info("日结开始");
        for (DailyDailyBalance as : dailyList) {
            //做mysql 插入
            int flag = dailyDailyBalanceDao.insert(as);
            if (flag == 0) {
                //System.out.println("插入失败");
            } else if (flag == 1) {
                //System.out.println("插入成功");
            }
        }
        log.info("日结结束");

        //更新交易记录以及罐表的班次号
        Map map = new HashMap();
        log.info("-最小班次号:-----------" + minTeamVouchno + "----------");
        log.info("-最大班次号:-----------" + maxTeamVouchno + "---------");
        map.put("minshift", minTeamVouchno);
        map.put("maxshift", maxTeamVouchno);
        map.put("accountdate", date);
/*        List<DailyTankShift> dailyTankShiftList = dailyTankShiftDao.findBetweenShift(map);
        for (DailyTankShift dailyTankShift : dailyTankShiftList) {
            System.out.println("查询到：——"+dailyTankShift.toString());
            dailyTankShift.setAccountdate(date);
            System.out.println("更新Account："+dailyTankShift.toString());
            int i = dailyTankShiftDao.updateByShift(dailyTankShift);
            if(i==1){
                System.out.println("更新成功");
            }else {
                System.out.println("更新失败");
            }
        }*/
        try {
            dailyTankShiftDao.updateByShift(map);
            dailyTradeAccountDao.updateByShift(map);
            dailyOpoCountDao.updateByShift(map);
            dailyPumpDigitShiftDao.updateByShift(map);
        }catch (Exception e)
        {
            log.error("Update Shift Error！"+e.getMessage());
        }
    }

    @Test
    public void a() {
        //Date timeDate2 = Timestamp.valueOf("2016-09-05 20:40:13.0");
        ////System.out.println(timeDate2);
        //System.out.println(round(BigDecimal.valueOf(33), 1111, 2));
        //根据班次号查询出对象
    }

    public static double round(BigDecimal x, double y, int len) {     // 进行四舍五入    操作
        //  BigDecimal b1 = new BigDecimal(x);
        BigDecimal b2 = new BigDecimal(y);
        // 任何一个数字除以1都是原数字
        // ROUND_HALF_UP是BigDecimal的一个常量，
        return x.divide(b2, len, BigDecimal.
                ROUND_HALF_UP).doubleValue();
    }
}


