package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.dao.*;
import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.ATG.service.SysManageDic;
import com.kld.gsm.ATGDevice.ATGManager;
import com.kld.gsm.ATGDevice.atg_stock_data_out_t;
import com.kld.gsm.ATGDevice.atg_timestock_data_in_t;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.servcie.ISaleOutAlarmService;
import com.kld.gsm.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/22 12:59
 * @Description:
 */
@SuppressWarnings("all")
@Service
public class SaleOutAlarmServiceImpl implements ISaleOutAlarmService {

    Logger logger = Logger.getLogger(SaleOutAlarmServiceImpl.class);
    @Resource
    AlarmSaleOutDao alarmSaleOutDao;
    @Resource
    SysManageTimeSaleOutDao sysManageTimeSaleOutDao;
    @Resource
    SysManagePaTRelationDao sysManagePaTRelationDao;//油品和油罐关系
    @Autowired
    SysManageCanInfoDao sysManageCanInfodao;
    @Autowired
    SysManageDictDao  sysManageDicDao;
    @Override
    public void saleOutAlarmSave(List<atg_stock_data_out_t> outList) throws Exception{
        logger.info("come into saleOutAlarmSave...");
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        Map<String,Double> oilnoMap = new HashMap<String, Double>();
        int timefrom =0;//取得当前的小时
        int countSaleTime = 0;
        String nowTime = "";//当前时间
        logger.info("获取到库存数据:"+outList.size());
        //1遍历油罐，取出各个油品的库存
        for(atg_stock_data_out_t stock : outList){
            //取得当前的小时
            timefrom = Integer.parseInt(stock.strTime.substring(0, 2));
            //取得结束报警时间
            nowTime = stock.strDate+stock.strTime;
            logger.info("获取时间段:"+timefrom+"-"+nowTime);
            //根据油罐编号查询油品编号
            SysManageCanInfo sysManageCanInfo = sysManageCanInfodao.selectByPrimaryKey(stock.uOilCanNo);
            logger.info("获取油品编号列表:"+sysManageCanInfo.toString());
            String oilNo="";//油品编号
            if(sysManageCanInfo!=null) {
                oilNo = sysManageCanInfo.getOilno();
            }
            if(oilnoMap.get(oilNo)==null){
                oilnoMap.put(oilNo,stock.fOilCubage);
            }else{
                oilnoMap.put(oilNo,oilnoMap.get(oilNo)+stock.fOilCubage);
            }
        }
        //没有油罐
        if(oilnoMap.size()==0){
            logger.warn("遍历油罐，取出各个油品的库存为空.待下次.");
            return;
        }
        //根据油罐号查询预设值脱销报警时间
        SysManageDict sysManageDict = sysManageDicDao.selectByCode("txyjsj");//脱销报警时间
        if(sysManageDict==null||"".equals(sysManageDict)){
            logger.error("sysManageDict is null!!!");
        }
        int alarmTime = Integer.parseInt(sysManageDict.getValue());
        //查询到 fromtime < sales <=totime
        if(alarmTime<1){alarmTime=1;}
        int timeto=timefrom+alarmTime;
        //接下来几小时需要的库存数
        double nextstock=0;
        Map fromto=new HashMap();
        fromto.put("fromh",timefrom);
        fromto.put("toh",timeto);
        //查询当前小时往后增加 脱销预警小时数的 平均时点销量. 往前推一个月
        List<SysManageTimeSaleOut> oilsales=sysManageTimeSaleOutDao.getOilSaleSum(fromto);
        Map<String,Double> oilsalecount=new HashMap<String, Double>();
        for (SysManageTimeSaleOut stso :oilsales)
        {
            oilsalecount.put(stso.getOilno(),stso.getSales());
        }
        logger.info("获取到油品平均销量(条):"+oilsalecount.size());


        //2遍历所有油品
        for(String oilno : oilnoMap.keySet()){
            double curstock=  oilnoMap.get(oilno);
            try {
                nextstock = oilsalecount.get(oilno);
            }catch (Exception ex)
            {
                nextstock=0;
            }

            logger.info("接下来需要:"+nextstock+"目前具备:"+curstock);
            //解除报警
            AlarmSaleOut alarmSaleOutDataIn = new AlarmSaleOut();
            alarmSaleOutDataIn.setOilno(oilno);
            logger.info("油品编号alarmSaleOutDataIn.oilNo:"+oilno);
            List<AlarmSaleOut> alarmSaleOutList = alarmSaleOutDao.selectEndTime(alarmSaleOutDataIn);
            logger.info("alarmSaleOutList.size():"+alarmSaleOutList.size());

            //需要报警
            if(nextstock>curstock)
            {
                if(alarmSaleOutList.size()<=0) {
                    AlarmSaleOut alarmSaleOut = new AlarmSaleOut();
                    alarmSaleOut.setOilno(oilno);
                    Date dt = DateUtil.convertStringToDate("yyyyMMddHHmmss", nowTime);
                    alarmSaleOut.setMesasuretime(dt);// sd.parse(nowTime)
                    alarmSaleOut.setNowvolume(oilnoMap.get(oilno));
                    alarmSaleOut.setCansalevolume(oilnoMap.get(oilno));
                    alarmSaleOut.setHouraveragesales(nextstock / alarmTime);
                    alarmSaleOut.setPredictsales(oilnoMap.get(oilno));
                    alarmSaleOut.setStartalarmtime(dt);
                    alarmSaleOut.setPredictHours((double) (curstock / (nextstock / alarmTime)));
                    alarmSaleOut.setTranstatus("0");
                    //alarmSaleOut.setShift("班次号");//班次号
                    //保存脱销预警
                    logger.info("保存脱销预警alarmSaleOut:" + alarmSaleOut);
                    alarmSaleOutDao.insert(alarmSaleOut);
                }
            }
            else
            {
                if(alarmSaleOutList.size()>0)
                {
                    AlarmSaleOut alarmSaleOut = new AlarmSaleOut();
                    alarmSaleOut.setOilno(oilno);
                    alarmSaleOut.setEndalarmtime(new Date());
                    //更新报警结束时间，解除报警
                    logger.info("更新报警结束时间alarmSaleOut:"+alarmSaleOut);
                    alarmSaleOutDao.updateByOilno(alarmSaleOut);
                }
            }
/*

            countSaleTime = timefrom;
            int countHours = 0;//预计销售时间
            double hourAverageSales = 0;//该时点时均销量
            //int alarmTime = Integer.parseInt(sysManageDict.getValue());
            double sales = 0;//平均时点销售量总和

*/

/*
            while(sales < oilnoMap.get(oilno)) {//平均时点销售量的总和<该油品的实时库存
                try {
                    logger.info("sales < oilnoMap.get(oilno)");
                    SysManageTimeSaleOutKey sysManageTimeSaleOutKey = new SysManageTimeSaleOutKey();
                    sysManageTimeSaleOutKey.setOilno(oilno);
                    sysManageTimeSaleOutKey.setSaletime(countSaleTime);
                    logger.info("sysManageTimeSaleOutKey:" + sysManageTimeSaleOutKey);
                    //查询平均时点销量
                    SysManageTimeSaleOut sysManageTimeSaleOut = sysManageTimeSaleOutDao.selectByPrimaryKey(sysManageTimeSaleOutKey);
                    //给该时点时均销量赋值
                    if (sysManageTimeSaleOut==null||timefrom == sysManageTimeSaleOut.getSaletime()) {
                        hourAverageSales = 0;
                    }else {
                        hourAverageSales = sysManageTimeSaleOut.getSales();
                    }
                    logger.info("hourAverageSales:" + hourAverageSales);
                    countSaleTime++;//当前时间增加一个小时，下一次while循环查询平均时点库存使用
                    if(sysManageTimeSaleOut==null){//平均时点销量如果是空则加0。
                        sales += 0;
                    }else {
                        sales += sysManageTimeSaleOut.getSales();
                    }
                    //如果平均时点销售量的总和<实时库存
                    if (sales < oilnoMap.get(oilno)) {
                        countHours++;//预计销售时间增加一个小时
                    }
                    logger.info("预计销售时间countHours:" + countHours);
                    if(sales==0&&countHours>alarmTime){
                        logger.warn("平均时点销售量总和是0，并且预计销售时间:"+countHours+"已经大于报警时间:"+alarmTime+"，" +
                                "此时是第一天进入，还没生成数据。跳出while循环。");
                        break;
                    }
                }catch (Exception e){
                    logger.info(e);
                    e.printStackTrace();
                }
            }*/

/*
            //根据油品编号查询是否有空的报警结束时间
            AlarmSaleOut alarmSaleOutDataIn = new AlarmSaleOut();
            alarmSaleOutDataIn.setOilno(oilno);
            logger.info("油品编号alarmSaleOutDataIn.oilNo:"+oilno);
            List<AlarmSaleOut> alarmSaleOutList = alarmSaleOutDao.selectEndTime(alarmSaleOutDataIn);
            logger.info("alarmSaleOutList.size():"+alarmSaleOutList.size());
            if(alarmSaleOutList!=null&&alarmSaleOutList.size()>0){//如果有报警结束时间为空，则更新，不进行新增操作
                if (countHours > alarmTime) {//预计销售时间>预设值脱销报警时间
                    logger.info("countHours >= 2:"+countHours);
//                if (countHours >= 2) {//测试使用
                    AlarmSaleOut alarmSaleOut = new AlarmSaleOut();
                    alarmSaleOut.setOilno(oilno);
                    alarmSaleOut.setEndalarmtime(sd.parse(nowTime));
                    //更新报警结束时间，解除报警
                    logger.info("更新报警结束时间alarmSaleOut:"+alarmSaleOut);
                    alarmSaleOutDao.updateByOilno(alarmSaleOut);
                }
            }else {//如果没有报警结束时间为空则可以继续新增。（有报警时间为空的时候，有新的报警也不新增）
                if (countHours < alarmTime) {
                logger.info("countHours < 2:"+countHours);
//                if (countHours < 2) {//测试使用
                    AlarmSaleOut alarmSaleOut = new AlarmSaleOut();
                    alarmSaleOut.setOilno(oilno);
                    alarmSaleOut.setMesasuretime(sd.parse(nowTime));
                    alarmSaleOut.setNowvolume(oilnoMap.get(oilno));
                    alarmSaleOut.setCansalevolume(oilnoMap.get(oilno));
                    alarmSaleOut.setHouraveragesales(hourAverageSales);
                    alarmSaleOut.setPredictsales(oilnoMap.get(oilno));
                    alarmSaleOut.setStartalarmtime(sd.parse(nowTime));
                    alarmSaleOut.setPredictHours(countHours);
                    //alarmSaleOut.setShift("班次号");//班次号
                    //保存脱销预警
                    logger.info("保存脱销预警alarmSaleOut:"+alarmSaleOut);
                    alarmSaleOutDao.insert(alarmSaleOut);
                }
            }*/
        }
    }

    @Override
    public List<Integer> selectAllOilCanNos() {
        List<Integer> canList = new ArrayList<Integer>();
        //获取所有罐号
        logger.info("MonitorInventoryServiceImpl.initTimeStockParameters()..oss_sysmanage_CanInfo");
        List<SysManageCanInfo> oilCanInforList = sysManageCanInfodao.selectAll();
        List<atg_timestock_data_in_t> timeStockList = new ArrayList<atg_timestock_data_in_t>();
        logger.info("oilCanInforList.size:" + oilCanInforList.size());
        for (SysManageCanInfo o : oilCanInforList) {
            canList.add(o.getOilcan());
        }
        return canList;
    }


   public static void main(String[] args)
    {
        List<atg_stock_data_out_t> outList = ATGManager.getStock();
      SaleOutAlarmServiceImpl soas= Context.getInstance().getBean(SaleOutAlarmServiceImpl.class);
        try{
            soas.saleOutAlarmSave(outList);
        }catch (Exception ex)
        {
            //System.out.println(ex.getMessage());
        }
    }

}