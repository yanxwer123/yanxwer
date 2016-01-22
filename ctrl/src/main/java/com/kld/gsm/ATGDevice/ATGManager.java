package com.kld.gsm.ATGDevice;

import com.kld.gsm.ATG.dao.SysManageCubageDao;
import com.kld.gsm.ATG.dao.SysManageCubageInfoDao;
import com.kld.gsm.ATG.domain.SysManageCubage;
import com.kld.gsm.ATG.domain.SysManageCubageInfo;
import com.kld.gsm.ATG.domain.SysManageCubageInfoKey;
import com.kld.gsm.MacLog.util.EhCacheHelper;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.servcie.ISaleOutAlarmService;
import com.kld.gsm.coord.timertask.TimeTaskPar;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by luyan on 15/10/21.
 */
public class ATGManager {

    static Logger logger = Logger.getLogger(ATGManager.class);
    static ISaleOutAlarmService saleOutAlarmService = Context.getInstance().getBean(ISaleOutAlarmService.class);
    static int is_init = 1;//是否已经初始化液位仪，初始化成功以后改为0

    /**
     * 加载配置信息
     *
     * @param inputData
     * @return 返回值 0：成功，!0：失败
     */
    public static int init(atg_init_in_t inputData) {
        ///smc20/gsm/logs/ATG/
        File file = new File(inputData.strLogPath);
        if(!file.exists()){
            logger.info("如果日志路径不存在，则创建");
            file.mkdirs();
        }
        is_init = ATGDevice.init(inputData);
        return is_init;

    }

    /**
     * 探棒校正参数设置
     *
     * @param inputData
     * @return 返回值 0：成功，!0：失败
     */
    public static int setCorrection(List<atg_correction_data_in_t> inputData) {
        if(is_init!=0) {
            return 1;
        }
        return ATGDevice.setCorrection(inputData);
    }

    /**
     * 探棒油罐配置
     *
     * @param inputData
     * @return 返回值 0：成功，!0：失败
     */
    public static int setProbe(List<atg_probecan_data_in_t> inputData) {
        if(is_init!=0) {
            return 1;
        }
        return ATGDevice.setProbe(inputData);
    }

    /**
     * 使用缓存缓存全部库存.
     *
     * @return
     */
    private static List<atg_stock_data_out_t> getAllStock() {
        if(is_init!=0) {
            return null;
        }
        //查询到所有油罐编号
        List<Integer> oilCanNo = saleOutAlarmService.selectAllOilCanNos();
        logger.info("获取所有油罐数量:" + oilCanNo.size());
        List<atg_stock_data_out_t> stockDataOutTList = ATGDevice.getStock(oilCanNo);
        List<atg_stock_data_out_t> stockRet = new ArrayList<atg_stock_data_out_t>();
        logger.info("获取罐存数量:" + stockDataOutTList.size());
        try {
            //高升转换传入参数
            List<atg_hightoliter_in_t> hightoliterInTList = new ArrayList<atg_hightoliter_in_t>();

            for (int i = 0; i < oilCanNo.size(); i++) {
                boolean isfind = false;
                atg_stock_data_out_t stockDataOutT = null;
                for (int j = 0; j < stockDataOutTList.size(); j++) {
                    stockDataOutT = stockDataOutTList.get(j);
                    if (oilCanNo.get(i) == stockDataOutT.uOilCanNo) {
                        isfind = true;
                        break;
                    }
                }
                //如果实时库存返回的罐号和传入的不相等，则跳过，继续下一条记录
                if (!isfind) {
                    continue;
                }
                atg_hightoliter_in_t hightoliterInT = new atg_hightoliter_in_t();
                hightoliterInT.fTotalHeight = stockDataOutT.fTotalHeight;
                hightoliterInT.fWaterHeight = stockDataOutT.fWaterHeight;
                hightoliterInT.fOilTemp = stockDataOutT.fOilTemp;
                hightoliterInT.fOilTemp1 = stockDataOutT.fOilTemp1;
                hightoliterInT.fOilTemp2 = stockDataOutT.fOilTemp2;
                hightoliterInT.fOilTemp3 = stockDataOutT.fOilTemp3;
                hightoliterInT.fOilTemp4 = stockDataOutT.fOilTemp4;
                hightoliterInT.fOilTemp5 = stockDataOutT.fOilTemp5;
                //容积表
                List<atg_capacity_data_in_t> capacityDataInTList = new ArrayList<atg_capacity_data_in_t>();
                atg_capacity_data_in_t capacityDataInT = new atg_capacity_data_in_t();
                //容积表dao，查询容积表信息
                SysManageCubageDao sysManageCubageDao = Context.getInstance().getBean(SysManageCubageDao.class);
                List<SysManageCubage> sysManageCubageList = sysManageCubageDao.selectByKey(stockDataOutT.uOilCanNo);
                //如果容积表是空，则把高升转换的值都赋值为0.0
                if (sysManageCubageList == null || sysManageCubageList.size() == 0) {
                    logger.warn("sysManageCubageList is null stockDataOutT.uOilCanNo:" + stockDataOutT.uOilCanNo);
                    stockDataOutT.fOilCubage = 0.0;//净油体积
                    stockDataOutT.fOilStandCubage = 0.0;//标准体积
                    stockDataOutT.fEmptyCubage = 0.0;//空体积
                    stockDataOutT.fWaterBulk = 0.0;//水体积
                    stockRet.add(stockDataOutT);
                    continue;
                }
                //容积表明细dao,查询容积表明细信息
                SysManageCubageInfoDao sysManageCubageInfoDao = Context.getInstance().getBean(SysManageCubageInfoDao.class);
                capacityDataInT.uOilCanNO = stockDataOutT.uOilCanNo;
                //容积表明细
                List<atg_capacitytable_data_in_t> capacitytableDataInTList = new ArrayList<atg_capacitytable_data_in_t>();
                SysManageCubageInfoKey sysManageCubageInfokey = new SysManageCubageInfoKey();
                sysManageCubageInfokey.setOilcan(stockDataOutT.uOilCanNo);
                logger.info("sysManageCubageList:" + sysManageCubageList.size());
                logger.info("sysManageCubageList.get(0).getVersion():" + sysManageCubageList.get(0).getVersion());
                sysManageCubageInfokey.setVersion(sysManageCubageList.get(0).getVersion());
                //查询容积表明细
                //如果缓存中的容积表为空则从数据库查询，并缓存
                if (EhCacheHelper.getCubageInfo(stockDataOutT.uOilCanNo) == null) {
                    List<SysManageCubageInfo> sysManageCubageInfoList = sysManageCubageInfoDao.selectCubageInfo(sysManageCubageInfokey);
                    logger.info("从数据库取得容积表sysManageCubageInfoList.size():" + sysManageCubageInfoList.size());
                    for (SysManageCubageInfo sysManageCubageInfo : sysManageCubageInfoList) {
                        atg_capacitytable_data_in_t capacitytableDataInT = new atg_capacitytable_data_in_t();
                        capacitytableDataInT.uHigh = (int) (double) sysManageCubageInfo.getHeight();
                        capacitytableDataInT.fLiter = sysManageCubageInfo.getLiter();
                        capacitytableDataInTList.add(capacitytableDataInT);
                    }
                    //写入缓存
                    EhCacheHelper.updateCubageInfo(sysManageCubageInfoList);
                } else {
                    //从缓存中取得容积表
                    List<SysManageCubageInfo> sysManageCubageInfoList = EhCacheHelper.getCubageInfo(stockDataOutT.uOilCanNo);
                    logger.info("从缓存中取得容积表sysManageCubageInfoList.size():" + sysManageCubageInfoList.size());
                    for (SysManageCubageInfo sysManageCubageInfo : sysManageCubageInfoList) {
                        atg_capacitytable_data_in_t capacitytableDataInT = new atg_capacitytable_data_in_t();
                        capacitytableDataInT.uHigh = (int) (double) sysManageCubageInfo.getHeight();
                        capacitytableDataInT.fLiter = sysManageCubageInfo.getLiter();
                        capacitytableDataInTList.add(capacitytableDataInT);
                    }
                }
                capacityDataInT.uCapacitySize = capacitytableDataInTList.size();//容积表明细长度
                capacityDataInT.pCapacityTableData = capacitytableDataInTList;//容积表明细
                capacityDataInTList.add(capacityDataInT);//容积表明细放到容积表

                hightoliterInT.pCapacityData = capacityDataInTList;//容积表
                hightoliterInT.uCount = capacityDataInTList.size();//容积表长度
                logger.info("容积表长度hightoliterInT.uCount:" + hightoliterInT.uCount);
                hightoliterInTList.add(hightoliterInT);
                //根据容积表取得高升转换
                logger.info("开始根据容积表取得高升转换...");
                List<atg_hightoliter_data_out_t> ret = ATGDevice.HightOLiter(hightoliterInTList);
                logger.info("ret.size():" + ret.size());
                //遍历高升转换
                for (atg_hightoliter_data_out_t hightoliterDataOutT : ret) {
                    //如果实时库存和高升转换的罐号相等，从高升转换给实时库存赋值。
                    if (stockDataOutT.uOilCanNo == hightoliterDataOutT.uOilCanNo) {
                        stockDataOutT.fOilCubage = hightoliterDataOutT.fOilCubage;//净油体积
                        stockDataOutT.fOilStandCubage = hightoliterDataOutT.fOilStandCubage;//标准体积
                        stockDataOutT.fEmptyCubage = hightoliterDataOutT.fEmptyCubage;//空体积
                        stockDataOutT.fWaterBulk = hightoliterDataOutT.fWaterBulk;//水体积
                    }
                }
                capacitytableDataInTList.clear();
                hightoliterInTList.clear();
                stockRet.add(stockDataOutT);
            }
        } catch (Exception e) {
            logger.error("高升转换error:" + e);
            e.printStackTrace();
        }
        return stockRet;

    }

    public static List<atg_stock_data_out_t> getStock() {
        if(is_init!=0) {
            return null;
        }
        return getStock(new ArrayList<Integer>());
    }

    public static boolean getStockByThread(){
        List<atg_stock_data_out_t> outList = null;
        try{
            logger.info("从液位仪获取油水体积...");
            outList = ATGManager.getAllStock();
            EhCacheHelper.updateCanStocks(outList);
        }catch (Exception e){
            logger.info("从液位仪获取油水体积异常"+e.getMessage());
           return false;
        }
        return true;
    }

    /**
     * 实时库存采集
     * 如果查询所有罐.则传入一个空的ArrayList &lt; int 对象即可
     * @param oilCanNo
     * @return
     */
    public static List<atg_stock_data_out_t> getStock(List<Integer> oilCanNo) {
        if(is_init!=0) {
            logger.info("液位仪初始化失败");
            return null;
        }
        List<atg_stock_data_out_t> outList = null;
        try {
            Element el = EhCacheHelper.getAllCanStock();
            if (el == null) {
                //System.out.println("从液位仪获取油水体积");
                logger.info("从液位仪获取油水体积...");
                outList = ATGManager.getAllStock();
                EhCacheHelper.updateCanStocks(outList);
            } else {
                //System.out.println("从缓存获取油水体积");
                logger.info("从缓存获取油水体积...");
                outList = (List<atg_stock_data_out_t>) el.getValue();
                //System.out.println("从缓存中获取到体积列表:"+outList.size());
                logger.info("从缓存中获取到体积列表:"+outList.size());
            }
            List<atg_stock_data_out_t> rlist = new ArrayList<atg_stock_data_out_t>();
            if (oilCanNo != null&&oilCanNo.size()>0) {
                for (atg_stock_data_out_t atg : outList) {
                    if (oilCanNo.contains(atg.uOilCanNo)) {
                        rlist.add(atg);
                    }
                }
                //System.out.println("筛选出体积对象(个):"+rlist.size());
                logger.info("筛选出体积对象(个):"+rlist.size());
                return rlist;
            } else {
                //System.out.println("返回全部体积对象列表");
                logger.info("返回全部体积对象列表");
                return outList;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("获取油水体积失败:" + ex.getMessage(), ex);
        }
        return new ArrayList<atg_stock_data_out_t>();
    }


    /**
     * 整点库存采集
     *
     * @param timeStockData
     * @return
     */
    public static List<atg_stock_data_out_t> getTimeStock(List<atg_timestock_data_in_t> timeStockData) {
        if(is_init!=0) {
            return null;
        }
        return ATGDevice.getTimeStock(timeStockData);
    }

    /**
     * 进油信息采集
     *
     * @param oilInData
     * @return
     */
    public static List<atg_oilin_data_out_t> getOilIn(List<atg_oilin_data_in_t> oilInData) {
        if(is_init!=0) {
            return null;
        }
        return ATGDevice.getOilIn(oilInData);
    }

    /**
     * 油罐报警采集
     *
     * @param alarmData
     * @return
     */
    public static List<atg_alarm_data_out_t> getAlarm(List<atg_alarm_data_in_t> alarmData) {
        if(is_init!=0) {
            return null;
        }
        return ATGDevice.getAlarm(alarmData);
    }

    /**
     * 设备故障采集
     *
     * @param failureData
     * @return
     */
    public static List<atg_failure_data_out_t> getFailure(List<atg_failure_data_in_t> failureData) {
        if(is_init!=0) {
            return null;
        }
        return ATGDevice.getFailure(failureData);
    }

    /**
     * 液位仪对时
     *
     * @param date 管控机时间，格式：YYYYMMDDHHMMSS
     * @return 成功：""，失败：错误描述。
     */
    public static String checkTime(String date) {
        if(is_init!=0) {
            return "1";
        }
        return ATGDevice.checkTime(date);
    }

    /**
     * 预报警设置
     *
     * @param setAlarmData
     * @return 成功：""，失败：错误描述。
     */
    public static String alarmSetter(List<atg_setalarm_data_in_t> setAlarmData) {
        if(is_init!=0) {
            return "1";
        }
        return ATGDevice.alarmSetter(setAlarmData);
    }

    /**
     * 油罐油品变类
     *
     * @param chgOilInfoData
     * @return 成功：""，失败：错误描述。
     */
    public static String chgOilInfo(List<atg_chgoilinfo_data_in_t> chgOilInfoData) {
        if(is_init!=0) {
            return "1";
        }
        return ATGDevice.chgOilInfo(chgOilInfoData);
    }

    /**
     * 容积表(全罐表)上传
     *
     * @param data
     * @return
     */
    public static List<atg_capacity_data_in_t> getCapacityTable(List<Integer> data) {
        if(is_init!=0) {
            return null;
        }
        return ATGDevice.getCapacityTable(data);
    }

    /**
     * 容积表(全罐表)下发
     *
     * @param data
     * @return 成功：""，失败：错误描述。
     */
    public static String setCapacityTable(atg_capacity_data_in_t data) {
        if(is_init!=0) {
            return "1";
        }
        return ATGDevice.setCapacityTable(data);
    }

    /**
     * 启动静态液位异常测试
     *
     * @param data
     * @return
     */
    public static String startLiquid(List<atg_startliquid_data_in_t> data) {
        if(is_init!=0) {
            return "1";
        }
        return ATGDevice.startLiquid(data);
    }

    /**
     * 停止静态液位异常测试
     *
     * @param data
     * @return
     */
    public static List<atg_liquidreport_data_out_t> stopLiquid(List<atg_stopliquid_data_in_t> data) {
        if(is_init!=0) {
            return null;
        }
        return ATGDevice.stopLiquid(data);
    }

    /**
     * 静态液位异常测试报告
     *
     * @param data
     * @return
     */
    public static List<atg_liquidreport_data_out_t> liquidReport(List<atg_liquidreport_data_in_t> data) {
        if(is_init!=0) {
            return null;
        }
        return ATGDevice.liquidReport(data);
    }

    /**
     * 设备基础信息
     *
     * @param data
     * @return
     */
    public static atg_device_out_t getDeviceInfo(List<Integer> data) {
        if(is_init!=0) {
            logger.error("液位仪未初始化");
            return null;
        }
        atg_device_out_t device_out_t=ATGDevice.getDeviceInfo(data);
        if (device_out_t!=null) {
            logger.info("返回数据：" + device_out_t.toString());
        }
        return device_out_t;
    }

    /**
     * 高升转换
     *
     * @param data
     * @return
     */
    public static List<atg_hightoliter_data_out_t> HightOLiter(List<atg_hightoliter_in_t> data) {
        if(is_init!=0) {
            return null;
        }
        return ATGDevice.HightOLiter(data);
    }

    /**
     * 液位仪开关机记录
     *
     * @param data
     * @return
     */
    public static List<atg_powerrecord_data_out_t> getPowerRecord(List<atg_powerrecord_in_t> data) {
        if(is_init!=0) {
            return null;
        }
        return ATGDevice.getPowerRecord(data);
    }

    /**
     * 释放所占用的资源，断开与相关设备的连接，并清理连接信息
     *
     * @return 成功：0。
     */
    public static int clear() {
        return ATGDevice.clear();
    }

    public static int atg_param(String strOpeCode, Object inputdata) {
        return ATGDevice.atg_param(strOpeCode, inputdata);
    }

    public static int atg_init(atg_init_in_t input) {
        return ATGDevice.atg_init(input);
    }

    public static int testList(ArrayList<atg_init_in_t> list) {
        return ATGDevice.testList(list);
    }

    public static ArrayList<atg_init_in_t> getfromc(int count) {
        return ATGDevice.getfromc(count);
    }

    /**
     * 检查输出是否正确
     *
     * @param ret
     * @return
     */
    public static String checkReturnData(Object ret) {
        if (ret instanceof Integer) {//如果返回值是Integer
            if ((Integer) ret == 0) {//0，则表示本地方法调用成功
                return getErrorMeg("1000");
            } else if ((Integer) ret == 1) {//1，则表示本地方法调用失败
                return getErrorMeg("1001");
            } else if ((Integer) ret == 2) {//2，则表示传入值为空
                return getErrorMeg("1002");
            } else {//不是以上，则表示未知异常
                return getErrorMeg("4001");
            }
        } else if (ret instanceof ArrayList) {//如果返回值是ArrayList，则表示本地方法调用成功
            return getErrorMeg("2000");
        } else if (ret instanceof String && "".equals(ret.toString())) {//如果返回值是String，并且是空，则表示返回成功
            return getErrorMeg("3000");
        } else {//不是以上，则表示未知异常
            return getErrorMeg("4001");
        }
    }

    /**
     * 取得液位仪错误信息
     *
     * @param code
     * @return
     */
    private static String getErrorMeg(String code) {
        if ("1000".equals(code) || "2000".equals(code) || "3000".equals(code)) {//成功调用
            return "";
        } else if ("1001".equals(code)) {
            return "液位仪接口返回失败";
        } else if ("1002".equals(code)) {
            return "输入参数错误";
        } else {
            return "调用液位仪接口返回未知异常";
        }
    }
}
