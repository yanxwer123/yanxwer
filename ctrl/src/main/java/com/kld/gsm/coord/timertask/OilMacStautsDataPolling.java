package com.kld.gsm.coord.timertask;

import com.kld.gsm.ATG.dao.SysManageDictDao;
import com.kld.gsm.ATG.domain.AlarmGaTContrast;
import com.kld.gsm.ATGDevice.ATGManager;
import com.kld.gsm.ATGDevice.atg_stock_data_out_t;
import com.kld.gsm.MacLog.GunStatusEnum;
import com.kld.gsm.MacLog.MacLogInfo;
import com.kld.gsm.MacLog.util.EhCacheHelper;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.servcie.IOilMacStautsDataService;
import net.sf.ehcache.Element;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/23 15:49
 * @Description:油机状态数据
 */
public class OilMacStautsDataPolling extends Thread {

    Logger logger = Logger.getLogger(OilMacStautsDataPolling.class);
    //枪出罐出对比
    static List<AlarmGaTContrast> alarmGaTContrastList = new ArrayList<AlarmGaTContrast>();
    static List<Integer> oilCanNoList = null;
    //油罐轮询计数器
    static Map<Integer,Integer> oilCanCountMap = new HashMap<Integer,Integer>();
    static int maxCount = 0;

    @Override
    public void run() {
        RuntimeMXBean rt = ManagementFactory.getRuntimeMXBean();
        String pid = rt.getName();
        MDC.put("PID", pid);
        while(true) {
            try {
                logger.error("枪出罐出进入休眠--");
                sleep(TimeTaskPar.get("qcgcdbsjjg") * 1000);
            } catch (InterruptedException e) {
                logger.error("sleep:" + e);
                e.printStackTrace();
            }
            try {
                logger.error("get in 枪出罐出对比计划任务");
                IOilMacStautsDataService oilMacStautsDataService = Context.getInstance().getBean(IOilMacStautsDataService.class);
                //初始化油罐，获取到油罐数量
                if (oilCanNoList == null || oilCanNoList.size()==0) {
                    oilCanNoList = oilMacStautsDataService.selectAllOilCanNos();
                    logger.info("枪出罐出（1）初始化油罐:" + oilCanNoList.size());

                }
                if (oilCanCountMap == null || oilCanCountMap.size() == 0) {
                    //初始化油罐计数器为0，计算是否全部挂枪
                    for (int i = 0; i < oilCanNoList.size(); i++) {
                        oilCanCountMap.put(oilCanNoList.get(i), 0);
                        //初始化枪出罐出对比表保存列表List，与罐数量等同
                        AlarmGaTContrast alarmGaTContrast = new AlarmGaTContrast();
                        alarmGaTContrast.setOilcan(oilCanNoList.get(i));
                        alarmGaTContrastList.add(alarmGaTContrast);
                    }
                    logger.info("枪出罐出（2）初始化计数器:" + oilCanCountMap.size());
                }
                //查询轮询次数，挂枪间隔时间设置
                //SysManageDictDao sysManageDictDao = Context.getInstance().getBean(SysManageDictDao.class);
                maxCount = TimeTaskPar.get("dtywycsjjg");//Integer.parseInt(sysManageDictDao.selectByCode("qcgcdblxcs").getValue());
                logger.info("枪出罐出（3）定时器取值:"+maxCount);

                //在全部挂枪的情况下，计数器累加到maxCount才需要保存值
                //在提枪时，计数器清0，第一次全部挂枪，则需要取一次罐存数
//轮询计数器+1
                /*for (int i = 0; i < oilCanNoList.size(); i++) {
                    oilCanCountMap.put(oilCanNoList.get(i), oilCanCountMap.get(oilCanNoList.get(i)) + 1);
                    logger.info("枪出罐出（4）罐号：" + oilCanNoList.get(i) + "计数器+1:" + oilCanCountMap.get(oilCanNoList.get(i)));
                }
                //1、如果计数器大于1200，则查询该罐的实时库存
                    if (oilCanCountMap.get(oilCanNoList.get(i)) > maxCount) {
                        stockInList.add(oilCanNoList.get(i));//查询该罐的实时库存
                        oilCanCountMap.put(oilCanNoList.get(i), 0);//清空该罐计数器
                        logger.info("枪出罐出（5）计数器超过设定值：" + oilCanNoList.get(i).toString() + "罐数量" + stockInList.size());
                    }
                */

                //取得液位仪实时库存的传入参数
                //根据油罐轮询枪状态，查看枪状态

                List<Integer> stockInList = new ArrayList<Integer>();
                for (int i = 0; i < oilCanCountMap.size(); i++) {
                    //1、根据罐号取得油枪编号
                    List<Integer> gunList = oilMacStautsDataService.selectOilGunByOilCanNo(oilCanNoList.get(i));
                    boolean isCloseGun = true;//是否挂枪
                    //region "判断是否全部挂枪"
                    for (int j = 0; j < gunList.size(); j++) {
                        //如果该罐的所有油枪都是挂枪状态，则查询该罐的实时库存，否则是否挂枪为false,跳出循环。
                        try {
                            Element element = EhCacheHelper.getOilGunStatus(gunList.get(j).byteValue());
                            if (element != null) {
                                MacLogInfo macLogInfo = (MacLogInfo) element.getObjectValue();
                                if (macLogInfo != null) {
                                    logger.info("枪出罐出（4）枪状态:" + macLogInfo.GunStatus);
                                    //空闲(10),卡插入(0),挂枪(1),提枪(2),下班(3),脱机(4);
                                    //如果时提枪状态，则对应罐的计数器清0
                                    if (macLogInfo.GunStatus == GunStatusEnum.提枪) {
                                        oilCanCountMap.put(oilCanNoList.get(i), -2);//清空该罐计数器
                                        isCloseGun = false;
                                        break;
                                    }
                                } else {
                                    logger.info("there is no macloginfo.gunstatus in ehcache");
                                }
                            } else {
                                logger.info("ERROR枪出罐出（5）无枪状态缓存");
                            }
                        } catch (Exception ex1) {

                            logger.info("ERROR-ERROR枪出罐出（6）无枪状态缓存");
                            logger.info(ex1);
                        }
                    }
                    //endregion
                    //region ”处理全部挂枪状态“
                    if (isCloseGun) {
                        //挂枪计数器启动累加机制
                        oilCanCountMap.put(oilCanNoList.get(i), oilCanCountMap.get(oilCanNoList.get(i)) + 1);
                        //首次全部挂枪，计数器从0变1时，取一次该罐的实时罐存
                        if (oilCanCountMap.get(oilCanNoList.get(i)) ==1)
                        {
                            stockInList.add(oilCanNoList.get(i));//查询该罐的实时库存
                            logger.info("枪出罐出（7）首次挂枪后保存一次实时罐存：" + oilCanNoList.get(i).toString() + "罐数量" + stockInList.size());
                        }
                        //计数器超过设定值时，去一次该罐的实时罐存，同时计数器改为从1开始计数
                        if (oilCanCountMap.get(oilCanNoList.get(i)) > maxCount) {
                            stockInList.add(oilCanNoList.get(i));//查询该罐的实时库存
                            oilCanCountMap.put(oilCanNoList.get(i), 1);//挂枪间隔时间很长，保存实时罐存后，计数器重新从1开始计数
                            logger.info("枪出罐出（8）计数器超过设定值：" + oilCanNoList.get(i).toString() + "罐数量" + stockInList.size());
                        }
                        logger.info("枪出罐出（9）计数器值" + oilCanCountMap.get(oilCanNoList.get(i)));
                    }
                    //endregion
                }
                //region "去重复"
                logger.info("枪出罐出（10）待处理数量" + stockInList.size());
                //如果传入参数为空则返回，等待下一次轮询。
                if (stockInList == null || stockInList.size() <= 0) {
                    logger.info("如果传入参数为空则返回，等待下一次轮询...");
                    //sleep(TimeTaskPar.get("qcgcdbsjjg")*1000);
                    continue;
                }
                this.removeDuplicate(stockInList);//给传入参数去重
                logger.info("枪出罐出（11）去重:" + stockInList.size());
                List<atg_stock_data_out_t> stockDataOutList = ATGManager.getStock(stockInList);
                logger.info("stockDataOutList.size()"+stockDataOutList.size());
                //endregion
                //region "启动保存"
                try {
                    logger.info("枪出罐出（12）开始保存静态库存油罐和静态罐存油枪...");
                    //保存静态库存油罐和静态罐存油枪
                    for (AlarmGaTContrast alarmGaTContrast : alarmGaTContrastList) {
                        logger.info("枪出罐出（13）待保存数据:" + alarmGaTContrast);
                    }
                    oilMacStautsDataService.OilMacStautsDataSave(stockDataOutList, alarmGaTContrastList);
                } catch (Exception e) {
                    logger.error(e);
                    e.printStackTrace();
                }
                //endregion
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error("ERROR-ERROR枪出罐出（13.1）枪出罐出对比错误+OilMacStautsDataPolling:" + ex.getMessage());
            }

        }
    }


    public static void removeDuplicate(List list)   {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
    }
    public static void main(String[] args){
        /*Map<Integer,Integer> map = new HashMap<Integer,Integer>();
        List list = new ArrayList();
        list.add(1);
        map.put((Integer) list.get(0), 0);
        for(int i=0;i<10;i++) {
            map.put((Integer)list.get(0), map.get(list.get(0))+1);
            ////System.out.println(map.get(list.get(0)));
        }*/
//        OilMacStautsDataPolling o = new OilMacStautsDataPolling();
//        ////System.out.println(o.formatGunNo(1));
    }
}
