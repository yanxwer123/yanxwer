package com.kld.app.service.impl;

import com.kld.app.service.CheckSerivce;
import com.kld.gsm.ATG.dao.*;
import com.kld.gsm.ATG.domain.DailyTankShift;
import com.kld.gsm.ATG.domain.SysManageCanInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-12-30 15:46
 * @Description:
 */
@Service
@SuppressWarnings("all")
public class CheckSerivceImpl implements CheckSerivce {
    @Autowired
    SysManageCanInfoDao sysManageCanInfoDao;
    @Autowired
    DailyDailyBalanceDao dailyDailyBalanceDao;
    @Autowired
    DailyTankShiftDao dailyTankShiftDao;
    @Autowired
    AcceptanceOdRegisterInfoDao acceptanceOdRegisterInfoDao;
    @Autowired
    DailyTradeAccountDao dailyTradeAccountDao;
    @Autowired
    SysManageOilTypeDao sysManageOilTypeDao;

    @Override
    public Double findRealStock(String oilno) {
        String strStock = dailyDailyBalanceDao.findRealStock(oilno);
        Double dRet = Double.valueOf(0);
        try {
            dRet = Double.parseDouble(strStock);
        } catch (Exception e) {

        }
        return dRet;
    }

    //找到在用的油品类型
    public List<String> findUsedOilTypes() {
        List<String> lOilType = new ArrayList<String>();
        List<SysManageCanInfo> sysManageCanInfoList = sysManageCanInfoDao.selectAll();
        for (SysManageCanInfo item : sysManageCanInfoList) {
            boolean isHave = false;
            for (int i = 0; i < lOilType.size(); i++) {
                if (item.getOilno().equals(lOilType.get(i))) {
                    isHave = true;
                    break;
                }
            }
            if (!isHave) {
                lOilType.add(item.getOilno());
            }
        }
        return lOilType;
    }

    //找到日结的最后一个班次
    @Override
    public String findLastShift() {
        String strShift = dailyTankShiftDao.findOilNoAndShift();
        //查询不到说明从来没有班次，赋予一个最早的值
        if (strShift == null) {
            strShift = "2000010101";
        }
        return strShift;
    }

    @Override
    public List<DailyTankShift> findByShift(String shift) {
        List<DailyTankShift> dailyTankShiftList = dailyTankShiftDao.selectByAccountIsNull(shift);
        return dailyTankShiftList;
    }

    //Map:oilno,dischargel
    @Override
    public List<Map<String, Object>> findDischargeL(String shift) {
        return acceptanceOdRegisterInfoDao.findDischargeL(shift);
    }

    //Map:oilno,liter
    @Override
    public List<Map<String, Object>> getLiter(String shift) {
        return dailyTradeAccountDao.getLiter(shift);
    }
 //Map:oilno,liter
    @Override
    public List<Map<String, Object>> getLiterByAccountDate() {
        return dailyTradeAccountDao.getLiterByAccountDate();
    }



    @Override
    public String selectOilNo(String OilNo) {
        return sysManageOilTypeDao.selectOilNo(OilNo);
    }
}
