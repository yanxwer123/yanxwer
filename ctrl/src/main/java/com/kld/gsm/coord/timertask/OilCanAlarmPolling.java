package com.kld.gsm.coord.timertask;

import com.kld.gsm.ATG.dao.AlarmInventoryDao;
import com.kld.gsm.ATG.dao.SysManageAlarmParameterDao;
import com.kld.gsm.ATG.dao.SysManageCanInfoDao;
import com.kld.gsm.ATG.domain.AlarmInventory;
import com.kld.gsm.ATG.domain.SysManageAlarmParameter;
import com.kld.gsm.ATG.domain.SysManageCanInfo;
import com.kld.gsm.ATG.service.OilCanAlarmService;
import com.kld.gsm.ATGDevice.ATGManager;
import com.kld.gsm.ATGDevice.atg_stock_data_out_t;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.servcie.IOilCanAlarmProbeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="yanxwer@163.com">yanxiaowei</a>
 * @version 1.0
 * @CreationTime: 2015/11/24 20:20
 * @Description:油罐报警 报警类型定义 ：
 * 2003-低液位预警；  2004- 低液位报警；
 * 2002-高液位预警；  2001-高液位报警；
 * 2008-低温报警；   2007-高温报警
 * 2005-高水位预警；2006-高水位报警
 */
@SuppressWarnings("all")
public class OilCanAlarmPolling extends Thread  {
    static Logger logger = Logger.getLogger(OilCanAlarmPolling.class);
    @Resource
    IOilCanAlarmProbeService oilCanAlarmProbeService;
    @Resource
    OilCanAlarmService oilCanAlarmService;
    @Autowired
    SysManageCanInfoDao sysManageCanInfoDao;
    @Autowired
    AlarmInventoryDao alarmInventoryDao;
    @Autowired
    SysManageAlarmParameterDao sysManageAlarmParameterDao;


    @Override
    public void run() {
        while(true){
            try {
                sleep(TimeTaskPar.get("ygbjjgsj")*1000);
            } catch (InterruptedException e) {
                logger.error("sleep:" + e);
                e.printStackTrace();
            }
            logger.error(" 进入 OilCanAlarmPolling. 油罐报警.....");
            List<Integer> oilCanList;
            List<AlarmInventory> alarmInventotyList=new ArrayList<AlarmInventory>();
            List<SysManageAlarmParameter> alarmList;
            List<atg_stock_data_out_t> out_ts;
            //region获取所有油罐信息
            try {
                sysManageCanInfoDao = Context.getInstance().getBean(SysManageCanInfoDao.class);
                //List<SysManageCanInfo> list = sysManageCanInfoDao.selectAll();
                //所有的油罐号
                //oilCanList = new ArrayList();
               /* //提取所有油罐号
                for (SysManageCanInfo oilCanInfor : list) {
                    oilCanList.add(oilCanInfor.getOilcan());
                }*/
               /* for (int i : oilCanList) {
                    ////System.out.println("all oilcan : " + i);
                }*/
            }catch (Exception e)
            {
                logger.error("oilcanAlarm task-- get oil can info error:"+e.getMessage());
                return;

            }
            //endregion

            //region获取所有油罐报警设置信息
            try {
                sysManageAlarmParameterDao = Context.getInstance().getBean(SysManageAlarmParameterDao.class);
                alarmList = sysManageAlarmParameterDao.selectAll();
                logger.info("get AlarmParameter List :" + alarmList.size());
                for (SysManageAlarmParameter a : alarmList) {
                    logger.info("all  setalarmparmeter  : " + a);
                }
            }catch (Exception ex)
            {
                logger.error("OilCanAlarm task get oil can setting error"+ex.getMessage());
                return;
            }
            //endregion

            //region返回实时库存
            try{
                out_ts = ATGManager.getStock(new ArrayList());
                for (atg_stock_data_out_t asdot : out_ts) {
                    logger.info("atg_stock_data_out_t:[uOilCanNo] :" + asdot.uOilCanNo);
                    logger.info("atg_stock_data_out_t:[fTotalHeight]: " + asdot.fTotalHeight);
                    logger.info("atg_stock_data_out_t:[fWaterHeight]: " + asdot.fWaterHeight);
                    logger.info("atg_stock_data_out_t:[fOilTemp] :" + asdot.fOilTemp);
                }
            }catch (Exception exs)
            {
                logger.error("task error  get atg_stock"+exs.getMessage());
                return;
            }
            //endregion

            //region获取正在报警数据
            try {
                alarmInventoryDao = Context.getInstance().getBean(AlarmInventoryDao.class);
                alarmInventotyList = alarmInventoryDao.findBeginAlarm();
                logger.info("baojingsize:" + alarmInventotyList.size());
                for (AlarmInventory alarmInventory : alarmInventotyList) {
                    logger.info("get AlarmInventory aaaa :" + alarmInventory);
                }
            }catch (Exception exi)
            {
                logger.error("Task Error Inventory"+exi.getMessage());
                return;
            }
            //endregion



            //region 实时库存 进行油罐报警对比
            for (atg_stock_data_out_t data_out_t : out_ts) {

                SysManageAlarmParameter alarmParam = getAlarmParameter(data_out_t.uOilCanNo, alarmList);
                logger.info("real gun ...." + data_out_t.uOilCanNo);

                //当前油罐号存在预警设置数据
                if (alarmParam != null) {
                    logger.info("alarmParam[2003] is " + alarmParam);

                    //region 高液位报警 2001
                    try {
                        if (data_out_t.fTotalHeight >= alarmParam.getHighalarm()) {
                            AlarmInventory alarmInventory = getAlarmInventory(alarmParam.getOilcan()
                                    , 2001, alarmInventotyList);
                            logger.info("alarmParam[2001] come in");

                            //返回对象，要判断是不是空
                            if (alarmInventory == null) {
                                //TODO 当前油罐没有报警 进行插入
                                AlarmInventory alarmInventory3 = new AlarmInventory();
                                alarmInventory3.setOilcan(alarmParam.getOilcan());
                                alarmInventory3.setAlarmtype(2001);
                                alarmInventory3.setAlarmdesc("高液位报警");
                                alarmInventory3.setStarttime(new Date());
                                alarmInventory3.setTranstatus("0");

                                insert(alarmInventory3);
                            }
                        } else {
                            AlarmInventory alarmInventory = getAlarmInventory(alarmParam.getOilcan()
                                    , 2001, alarmInventotyList);
                            if (alarmInventory != null) {
                                //TODO 做更新操作
                                logger.info("update alarmParam[2001] come in");

                                alarmInventory.setEndtime(new Date());
                                alarmInventory.setTranstatus("0");
                                update(alarmInventory);
                            }
                        }
                    } catch (Exception exh) {
                        logger.error("oilalarm 2001" + exh.getMessage());
                    }
                    //endregion

                    //region 高液位预警 2002
                    try {
                        if (data_out_t.fTotalHeight <= alarmParam.getHighalarm() && data_out_t.fTotalHeight >= alarmParam.getHighprealarm()) {
                            AlarmInventory alarmInventory = getAlarmInventory(alarmParam.getOilcan()
                                    , 2002, alarmInventotyList);
                            logger.info("alarmParam[2002] come in");

                            //返回对象，要判断是不是空
                            if (alarmInventory == null) {
                                //TODO 当前油罐没有报警 进行插入
                                AlarmInventory alarmInventory2 = new AlarmInventory();
                                alarmInventory2.setOilcan(alarmParam.getOilcan());
                                alarmInventory2.setAlarmtype(2002);
                                alarmInventory2.setAlarmdesc("高液位预警");
                                alarmInventory2.setStarttime(new Date());
                                alarmInventory2.setTranstatus("0");
                                insert(alarmInventory2);
                            }
                        } else {
                            AlarmInventory alarmInventory = getAlarmInventory(alarmParam.getOilcan()
                                    , 2002, alarmInventotyList);
                            if (alarmInventory != null) {
                                //TODO 做更新操作
                                logger.info("update alarmParam[2002] come in");

                                alarmInventory.setEndtime(new Date());
                                alarmInventory.setTranstatus("0");
                                update(alarmInventory);
                            }
                        }
                    } catch (Exception exh) {
                        logger.error("oilalarm 2002" + exh.getMessage());
                    }
                    //endregion


                    //region低液位预警 2003  lowprealarm     低液位预警<= 设置参数 >=低液位报警
                    try {
                        if (data_out_t.fTotalHeight <= alarmParam.getLowprealarm() && data_out_t.fTotalHeight >= alarmParam.getLowalarm()) {
                            logger.info("alarmParam[2003] come in");
                            AlarmInventory alarmInventory = getAlarmInventory(alarmParam.getOilcan()
                                    , 2003, alarmInventotyList);
                            //返回对象，要判断是不是空
                            if (alarmInventory == null) {
                                //TODO 当前油罐没有报警 进行插入
                                AlarmInventory alarmInventory0 = new AlarmInventory();
                                alarmInventory0.setOilcan(alarmParam.getOilcan());
                                alarmInventory0.setAlarmtype(2003);
                                alarmInventory0.setAlarmdesc("低液位预警");
                                alarmInventory0.setStarttime(new Date());
                                alarmInventory0.setTranstatus("0");
                                insert(alarmInventory0);
                            }
                        } else {
                            logger.info("alarmParam[2003] ok ..update");

                            AlarmInventory alarmInventory = getAlarmInventory(alarmParam.getOilcan()
                                    , 2003, alarmInventotyList);
                            if (alarmInventory != null) {
                                //TODO 做更新操作
                                logger.info("update alarmParam[2003] come in");

                                alarmInventory.setEndtime(new Date());
                                alarmInventory.setTranstatus("0");
                                update(alarmInventory);
                            }
                        }
                    } catch (Exception exh) {
                        logger.error("oilalarm 2003" + exh.getMessage());
                    }
                    //endregion


                    //region 低液位报警 2004
                    try {
                        if (data_out_t.fTotalHeight <= alarmParam.getLowalarm()) {
                            AlarmInventory alarmInventory = getAlarmInventory(alarmParam.getOilcan()
                                    , 2004, alarmInventotyList);
                            logger.info("alarmParam[2004] come in");

                            //返回对象，要判断是不是空
                            if (alarmInventory == null) {
                                //TODO 当前油罐没有报警 进行插入
                                AlarmInventory alarmInventory1 = new AlarmInventory();
                                alarmInventory1.setOilcan(alarmParam.getOilcan());
                                alarmInventory1.setAlarmtype(2004);
                                alarmInventory1.setAlarmdesc("低液位报警");
                                alarmInventory1.setStarttime(new Date());
                                alarmInventory1.setTranstatus("0");
                                insert(alarmInventory1);
                            }
                        } else {
                            AlarmInventory alarmInventory = getAlarmInventory(alarmParam.getOilcan()
                                    , 2004, alarmInventotyList);
                            if (alarmInventory != null) {
                                //TODO 做更新操作
                                logger.info("update alarmParam[2004] come in");
                                alarmInventory.setEndtime(new Date());
                                alarmInventory.setTranstatus("0");
                                update(alarmInventory);
                            }
                        }
                    } catch (Exception exh) {
                        logger.error("oilalarm 2004" + exh.getMessage());
                    }
                    //endregion

                    //region 高水位预警 2005
                    try {
                        if (data_out_t.fWaterHeight >= alarmParam.getWateralarm() && data_out_t.fWaterHeight <= alarmParam.getWaterhightalarm()) {
                            AlarmInventory alarmInventory = getAlarmInventory(alarmParam.getOilcan()
                                    , 2005, alarmInventotyList);
                            logger.info("alarmParam[2005] come in");

                            //返回对象，要判断是不是空
                            if (alarmInventory == null) {
                                //TODO 当前油罐没有报警 进行插入
                                AlarmInventory alarmInventory6 = new AlarmInventory();
                                alarmInventory6.setOilcan(alarmParam.getOilcan());
                                alarmInventory6.setAlarmtype(2005);
                                alarmInventory6.setAlarmdesc("高水位预警");
                                alarmInventory6.setStarttime(new Date());
                                alarmInventory6.setTranstatus("0");
                                insert(alarmInventory6);
                            }
                        } else {
                            AlarmInventory alarmInventory = getAlarmInventory(alarmParam.getOilcan()
                                    , 2005, alarmInventotyList);
                            if (alarmInventory != null) {
                                //TODO 做更新操作
                                logger.info("update alarmParam[2005] come in");

                                alarmInventory.setEndtime(new Date());
                                alarmInventory.setTranstatus("0");
                                update(alarmInventory);
                            }
                        }
                    } catch (Exception exh) {
                        logger.error("oilalarm 2005" + exh.getMessage());
                    }
                    //endregion

                    //region 高水位报警 2006
                    try {

                        if (data_out_t.fWaterHeight >= alarmParam.getWaterhightalarm()) {
                            AlarmInventory alarmInventory = getAlarmInventory(alarmParam.getOilcan()
                                    , 2006, alarmInventotyList);
                            logger.info("alarmParam[2006] come in");

                            //返回对象，要判断是不是空
                            if (alarmInventory == null) {
                                //TODO 当前油罐没有报警 进行插入
                                AlarmInventory alarmInventory6 = new AlarmInventory();
                                alarmInventory6.setOilcan(alarmParam.getOilcan());
                                alarmInventory6.setAlarmtype(2006);
                                alarmInventory6.setAlarmdesc("高水位报警");
                                alarmInventory6.setStarttime(new Date());
                                alarmInventory6.setTranstatus("0");
                                insert(alarmInventory6);
                            }
                        } else {
                            AlarmInventory alarmInventory = getAlarmInventory(alarmParam.getOilcan()
                                    , 2006, alarmInventotyList);
                            if (alarmInventory != null) {
                                //TODO 做更新操作
                                logger.info("update alarmParam[2006] come in");

                                alarmInventory.setEndtime(new Date());
                                alarmInventory.setTranstatus("0");
                                update(alarmInventory);
                            }
                        }
                    } catch (Exception exh) {
                        logger.error("oilalarm 2006" + exh.getMessage());
                    }
                    //endregion

                    //region高温报警 2007
                    try {
                        if (data_out_t.fOilTemp >= alarmParam.getHightempalarm()) {
                            AlarmInventory alarmInventory = getAlarmInventory(alarmParam.getOilcan()
                                    , 2007, alarmInventotyList);
                            logger.info("alarmParam[2007] come in");

                            //返回对象，要判断是不是空
                            if (alarmInventory == null) {
                                //TODO 当前油罐没有报警 进行插入
                                AlarmInventory alarmInventory4 = new AlarmInventory();
                                alarmInventory4.setOilcan(alarmParam.getOilcan());
                                alarmInventory4.setAlarmtype(2007);
                                alarmInventory4.setAlarmdesc("高温报警");
                                alarmInventory4.setStarttime(new Date());
                                alarmInventory4.setTranstatus("0");
                                insert(alarmInventory4);
                            }
                        } else {
                            AlarmInventory alarmInventory = getAlarmInventory(alarmParam.getOilcan()
                                    , 2007, alarmInventotyList);
                            if (alarmInventory != null) {
                                //TODO 做更新操作
                                logger.info("update alarmParam[2007] come in");

                                alarmInventory.setEndtime(new Date());
                                alarmInventory.setTranstatus("0");
                                update(alarmInventory);
                            }
                        }
                    } catch (Exception exh) {
                        logger.error("oilalarm 2007" + exh.getMessage());
                    }
                    //endregion

                    //region 低温报警 2008
                    try {
                        if (data_out_t.fOilTemp <= alarmParam.getLowtempalarm()) {
                            AlarmInventory alarmInventory = getAlarmInventory(alarmParam.getOilcan()
                                    , 2008, alarmInventotyList);
                            logger.info("alarmParam[2008] come in");

                            //返回对象，要判断是不是空
                            if (alarmInventory == null) {
                                //TODO 当前油罐没有报警 进行插入
                                AlarmInventory alarmInventory5 = new AlarmInventory();
                                alarmInventory5.setOilcan(alarmParam.getOilcan());
                                alarmInventory5.setAlarmtype(2008);
                                alarmInventory5.setAlarmdesc("低温报警");
                                alarmInventory5.setStarttime(new Date());
                                alarmInventory5.setTranstatus("0");
                                insert(alarmInventory5);
                            }
                        } else {
                            AlarmInventory alarmInventory = getAlarmInventory(alarmParam.getOilcan()
                                    , 2008, alarmInventotyList);
                            if (alarmInventory != null) {
                                //TODO 做更新操作
                                logger.info("update alarmParam[2008] come in");

                                alarmInventory.setEndtime(new Date());
                                alarmInventory.setTranstatus("0");
                                update(alarmInventory);
                            }

                        }
                    } catch (Exception exh) {
                        logger.error("oilalarm 2008" + exh.getMessage());
                    }
                    //endregion

                }

            }

        }
        //endregion

    }

    /**
     * 开启报警
     *
     * @param alarmInventory
     */
    public void insert(AlarmInventory alarmInventory) {
        alarmInventoryDao = Context.getInstance().getBean(AlarmInventoryDao.class);
        int i = alarmInventoryDao.insert(alarmInventory);
    }

    /**
     * 结束报警
     *
     * @param alarmInventory
     */
    public void update(AlarmInventory alarmInventory) {
        alarmInventoryDao = Context.getInstance().getBean(AlarmInventoryDao.class);
        alarmInventoryDao.updateEndTime(alarmInventory);

    }


    /**
     * @param oilCanNo  油罐号
     * @param alarmList 预报警设置集合
     * @return 油罐的预报警设置
     */
    public static SysManageAlarmParameter getAlarmParameter(int oilCanNo, List<SysManageAlarmParameter> alarmList) {
        logger.info("getAlarmParameter-------------begin" + oilCanNo);

        for (SysManageAlarmParameter alarmParameter : alarmList) {
            logger.info("to : alarmParameter : " + alarmParameter.getOilcan());
            if (alarmParameter.getOilcan() == oilCanNo) {
                logger.info("return :");
                return alarmParameter;
            }
        }
        return null;
    }

    /**
     * @param oilCanNo           油罐号
     * @param alarmType          报警类型
     * @param alarmInventotyList 正在报警的集合
     * @return 返回报警对象
     */
    public static AlarmInventory getAlarmInventory(int oilCanNo, int alarmType, List<AlarmInventory> alarmInventotyList) {
        logger.info("come in getAlarmInventory [oilcanno]" + oilCanNo);
        logger.info("come in getAlarmInventory [alarmType]" + alarmType);


        for (AlarmInventory alarmInventory : alarmInventotyList) {
            ////System.out.println("---oilcanno------" + alarmInventory.getOilcan());
            ////System.out.println("---alarmType------" + alarmInventory.getAlarmtype());

            //油罐号&&报警类型
            if (alarmInventory.getOilcan() == oilCanNo && alarmInventory.getAlarmtype() == alarmType) {
                logger.info("-alarmInventory-:" + alarmInventory.getOilcan());
                logger.info("-alarmInventory-:" + alarmInventory.getAlarmtype());
                logger.info("-oilCanNo-:" + oilCanNo);
                logger.info("-alarmType-:" + alarmType);

                return alarmInventory;
            }
        }
        return null;
    }
}
