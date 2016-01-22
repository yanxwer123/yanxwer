package com.kld.gsm.coord.server.handler;

import com.kld.gsm.ATG.service.DailyRunning;
import com.kld.gsm.ATG.service.SysManageDic;
import com.kld.gsm.SysConfig;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.servcie.IShiftService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/11/2 18:34
 * @Description: 班结信息
 */
public class ShiftHandler {

    private static final Logger log = LoggerFactory.getLogger(ShiftHandler.class);
    /**
     * 班结信息
     * @param shiftno
     */
    public void saveShiftInfo(String shiftno) {
        IShiftService shiftService =Context.getInstance().getBean(IShiftService.class);
        SysManageDic dic=Context.getInstance().getBean(SysManageDic.class);
        log.info("start saveShiftInfo~~~~~~||||||||||||||||||||||||||~~~~~~~~~");
        shiftService.saveShiftInfo(shiftno);
        DailyRunning  pDailyRunning=Context.getInstance().getBean(DailyRunning.class);
        log.info("Value"+SysConfig.getCenterIP());
        //pDailyRunning.AddClassKnotData(dic.GetByCode("zxfwqdz").getValue());
        //上传省中心
        log.info("上传省中心IP"+SysConfig.getCenterIP());
        pDailyRunning.AddClassKnotData(SysConfig.getCenterIP());
        //System.out.println("-------"+shiftService);
        log.info("end saveShiftInfo~~~||||||||||||||||||||||~~~~~~~~~~");
    }

    public static void main(String[] args) {
         new ShiftHandler().saveShiftInfo("2016092301");

    }
}
