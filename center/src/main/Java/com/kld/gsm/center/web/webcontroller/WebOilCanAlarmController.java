package com.kld.gsm.center.web.webcontroller;

import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.service.AlarmInventoryService;
import com.mangofactory.swagger.annotations.ApiIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="yanxwer@163.com">yanxiaowei</a>
 * @version 1.0
 * @CreationTime: 2015/12/3 21:29
 * @Description:
 */
@Controller
@ApiIgnore
@RequestMapping("alarminfos/tank")
public class WebOilCanAlarmController extends WebBaseController{
    @Resource
    private AlarmInventoryService AlarmInventoryService;
    @RequestMapping("/selectSaleOut")
    @ResponseBody
    public ResultMsg selectSaleOut(@RequestParam(value = "OilNo", required = false) String OilNo,
                                   @RequestParam(value="StartAlarmTime" , required = false) Date StartAlarmTime, @RequestParam(value = "EndAlarmTime", required = false) Date EndAlarmTime){
        HashMap hashmap=new HashMap();
        hashmap.put("oilno",OilNo);
        hashmap.put("start", StartAlarmTime);
        hashmap.put("end", EndAlarmTime);
//        ResultMsg resultMsg=AlarmInventoryService.selectOilcan(hashmap);
        return null;
    }

}
