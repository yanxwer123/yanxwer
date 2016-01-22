package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.dao.DailyDailyBalanceDao;
import com.kld.gsm.ATG.domain.DailyDailyBalance;
import com.kld.gsm.coord.dao.VouchStockDao;
import com.kld.gsm.coord.domain.VouchStock;
import com.kld.gsm.coord.servcie.TransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-12-21 15:21
 * @Description:
 */
@SuppressWarnings("all")
@Transactional
@Service
public class TransactionalServiceImpl implements TransactionalService {
    @Resource
    private DailyDailyBalanceDao dailyDailyBalanceDao;
    @Autowired
    private VouchStockDao vouchStockDao;
    /**
     *
     * @param dailyStationShiftInfo
     * @return
     * @throws Exception   遇到指定异常时回滚
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor={Exception.class,RuntimeException.class})
    public int save(VouchStock vouchStock) throws Exception {
       int i =  vouchStockDao.insert( vouchStock);
        //System.out.println(i == 1 ? "VouchStock insert success" : "VouchStock insert fail");

//        DailyDailyBalance dailyDailyBalance = new DailyDailyBalance();
//        dailyDailyBalance.setOilno("1");
//        dailyDailyBalance.setAccountdate(new Date());
//        int result =  save(dailyDailyBalance);
       //  throw new Exception("Sybase RollBack");
         return  i;
     }

    /**
     *
     * @param dailyDailyBalance
     * @return
     * @throws Exception   遇到指定异常时回滚
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor={Exception.class,RuntimeException.class})
    public int save(DailyDailyBalance dailyDailyBalance) throws Exception{
        int i = dailyDailyBalanceDao.insert(dailyDailyBalance);
        //System.out.println(i==1?"DailyDailyBalance insert success":"DailyDailyBalance insert fail");
        return  i;
    }
}
