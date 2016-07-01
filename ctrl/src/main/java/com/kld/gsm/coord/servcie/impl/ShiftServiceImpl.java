package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.dao.*;
import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.dao.*;
import com.kld.gsm.coord.domain.*;
import com.kld.gsm.coord.servcie.IShiftService;
import com.kld.gsm.coord.servcie.TeamHotoService;
import com.kld.gsm.util.SybaseUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yinzhiguang on 2015/11/8.
 */
@SuppressWarnings("all")
@Service
public class ShiftServiceImpl implements IShiftService {
    @Autowired
    OilcanhotobillDao oilcanhotobillDao;
    @Autowired
    OilhotobillDao oilhotobillDao;
    @Autowired
    PayoilstatDao payoilstatDao;
    @Autowired
    PayoilclassStatDao payoilclassStatDao;
    @Autowired
    DailyTankShiftDao dailyTankShiftDao;
    @Autowired
    DailyPumpDigitShiftDao dailyPumpDigitShiftDao;
    @Autowired
    DailyOpoCountDao dailyOpoCountDao;
    @Autowired
    DailyOpotCountDao dailyOpotCountDao;
    @Autowired
    DailyShiftStockDao dailyShiftStockDao;
    @Autowired
    AlarmShiftLostDao alarmShiftLostDao;
    @Autowired
    SysManageDictDao sysManageDictDao;
    @Autowired
    TeamstockDao teamstockDao;
    @Autowired
    OilVouchDao oilVouchDao;
    @Autowired
    DailyTradeAccountDao dailyTradeAccountDao;
    @Autowired
    TeamHotoService teamHotoService;
    @Autowired
    DailyStationShiftInfoDao dailyStationShiftInfoDao;
    @Autowired
    AcceptanceOdRegisterInfoDao acceptanceOdRegisterInfoDao;
    @Autowired
    AcceptanceOdRegisterDao acceptanceOdRegisterDao;
    @Autowired
    OilcanindetailDao oilcanindetailDao;
    @Autowired
    OilGunInforDao oilGunInforDao;
    @Autowired
    SysManageOilGunInfoDao sysManageOilGunInfoDao;
    Logger log = Logger.getLogger(ShiftServiceImpl.class);
    double saleL;

    @Override
    public void saveShiftInfo(String shiftno) {

        try {
            log.info("start to save payoilstat~~~||||||||||||||||~`");
            //付油数量统计交接表(payoilstat)
            String sql  ="SELECT teamvouchno,takedate,oilno,oilname,backcanliter,oilcanno," +
                    " passnum,oiltotal,moneytotal,accountdate,hotoflag,transflag" +
                    " FROM payoilstat" +
                    " WHERE teamvouchno='"+shiftno+"'";
            log.info("getPayoilstat1的sql:"+sql);
            List<Payoilstat> payoilstatList = payoilstatDao.getPayoilstat1(sql);
            DailyOpoCount dailyOpoCount = new DailyOpoCount();
            payoilstat2dailyOpoCount(payoilstatList, dailyOpoCount);

            log.info("start to save ATGTEAMSTOCK~~~||||||||||||||||||||`");
            //班结库存表(ATGTEAMSTOCK)
            //System.out.println("shiftno:" + shiftno);
            Teamstock t = new Teamstock();
            String s = t.getSelectAllSql("ATGTEAMSTOCK");
            sql =s +
                    " WHERE teamvouchno='"+shiftno+"'";
            log.info("getTeamstock1的sql:"+sql);
            List<Teamstock> teamstockList = (List<Teamstock>) teamstockDao.getTeamstock1(sql);
            DailyShiftStock dailyShiftStock = new DailyShiftStock();
            teamstock2dailyShiftStock(teamstockList, dailyShiftStock);

            log.info("start to save oilhotobill~~|||||||||||||||~`");
            //泵码交接表(oilhotobill)
            sql ="SELECT vouchno,teamvouchno,takeDate,oilno,oilname, " +
                    " oilgunno,Topump,Hopump,Passnum,accountdate,Billstatus,hotoflag,Transflag " +
                    " FROM oilhotobill WHERE teamvouchno='"+shiftno+"' ";
            log.info("getOilhotobill1的sql:"+sql);
            List<Oilhotobill> oilhotobillList = oilhotobillDao.getOilhotobill1(sql);
            DailyPumpDigitShift dailyPumpDigitShift = new DailyPumpDigitShift();
            oilhotobill2dailyPumpDigitShift(oilhotobillList, dailyPumpDigitShift);

            log.info("start to save PAYOILCLASS_STAT~~~~||||||||||||||||||");
            //付油量分类统计表（按付油类型）(PAYOILCLASS_STAT)
            sql ="SELECT teamvouchno,takedate,oilno,oilname,payoiltype, " +
                    " oilamount,unitprice,amount,transflag,hotoflag,dayflag,accountdate " +
                    " FROM payoilclass_stat " +
                    " WHERE teamvouchno='"+shiftno+"'";
            log.info("getPayoilclassStat1的sql:"+sql);
            List<PayoilclassStat> payoilclassStatList = payoilclassStatDao.getPayoilclassStat1(sql);
            log.info("reslut payoilclassStat:" + payoilclassStatList);
            DailyOpotCount dailyOpotCount = new DailyOpotCount();
            payoilclassStat2dailyOpotCount(payoilclassStatList, dailyOpotCount);

            //region 油站班报表
            teamHotoService = Context.getInstance().getBean(TeamHotoService.class);
            dailyStationShiftInfoDao = Context.getInstance().getBean(DailyStationShiftInfoDao.class);

            sql =" select  * from teamhoto  where  teamvouchno = '"+shiftno+"'";
            log.info("teamhoto的sql:"+sql);

            //TeamHoto teamHoto = teamHotoService.findByTeamVouchNo(shiftno);
            TeamHoto teamHoto = teamHotoService.findByTeamVouchNo1(sql);

            log.info("油站班报同步:" + teamHoto);

            if (teamHoto != null) {

                log.info("Sybase  TeamHoto List：" + teamHoto);
                DailyStationShiftInfo dailyStationShiftInfo = new DailyStationShiftInfo();
                dailyStationShiftInfo.setShift(Integer.valueOf(teamHoto.getTeamvouchno()));
               /* //System.out.println(teamHoto.getToteamer());
                //System.out.println(teamHoto.getHoteamer());
                //System.out.println(SybaseUtils.getSybaseCNStr(teamHoto.getToteamer()));
                //System.out.println(SybaseUtils.getSybaseCNStr(teamHoto.getHoteamer()));*/
                dailyStationShiftInfo.setSuccessor(SybaseUtils.getSybaseCNStr(teamHoto.getToteamer()));
                dailyStationShiftInfo.setShiftoperator(SybaseUtils.getSybaseCNStr(teamHoto.getHoteamer()));
                //SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                log.info("date : " + new Date());
                dailyStationShiftInfo.setShifttime(new Date());
                dailyStationShiftInfo.setTranstatus("0");
                log.info("toteamer:" + SybaseUtils.getSybaseCNStr(teamHoto.getToteamer()));
                log.info("hoteamer:" + SybaseUtils.getSybaseCNStr(teamHoto.getHoteamer()));

                log.info("stationshiftInfo : " + dailyStationShiftInfo.toString());
                // //System.out.println(dailyStationShiftInfo.toString());

                dailyStationShiftInfoDao.insert(dailyStationShiftInfo);
            }
            log.info("此处开始更新oss_daily_TradeAccount");
            //endregion
            //交易明细表（oilvouch）
            sql = "SELECT macno,ttc,takedate,oilgunno,teamvouchno " +
                    "        FROM oilvouch " +
                    "        WHERE teamvouchno = '"+shiftno+"'";
            List<OilVouch> oilVouchList = oilVouchDao.selectByshift1(sql);
            for (OilVouch oilVouch : oilVouchList) {
                //更新Mysql交易加油流水表（oss_daily_TradeAccount）
                if (!"".equals(oilVouch.getCardno()) && null != oilVouch.getCardno()&&oilVouch.getCardno().length()>=5) {
                    String cradNoStr = oilVouch.getCardno().substring(3, 5);
                    if(cradNoStr.equals("05")||cradNoStr.equals("06")){
                        log.info("CardNo:"+oilVouch.getCardno());
                        log.info("更新回灌标识");
                        oilVouch.setBackmatchflag("1");
                    }
                 }
                dailyTradeAccountDao.updateByKey(oilVouch);
            }
            log.info("更新oss_daily_TradeAccount完毕");
            log.info("此处开始更新oss_acceptance_odRegisterInfo");

            log.info("start to save oilcanhotobill~~~|||||||||||||`");
            //油罐交接表(oilcanhotobill)
            sql=" SELECT vouchno,teamvouchno,takedate,oilcanno,oilno,oilname,tooilhigh," +
                    "       tooilliter,inoilliter,hooilhigh,hooilliter,saleliter,accountdate," +
                    "       canfact,waterheight,pureheight,billstatus,hotoflag,transflag" +
                    "       FROM oilcanhotobill" +
                    "       WHERE teamvouchno='"+shiftno+"'";
            log.info("oilcanhotobill的sql:"+sql);
            List<Oilcanhotobill> oilcanhotobillList = oilcanhotobillDao.getOilcanhotobill1(sql);
            DailyTankShift dailyTankShift = new DailyTankShift();
            oilhotobill2dailyTankShift(oilcanhotobillList, dailyTankShift);

            //根据sybase的oilcanindetail更新Mysql
            sql="select * from oilcanindetail where teamvouchno='"+shiftno+"'";
            log.info("oilcanindetail的sql:"+sql);
            List<OilCanIndeTail> oilcanindetailList =oilcanindetailDao.selectByOilvoch1(sql);
            for (OilCanIndeTail oilCanIndeTail : oilcanindetailList) {
                HashMap map = new HashMap();
                String manualno = oilCanIndeTail.getGoodsbillno();
                String shift = oilCanIndeTail.getTeamvouchno();
                map.put("manualno", manualno);
                map.put("shift", shift);
                //更新 （oss_acceptance_odRegisterInfo）
                acceptanceOdRegisterInfoDao.updateByManualNo(map);
                log.info("此处更新oss_acceptance_odRegisterInfo完毕");
                log.info("打印" + map.toString());
                //更新Mysql（oss_acceptance_odRegister）
                log.info("此处开始更新oss_acceptance_odRegisterInfo");
                acceptanceOdRegisterDao.updateByManualNo(map);
                log.info("打印" + map.toString());
                log.info("此处更新oss_acceptance_odRegisterInfo完毕");

            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("此…………………………………………………………………………………………………………………………：" + e.getMessage().toString());
        }
    }

    @Override
    public  List<Payoilstat> selectDifferent() {
        log.info("进入班结的sql查询");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDate=sd.format(new Date());
        String shift = dailyOpoCountDao.selectbymax(nowDate);

        log.info("打印sql"+shift);
        List<Payoilstat> payoilstatList=new ArrayList<Payoilstat>();
        if(null!=shift && !"".equals(shift)){
            String sql1  ="SELECT teamvouchno FROM payoilstat" +
                    " WHERE teamvouchno >'"+shift+"'";
            log.info("打印sql1"+sql1.toString());

            payoilstatList = payoilstatDao.getPayoilstat2(sql1);

            return payoilstatList;
        }else{
            log.info("mysql库查询班结班次为空");

        }
        return payoilstatList;
    }

    private void payoilstat2dailyOpoCount(List<Payoilstat> payoilstatList, DailyOpoCount dailyOpoCount) throws Exception {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Payoilstat payoilstat : payoilstatList) {
            dailyOpoCount.setShift(payoilstat.getTeamvouchno());
            dailyOpoCount.setTakedate(new Date());
            dailyOpoCount.setOilno(payoilstat.getOilno());
            dailyOpoCount.setOilname(SybaseUtils.getSybaseCNStr(payoilstat.getOilname()));
            dailyOpoCount.setBackcanl(payoilstat.getBackcanliter());
            dailyOpoCount.setOilcan(payoilstat.getOilcanno());
            dailyOpoCount.setPumpnum(payoilstat.getPassnum());
            dailyOpoCount.setOiltotal(payoilstat.getOiltotal());
            saleL = payoilstat.getOiltotal();


            dailyOpoCount.setAmount(payoilstat.getMoneytotal());
            dailyOpoCount.setAccountdate(payoilstat.getAccountdate());
            dailyOpoCount.setTranstatus("0");
            //保存mysql库 付油数量统计交接表
            dailyOpoCountDao.insertSelective(dailyOpoCount);
        }
    }


    private void oilhotobill2dailyTankShift(List<Oilcanhotobill> oilcanhotobillList, DailyTankShift dailyTankShift) throws Exception {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        for (Oilcanhotobill oilcanhotobill : oilcanhotobillList) {
            dailyTankShift.setShift(oilcanhotobill.getTeamvouchno());
            dailyTankShift.setTakedate(new Date());
            dailyTankShift.setOilcan(Integer.parseInt(oilcanhotobill.getOilcanno()));
            //设置油品编码
            dailyTankShift.setOilno(oilcanhotobill.getOilno() + "");
            dailyTankShift.setOilname(SybaseUtils.getSybaseCNStr(oilcanhotobill.getOilname()));
            dailyTankShift.setTooilhigh(Double.parseDouble(oilcanhotobill.getTooilhigh()));
            if (oilcanhotobill.getTooilhigh() != null) {
                dailyTankShift.setTooilhigh(Double.parseDouble(oilcanhotobill.getTooilhigh()));
            }
            dailyTankShift.setTooill(oilcanhotobill.getTooilliter());
            dailyTankShift.setInoill(oilcanhotobill.getInoilliter());
            dailyTankShift.setHooilhigh(oilcanhotobill.getHooilhigh());
            dailyTankShift.setHooill(oilcanhotobill.getHooilliter());
            //TODO 1. 根据油罐号去有枪信息表中拿对应的油枪号
            String canno = oilcanhotobill.getOilcanno(); //油罐号
            List<SysManageOilGunInfo> list = sysManageOilGunInfoDao.findByOilCanNo(canno);//油枪号 当前油罐所属的油枪号
            for (SysManageOilGunInfo sysManageOilGunInfo : list) {
                log.info("油枪号：" + sysManageOilGunInfo.getOilgun());
            }


            //System.out.println("List:" + list.size());
            //TODO 2. 根据油枪号+班次号拿到付油量
            String shift = oilcanhotobill.getTeamvouchno();
            double saleLiterSum = 0.0;
            log.info("Shift: " + shift);
            for (SysManageOilGunInfo sysManageOilGunInfo : list) {
                log.info("oilgun：" + sysManageOilGunInfo.getOilgun());
                Map map = new HashMap<String, String>();
                map.put("Shift", shift);
                map.put("OilGun", sysManageOilGunInfo.getOilgun());
                String sum = dailyTradeAccountDao.findLiter(map);
                if (sum != null) {
                    saleLiterSum += Double.valueOf(sum);
                }
                log.info("获取到付油量:" + sum);
            }
            log.info("saleLiterSum :" + saleLiterSum);

            dailyTankShift.setSalel(saleLiterSum);

            dailyTankShift.setAccountdate(oilcanhotobill.getAccountdate());
            dailyTankShift.setCanreall(oilcanhotobill.getCanfact());
            dailyTankShift.setHeightwater(oilcanhotobill.getWaterheight());
            dailyTankShift.setWaterl(oilcanhotobill.getPureheight());
            dailyTankShift.setTranstatus("0");
            //保存mysql库 油罐交接表
            dailyTankShiftDao.insertSelective(dailyTankShift);
            //油罐交接表(oilcanhotobill)
            Double tooill = dailyTankShift.getTooill();//接班油量
            Double inoill = dailyTankShift.getInoill();//卸油量
            Double hooill = dailyTankShift.getHooill();//交班升数
            Double salel = dailyTankShift.getSalel();//付油量
            Double profitLossRatio = calculateProfitLossRatio(tooill, inoill, hooill, salel);
            log.info("交接班损益率："+profitLossRatio);
            //查询oss_sysmanage_Dict的班结损益率异常值
            String code = "bjsyycz";//班结损益率异常值
            SysManageDictDao sysManageDictDao = Context.getInstance().getBean(SysManageDictDao.class);
            SysManageDict sysManageDict = sysManageDictDao.selectByCode(code);


            if (profitLossRatio > Double.valueOf(sysManageDict.getValue())) {
                //插入数据到交接班损益预警表中
                AlarmShiftLost alarmShiftLost = new AlarmShiftLost();
                //todo 最后加上的
                alarmShiftLost.setOilno(dailyTankShift.getOilno());

                alarmShiftLost.setShift(dailyTankShift.getShift());
                alarmShiftLost.setOilcanno(dailyTankShift.getOilcan());
                alarmShiftLost.setStartoilheight(dailyTankShift.getTooilhigh());
                alarmShiftLost.setStartoill(tooill);
                alarmShiftLost.setEndoilheight(dailyTankShift.getHooilhigh());
                alarmShiftLost.setEndoill(hooill);
                alarmShiftLost.setAcutalendoill(hooill);
                alarmShiftLost.setEndwaterheight(dailyTankShift.getHeightwater());
                alarmShiftLost.setEndwaterl(dailyTankShift.getWaterl());
                alarmShiftLost.setEndtemperature(null);//交班温度不赋值
                alarmShiftLost.setOildischarge(inoill);
                //region 待处理
                //TODO  -----
                alarmShiftLost.setSale(saleLiterSum);
                alarmShiftLost.setInventory(tooill + inoill - saleLiterSum);
                if(saleLiterSum>0) {
                    DecimalFormat df = new DecimalFormat("######0.00");
                    alarmShiftLost.setLoss(Double.valueOf(df.format((tooill + inoill - saleLiterSum - hooill) / saleLiterSum * 100)));
                }
                else
                {
                    alarmShiftLost.setLoss(100.00);
                }
                //TODO  -----
                //endregion
                alarmShiftLost.setState(null); //状态不赋值
                alarmShiftLost.setShifttime(dailyTankShift.getTakedate());
                alarmShiftLost.setTranstatus("0");
                alarmShiftLost.setProfitLossRatio(profitLossRatio);
                AlarmShiftLostDao alarmShiftLostDao = Context.getInstance().getBean(AlarmShiftLostDao.class);
                alarmShiftLostDao.insert(alarmShiftLost);
            }
        }
    }

    /**
     * @param tooill
     * @param inoill
     * @param hooill
     * @param salel
     * @return ProfitLossRatio 损益率
     */
    //计算损益率  损益率=（接班油量+卸油量-付油量-交班升数）/付油量）
    private Double calculateProfitLossRatio(Double tooill, Double inoill, Double hooill, Double salel) {
        Double ProfitLossRatio;
        log.info("计算损益率：" + salel);
        if (salel < 0.000000001) {
            ProfitLossRatio = 0.0;
        } else {
            ProfitLossRatio = (tooill + inoill - salel - hooill) / salel;
        }
        return ProfitLossRatio;
    }

    private void oilhotobill2dailyPumpDigitShift(List<Oilhotobill> oilhotobillList, DailyPumpDigitShift dailyPumpDigitShift) throws Exception {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Oilhotobill oilhotobill : oilhotobillList) {
            dailyPumpDigitShift.setShift(oilhotobill.getTeamvouchno());
            //dailyPumpDigitShift.setTakedate(sd.parse(oilhotobill.getTakedate() + " 00:00:00"));
            dailyPumpDigitShift.setTakedate(new Date());
            dailyPumpDigitShift.setOilno(oilhotobill.getOilno());
            dailyPumpDigitShift.setOilname(SybaseUtils.getSybaseCNStr(oilhotobill.getOilname()));
            dailyPumpDigitShift.setOilgun(oilhotobill.getOilgunno());
            dailyPumpDigitShift.setTopump(oilhotobill.getTopump());
            dailyPumpDigitShift.setHopump(oilhotobill.getHopump());
            dailyPumpDigitShift.setPumpnum(oilhotobill.getPassnum());
            dailyPumpDigitShift.setPaydate(oilhotobill.getAccountdate());
            dailyPumpDigitShift.setTranstatus("0");
            //保存mysql库 泵码交接表
            dailyPumpDigitShiftDao.insertSelective(dailyPumpDigitShift);

        }
    }


    private void payoilclassStat2dailyOpotCount(List<PayoilclassStat> payoilclassStatList, DailyOpotCount dailyOpotCount) {
        for (PayoilclassStat payoilclassStat : payoilclassStatList) {
            dailyOpotCount.setShift(payoilclassStat.getTeamvouchno());
            dailyOpotCount.setOilno(payoilclassStat.getOilno());
            dailyOpotCount.setOilname(SybaseUtils.getSybaseCNStr(payoilclassStat.getOilname()));
            dailyOpotCount.setType(payoilclassStat.getPayoiltype());
            dailyOpotCount.setLiter(payoilclassStat.getOilamount());
            dailyOpotCount.setPrice(payoilclassStat.getUnitprice());
            dailyOpotCount.setAmount(payoilclassStat.getAmount());
            dailyOpotCount.setTranstatus("0");
            //保存mysql库 付油量分类统计表（按付油类型）
            log.info("new PayoilclassStat:" + dailyOpotCount);
            dailyOpotCountDao.insertSelective(dailyOpotCount);
        }
    }

    private void teamstock2dailyShiftStock(List<Teamstock> teamstockList, DailyShiftStock dailyShiftStock) {
        for (Teamstock teamstock : teamstockList) {
            dailyShiftStock.setOilcan(teamstock.getOilcanno());
            dailyShiftStock.setShift(String.valueOf(teamstock.getTeamvouchno()));
            dailyShiftStock.setOpetime(teamstock.getOpetime());
            dailyShiftStock.setStockdate(teamstock.getStockdate() + "");
            dailyShiftStock.setStocktime(teamstock.getStocktime() + "");
            dailyShiftStock.setOilno(teamstock.getOilno());
            dailyShiftStock.setOilcubage(teamstock.getOilcubage());
            dailyShiftStock.setOilstandcubage(teamstock.getOilstandcubage());
            dailyShiftStock.setEmptycubage(teamstock.getEmptycubage());
            dailyShiftStock.setTotalheight(teamstock.getTotalheight());
            dailyShiftStock.setWaterheight(teamstock.getWaterheight());
            dailyShiftStock.setOiltemp(teamstock.getOiltemp());
            dailyShiftStock.setWaterbulk(teamstock.getWaterbulk());
            dailyShiftStock.setApparentdensity(teamstock.getApparentdensity());
            dailyShiftStock.setStanddensity(teamstock.getStanddensity());
            dailyShiftStock.setOilinflag(teamstock.getOilinflag());
            dailyShiftStock.setTranflag(teamstock.getTranflag());
            dailyShiftStock.setRemark(teamstock.getRemark());
            //保存mysql库 班结库存表
            //System.out.println("insert" + dailyShiftStock.getOilno());
            dailyShiftStockDao.insert(dailyShiftStock);
        }
    }

    public static void main(String[] args) {

        SysManageOilGunInfoDao sysManageOilGunInfoDao = Context.getInstance().getBean(SysManageOilGunInfoDao.class);
        DailyTradeAccountDao dailyTradeAccountDao = Context.getInstance().getBean(DailyTradeAccountDao.class);
        //TODO 1. 根据油罐号去有枪信息表中拿对应的油机号
        String canno = "1";//油罐号
        List<SysManageOilGunInfo> list = sysManageOilGunInfoDao.findByOilCanNo(canno);//油机号 当前油罐所属的油机号
        for (SysManageOilGunInfo sysManageOilGunInfo : list) {
            //System.out.println("油枪：" + sysManageOilGunInfo.getOilgun());

        }


        //System.out.println("List:" + list.size());
        //TODO 2. 根据油机号+班次号拿到付油量
        double saleLiterSum = 0.0;
        String shift = "1";
        //System.out.println("Shift: " + shift);
        for (SysManageOilGunInfo sysManageOilGunInfo : list) {
            //System.out.println("当前油机号：" + sysManageOilGunInfo.getOilgun());
            Map map = new HashMap<String, String>();
            map.put("Shift", shift);
            map.put("OilGun", sysManageOilGunInfo.getOilgun());
            String sum = dailyTradeAccountDao.findLiter(map);
            if (sum != null) {
                saleLiterSum += Double.valueOf(sum);
            }
            //System.out.println("获取到付油量:" + sum);
        }
        //System.out.println("saleLiterSum :" + saleLiterSum);

    }

}
