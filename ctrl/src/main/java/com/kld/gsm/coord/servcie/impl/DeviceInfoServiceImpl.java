package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.dao.DailyDeviceInfoDao;
import com.kld.gsm.ATG.dao.DailyDeviceInfoDetailDao;
import com.kld.gsm.ATG.dao.SysManageCanInfoDao;
import com.kld.gsm.ATG.domain.DailyDeviceInfo;
import com.kld.gsm.ATG.domain.DailyDeviceInfoDetail;
import com.kld.gsm.ATG.domain.DailyDeviceInfoDetailKey;
import com.kld.gsm.ATG.domain.SysManageCanInfo;
import com.kld.gsm.ATGDevice.ATGManager;
import com.kld.gsm.ATGDevice.atg_device_data_out_t;
import com.kld.gsm.ATGDevice.atg_device_out_t;
import com.kld.gsm.coord.dao.OilCanInforDao;
import com.kld.gsm.coord.domain.OilCanInfor;
import com.kld.gsm.coord.servcie.IDeviceInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/26 15:27
 * @Description:
 */
@SuppressWarnings("all")
@Service
public class DeviceInfoServiceImpl implements IDeviceInfoService {

    Logger logger = Logger.getLogger(DeviceInfoServiceImpl.class);
    @Autowired
    DailyDeviceInfoDao dailyDeviceInfoDao;
    @Autowired
    DailyDeviceInfoDetailDao dailyDeviceInfoDetailDao;
    @Autowired
    SysManageCanInfoDao sysManageCanInfoDao;

    @Override
    public boolean DeviceInfoSave() throws Exception {
        logger.info("come into DeviceInfoSave...");
        boolean result=false;
        //获取所有罐号
        logger.info("获取油罐号...");
        List<SysManageCanInfo> sysManageCanInfoList = sysManageCanInfoDao.selectAll();
        logger.info("获取油罐号...sysManageCanInfoList.size；" + sysManageCanInfoList.size());
        List<Integer> list = new ArrayList<Integer>();
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        //按油罐进行循环遍历
        for(SysManageCanInfo o : sysManageCanInfoList) {
            list.add(o.getOilcan());
            logger.info("oilcan:" + o.getOilcan());
            //获取设备信息
            // atg_device_out_t deviceOutT = ATGManager.getDeviceInfo(list);
            listmain = list;

            atg_device_out_t deviceOutT = getdevice();

            if(deviceOutT==null){
                logger.warn("ATGManager.getDeviceInfo can list:"+list);
                continue;
            }

            result=true;
            list.clear();

            //logger.info("getok:"+o.getOilcan());
            //插入主表
            logger.info("atg_device_out_t:" + deviceOutT.toString());
            DailyDeviceInfo dailyDeviceInfo = new DailyDeviceInfo();
            dailyDeviceInfo.setDevicemodel(deviceOutT.strDeviceModel);
            dailyDeviceInfo.setEquipcode(deviceOutT.strEquipCode);
            dailyDeviceInfo.setVersion(deviceOutT.strSysVersion);
            dailyDeviceInfo.setMakedate(sd.parse(deviceOutT.strMakeDate));
            List<atg_device_data_out_t> deviceDataOutTList = deviceOutT.pDeviceData;
            dailyDeviceInfo.setOilcanno(deviceDataOutTList.get(0).uOilCanNo);
            logger.info("dailyDeviceInfoDao.deleteByPrimaryKey...dailyDeviceInfo:" + dailyDeviceInfo);
            dailyDeviceInfoDao.deleteByPrimaryKey(dailyDeviceInfo.getOilcanno());
            logger.info("dailyDeviceInfoDao.insertSelective...");
            dailyDeviceInfoDao.insertSelective(dailyDeviceInfo);
            //插入子表
            for(atg_device_data_out_t deviceDataOutT : deviceDataOutTList){
                DailyDeviceInfoDetail dailyDeviceInfoDetail = new DailyDeviceInfoDetail();
                dailyDeviceInfoDetail.setOilcanno(deviceDataOutT.uOilCanNo);
                dailyDeviceInfoDetail.setProbemodel(deviceDataOutT.strProbeModel);
                dailyDeviceInfoDetail.setProbeno(deviceDataOutT.strProbeNo);
                DailyDeviceInfoDetailKey dailyDeviceInfoDetailKey = new DailyDeviceInfoDetailKey();
                dailyDeviceInfoDetailKey.setProbeno(deviceDataOutT.strProbeNo);
                dailyDeviceInfoDetailKey.setOilcanno(deviceDataOutT.uOilCanNo);
                logger.info("dailyDeviceInfoDetailDao.deleteByPrimaryKey...:" + dailyDeviceInfoDetailKey);
                dailyDeviceInfoDetailDao.deleteByOilCanNo(deviceDataOutT.uOilCanNo);
                logger.info("dailyDeviceInfoDetailDao.insertSelective...:"+dailyDeviceInfoDetail);
                dailyDeviceInfoDetailDao.insertSelective(dailyDeviceInfoDetail);
                logger.info("end insert");
            }
            logger.info("end device for");
        }
        logger.info("end oilcan for");
        return true;
    }
    private List<Integer> listmain;
    public atg_device_out_t getdevice(){
        atg_device_out_t result=null;
       /* ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask<atg_device_out_t> future =
                new FutureTask<atg_device_out_t>(new Callable<atg_device_out_t>() {//使用Callable接口作为构造参数
                    public atg_device_out_t call() {
                        return  ATGManager.getDeviceInfo(listmain);
                    }});
        executor.execute(future);*/
        final ExecutorService exec = Executors.newFixedThreadPool(1);
        Callable<atg_device_out_t> call = new Callable<atg_device_out_t>() {
            public atg_device_out_t call() throws Exception {
                try {
                    logger.info("=================== get devicelist start:" + new Date().toString() + "=================");
                    logger.info("=================== listmain:" + listmain + "=================");
                     return ATGManager.getDeviceInfo(listmain);
                }catch (Exception e){
                    logger.info("================Callable Failed===========");
                   return null;
                }
            }
        };
        //在这里可以做别的任何事情
        try {
            Future<atg_device_out_t> future = exec.submit(call);
            result=future.get(25000, TimeUnit.MILLISECONDS); //取得结果，同时设置超时执行时间为5秒。同样可以用future.get()，不设置执行超时时间取得结果
        } catch (TimeoutException ex) {
            logger.error("=============== get devicelist time out=============== "+new Date().toString());
        } catch (Exception e) {
            System.out.println("failed to handle.");
        }
        exec.shutdown();
        logger.info("exe end");
        return result;
    }
}
