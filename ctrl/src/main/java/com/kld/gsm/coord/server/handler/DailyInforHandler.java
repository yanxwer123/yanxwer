package com.kld.gsm.coord.server.handler;


import com.kld.gsm.ATG.service.DailyRunning;
import com.kld.gsm.ATG.service.SysManageDic;
import com.kld.gsm.SysConfig;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.servcie.OilDayStatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime日结信息: 2015/11/2 18:39
 * @Description: 日结信息
 */
public class DailyInforHandler {
    @Autowired
    private static com.kld.gsm.ATG.dao.DailyDailyBalanceDao dailyDailyBalanceDao;
    /**
     * 日结的时候需要从管控同步的数据表
     * @param accountDate
     * @Description 从sybase 库中读取上面四个表，拿到数据存同步到末尾的四个表中
     */
    public static void getInformation(Date accountDate) {
        OilDayStatService oilDayStatService = Context.getInstance().getBean(OilDayStatService.class);
        oilDayStatService.findByDate(accountDate);

//region 省中心API
        SysManageDic dic=Context.getInstance().getBean(SysManageDic.class);

        DailyRunning dailyRunn=Context.getInstance().getBean(DailyRunning.class);
        //dailyRunn.DailyBalanceLost(dic.GetByCode("zxfwqdz").getValue());
        dailyRunn.DailyBalanceLost(SysConfig.getCenterIP());
//endregion

    }
}

