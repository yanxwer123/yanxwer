package com.kld.gsm.coord.timertask;

import com.kld.gsm.ATG.dao.SysManageIquidCubageDao;
import com.kld.gsm.ATG.dao.SysManageIquidCubageInfoDao;
import com.kld.gsm.ATG.domain.SysManageIquidCubage;
import com.kld.gsm.ATG.domain.SysManageIquidCubageInfo;
import com.kld.gsm.ATGDevice.ATGManager;
import com.kld.gsm.ATGDevice.atg_capacity_data_in_t;
import com.kld.gsm.ATGDevice.atg_capacitytable_data_in_t;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.coord.Constants;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.dao.OilCanInforDao;
import com.kld.gsm.coord.domain.OilCanInfor;
import com.kld.gsm.util.JsonMapper;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  容积表逻辑需要重新规划.不启动计划任务
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/12/9 16:53
 * @Description: 获取液位仪容积表信息
 */
public class GetIquidCubagePolling extends Thread{

    Logger logger  = Logger.getLogger(GetIquidCubagePolling.class);
    @Override
    public void run(){
       // while(true) {


            try {
                logger.error("GetIquidCubagePolling start... ");
                // 存放油罐号
                OilCanInforDao oilCanInforDao = Context.getInstance().getBean(OilCanInforDao.class);
                List oilnoList = new ArrayList();
                List<OilCanInfor> oilCanInforList = oilCanInforDao.selectOilCanInfor();
                if (oilCanInforList.size() > 0) {
                    //todo 遍历所有的油罐号存到oilnoList中
                    for (OilCanInfor smtif : oilCanInforList) {
                        oilnoList.add(smtif.getOilcanno());
                    }
                }
                //拿到所有的油罐号去调用液位仪接口,拿到液位仪信息 存入Mysql
                List<atg_capacity_data_in_t> resultCaTabList = ATGManager.getCapacityTable(oilnoList);
                for (atg_capacity_data_in_t cdit : resultCaTabList) {
                    int uOilCanNO = cdit.uOilCanNO;
                    SysManageIquidCubage sysManageCubage = new SysManageIquidCubage();
                    sysManageCubage.setOilcan(uOilCanNO);
                    sysManageCubage.setVersion(cdit.strVersion);
                    //TODO 持久层操作，Insert操作     //获取消息中的操作类型
                    SysManageIquidCubageDao sysManageIquidCubageDao = Context.getInstance().getBean(SysManageIquidCubageDao.class);
                    SysManageIquidCubage temp = sysManageIquidCubageDao.selectByPrimaryKey(sysManageCubage);
                    if (null != temp) {
                        logger.info("SysManageIquidCubage is already exists details:" + temp);
                        continue;
                    }
                    sysManageIquidCubageDao.insert(sysManageCubage);
                    List<atg_capacitytable_data_in_t> acditList = cdit.pCapacityTableData;
                    for (atg_capacitytable_data_in_t acdit : acditList) {
                        double auh = acdit.uHigh;
                        double fl = acdit.fLiter;
                        SysManageIquidCubageInfo sysManageCubageInfo = new SysManageIquidCubageInfo();
                        sysManageCubageInfo.setOilcan(uOilCanNO);
                        sysManageCubageInfo.setVersion(cdit.strVersion);
                        sysManageCubageInfo.setHeight(auh);
                        sysManageCubageInfo.setLiter(fl);
                        SysManageIquidCubageInfoDao sysManageIquidCubageInfoDao = Context.getInstance().getBean(SysManageIquidCubageInfoDao.class);
                        sysManageIquidCubageInfoDao.insertSelective(sysManageCubageInfo);
                    }
                }
            } catch (Exception e) {
                logger.error("GetIquidCubagePolling error" + e);
                e.printStackTrace();
            }
       // }
    }
}
