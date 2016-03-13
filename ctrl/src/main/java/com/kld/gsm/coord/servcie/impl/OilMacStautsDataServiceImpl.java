package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.dao.*;
import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.ATGDevice.atg_stock_data_out_t;
import com.kld.gsm.ATGDevice.atg_timestock_data_in_t;
import com.kld.gsm.MacLog.MacLogInfo;
import com.kld.gsm.MacLog.util.EhCacheHelper;
import com.kld.gsm.coord.dao.OilCanInforDao;
import com.kld.gsm.coord.domain.OilCanInfor;
import com.kld.gsm.coord.servcie.IOilMacStautsDataService;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/23 16:34
 * @Description:
 */
@Service
@SuppressWarnings("all")
public class OilMacStautsDataServiceImpl implements IOilMacStautsDataService {
    Logger logger = Logger.getLogger(OilMacStautsDataServiceImpl.class);
    @Autowired
    DailyStaticOilCanInventoryDao dailyStaticOilCanInventoryDao;
    @Autowired
    DailyStaticOilGunInverntoryDao dailyStaticOilGunInverntoryDao;
    @Autowired
    SysManageOilGunInfoDao sysManageOilGunInfoDao;
    @Autowired
    DailyTradeAccountDao dailyTradeAccountDao;
    @Autowired
    AlarmGaTContrastDao alarmGaTContrastDao;
    @Autowired
    DailyTradeInventoryDao dailyTradeInventoryDao;
    @Autowired
    SysManageCanInfoDao sysManageCanInfodao;

    @Override
    public List<Integer> selectAllOilCanNos() {
        List<Integer> canList = new ArrayList<Integer>();
        //获取所有罐号
        logger.info("MonitorInventoryServiceImpl.initTimeStockParameters()..oss_sysmanage_CanInfo");
        List<SysManageCanInfo> oilCanInforList = sysManageCanInfodao.selectAll();
        List<atg_timestock_data_in_t> timeStockList = new ArrayList<atg_timestock_data_in_t>();
        logger.info("oilCanInforList.size:"+oilCanInforList.size());
        for (SysManageCanInfo o : oilCanInforList) {
            canList.add(o.getOilcan());
        }
        return canList;
    }

    @Override
    public void OilMacStautsDataSave(List<atg_stock_data_out_t> stockDataOutList,List<AlarmGaTContrast> alarmGaTContrastList) throws Exception {
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        logger.info("枪出罐出保存(1)List<atg_stock_data_out_t> stockDataOutList.size:"+stockDataOutList.size());
        if(stockDataOutList==null||stockDataOutList.size()<0){
            logger.warn("枪出罐出保存(2)OilMacStautsDataSave的参数stockDataOutList是空");
            return;
        }
        for(atg_stock_data_out_t stockDataOutT : stockDataOutList) {
            //region "保存油罐罐存"
            String id = UUID.randomUUID().toString().replace("-","");
            //油罐静态库存
            DailyStaticOilCanInventory dailyStaticOilCanInventory = new DailyStaticOilCanInventory();
            //给油罐静态库存赋值
            dailyStaticOilCanInventory.setId(id);
            stockDataOutT2DailyStaticOilCanInventory(stockDataOutT, dailyStaticOilCanInventory);
            logger.info("枪出罐出保存（3）保存油罐静态库存"+dailyStaticOilCanInventory);
            //保存油罐静态库存
            dailyStaticOilCanInventoryDao.insert(dailyStaticOilCanInventory);
            //endregion
            //region "保存油枪泵码"
            //根据油罐号获取油枪信息
            List<SysManageOilGunInfo> guninfoList = sysManageOilGunInfoDao.selectOilInfoByOilCanNo(stockDataOutT.uOilCanNo);
            logger.info("枪出罐出保存（4）枪数量:"+guninfoList.size());
            for(int i=0;i<guninfoList.size();i++) {
                //静态罐存油枪
                DailyStaticOilGunInverntory dailyStaticOilGunInverntory = new DailyStaticOilGunInverntory();
                //给静态罐存油枪赋值
                dailyStaticOilGunInverntory.setId(id);
                dailyStaticOilGunInverntory.setGunno(guninfoList.get(i).getOilgun());
                //根据油枪号取得泵码信息
                ////System.out.println("枪出罐出（16）枪泵码数guninfoList.get("+i+").getOilgun():"+guninfoList.get(i).getOilgun());
                //logger.info("枪出罐出（16）枪泵码数guninfoList.get("+i+").getOilgun():"+guninfoList.get(i).getOilgun());
                //region 改为取缓存泵码数
                /*DailyTradeAccount dailyTradeAccount = dailyTradeAccountDao.selectByOilGun(this.formatGunNo(guninfoList.get(i).getOilgun()));
                if(dailyTradeAccount==null){
                    dailyStaticOilGunInverntory.setPumpup(0.0);
                }else {
                    logger.info("枪出罐出保存（5）枪泵码数dailyTradeAccount.getPumpNo():" + dailyTradeAccount.getPumpNo());
                    dailyStaticOilGunInverntory.setPumpup(dailyTradeAccount.getPumpNo());
                }*/
                //endregion
                //region 取缓存泵码数
                Element element=EhCacheHelper.getOilGunStatus((byte) dailyStaticOilGunInverntory.getGunno().intValue());
                dailyStaticOilGunInverntory.setPumpup(0.0);
                 if(element!=null){
                     MacLogInfo macLogInfo = (MacLogInfo) element.getObjectValue();
                     dailyStaticOilGunInverntory.setPumpup(macLogInfo.getPumpReadout());
                 }

                //endregion

                dailyStaticOilGunInverntory.setOilcan(guninfoList.get(i).getOilcan());
                stockDataOutT2DailyStaticOilGunInverntory(stockDataOutT, dailyStaticOilGunInverntory);
                logger.info("枪出罐出保存（6）保存静态罐存油枪dailyStaticOilGunInverntory:"+dailyStaticOilGunInverntory);
                //保存静态罐存油枪
                dailyStaticOilGunInverntoryDao.insert(dailyStaticOilGunInverntory);
            }
            //endregion
            //region "保存检测数据"
            logger.info("枪出罐出保存（7）开始保存对比数据"+alarmGaTContrastList.size());
            for(AlarmGaTContrast alarmGaTContrast :alarmGaTContrastList) {
                logger.info("枪出罐出保存（8）开始保存对比数据stockDataOutT.uOilCanNo:"+stockDataOutT.uOilCanNo);
                logger.info("枪出罐出保存（9）alarmGaTContrast.getOilcan():"+alarmGaTContrast.getOilcan());
                if (stockDataOutT.uOilCanNo == alarmGaTContrast.getOilcan()) {
                    //初始化第一次检测，第一次进入方法时第一次检测时间和第一次检测库存为空
                    // 初始化后第一次检测不为空，第一次检测不执行新增操作
                    logger.info("枪出罐出保存（10）初始化后第一次检测不为空alarmGaTContrast.getFristmeasurestore():" + alarmGaTContrast.getFristmeasurestore());
                    if (alarmGaTContrast.getFristmeasurestore() == null &&
                            alarmGaTContrast.getFristmeasuretime() == null) {
                        logger.info("枪出罐出保存（11）初始化第一次检测，第一次进入方法时第一次检测时间和第一次检测库存为空...");
                        alarmGaTContrast.setFristmeasuretime(sd.parse(stockDataOutT.strDate + stockDataOutT.strTime));
                        alarmGaTContrast.setFristmeasurestore(stockDataOutT.fOilCubage);
                    } else {//第二次以及以后的检测，开始给第二次检测时间和库存赋值，并执行新增操作
                        alarmGaTContrast.setSecodemeasuretime(sd.parse(stockDataOutT.strDate + stockDataOutT.strTime));
                        alarmGaTContrast.setSecodemeasurestore(stockDataOutT.fOilCubage);

                        //间隔时间
                        //(end.getTime() - start.getTime())/(1000*60);
                        Long iInterval=(alarmGaTContrast.getSecodemeasuretime().getTime() - alarmGaTContrast.getFristmeasuretime().getTime())/1000;
                        alarmGaTContrast.setIntervaltime(iInterval.toString());
                        //原时间计算错误
                        //alarmGaTContrast.setIntervaltime(Double.parseDouble(sd.format(alarmGaTContrast.getSecodemeasuretime())) - Double.parseDouble(sd.format(alarmGaTContrast.getFristmeasuretime())) + "");
                        alarmGaTContrast.setTranstatus("0");
                        logger.info("枪出罐出保存（12）第二次以及以后的检测，开始给第二次检测时间和库存赋值，并执行新增操作...alarmGaTContrast:"+alarmGaTContrast);
                        //System.out.println("枪出罐出保存(12)第二次以及以后的检测，开始给第二次检测时间和库存赋值，并执行新增操作...:"+alarmGaTContrast);
                        //查询第一次检测时间的交易的油体积
                        Map fmap = new HashMap();
                        fmap.put("begindate", alarmGaTContrast.getFristmeasuretime());
                        fmap.put("oilcan", alarmGaTContrast.getOilcan());
                        fmap.put("enddate", alarmGaTContrast.getSecodemeasuretime());
                        /*
                        fmap.put("taketime", alarmGaTContrast.getFristmeasuretime());
                        fmap.put("oilcanno", alarmGaTContrast.getOilcan());
                        Object o = dailyTradeInventoryDao.selectOilL(fmap);
                        double firstOilL = 0.0;
                        logger.info("枪出罐出保存(13)第一次检测时间的交易的油体积:"+o);
                        if(o!=null){
                            firstOilL = (Double)o;
                        }
                        //查询第二次检测时间的交易的油体积
                        Map smap = new HashMap();
                        smap.put("taketime", alarmGaTContrast.getSecodemeasuretime());
                        smap.put("oilcanno", alarmGaTContrast.getOilcan());
                        double secondOilL = 0.0;
                        o = dailyTradeInventoryDao.selectOilL(smap);
                        logger.info("枪出罐出保存(14)第二次检测时间的交易的油体积:"+o);
                        if(o!=null){
                            secondOilL = (Double)o;
                        }
                        //两次检测时间内销量*/
                        Double dSale= dailyTradeAccountDao.selectRangeOil(fmap);      //secondOilL - firstOilL;
                        logger.info("枪出罐出保存(15)第二次检测时间的交易的油体积:"+dSale);
                        if(dSale!=null) {
                            alarmGaTContrast.setIntervalsales(dSale);
                        }
                        else
                        {
                            dSale=0.00;
                            alarmGaTContrast.setIntervalsales(dSale);
                        }
                        //差异
                        DecimalFormat df=new DecimalFormat("########0.00");
                        //库存减少量（第一次库存-第二次库存）-销售量，比销售量大太多是有问题的。
                        alarmGaTContrast.setDifference(Double.parseDouble(df.format(alarmGaTContrast.getFristmeasurestore()-alarmGaTContrast.getSecodemeasurestore()-dSale)));
                        //执行新增操作
                        alarmGaTContrastDao.insertSelective(alarmGaTContrast);
                        //保存完成后，把第二次检测数据赋值到第一次检测数据。
                        alarmGaTContrast.setFristmeasurestore(alarmGaTContrast.getSecodemeasurestore());
                        alarmGaTContrast.setFristmeasuretime(alarmGaTContrast.getSecodemeasuretime());
                        alarmGaTContrast.setDifference(null);
                        alarmGaTContrast.setIntervaltime(null);
                        alarmGaTContrast.setSecodemeasuretime(null);
                        alarmGaTContrast.setSecodemeasurestore(null);
                        alarmGaTContrast.setIntervalsales(null);
                        fmap.clear();
                        //smap.clear();
                        logger.info("枪出罐出（22）保存完毕");
                    }
                }
            }
            //endregion
        }
    }

    @Override
    public List<Integer> selectOilGunByOilCanNo(int oilCanNo) {
        return sysManageOilGunInfoDao.selectOilGunByOilCanNo(oilCanNo);
    }

    private void stockDataOutT2DailyStaticOilCanInventory
            (atg_stock_data_out_t stockDataOutT,
             DailyStaticOilCanInventory dailyStaticOilCanInventory)throws Exception{
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        dailyStaticOilCanInventory.setOilcan(stockDataOutT.uOilCanNo);
        dailyStaticOilCanInventory.setMeasuretime(sd.parse(stockDataOutT.strDate + stockDataOutT.strTime));
        dailyStaticOilCanInventory.setOil(stockDataOutT.fOilCubage);
        dailyStaticOilCanInventory.setStandardl(stockDataOutT.fOilStandCubage);
        dailyStaticOilCanInventory.setHeighttotal(stockDataOutT.fTotalHeight);
        dailyStaticOilCanInventory.setWaterheight(stockDataOutT.fWaterHeight);
        dailyStaticOilCanInventory.setWaterl(stockDataOutT.fWaterBulk);
        dailyStaticOilCanInventory.setTemperature(stockDataOutT.fOilTemp);
    }

    private void stockDataOutT2DailyStaticOilGunInverntory
            (atg_stock_data_out_t stockDataOutT,
             DailyStaticOilGunInverntory dailyStaticOilGunInverntory){
       /*
        dailyStaticOilGunInverntory.setGunno();
        dailyStaticOilGunInverntory.setPumpup();
        dailyStaticOilGunInverntory.setOilcan();
        */
    }

    public String formatGunNo(int gunno){
        String ret = gunno+"";
        int len = ret.length();
        int formatlen = 3-len;
        if(formatlen>0){
            for(int i=0;i<formatlen;i++){
                ret = "0"+ret;
            }
        }
        return ret;
    }
}
