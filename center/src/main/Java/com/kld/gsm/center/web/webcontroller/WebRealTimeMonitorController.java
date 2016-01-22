package com.kld.gsm.center.web.webcontroller;

 import com.kld.gsm.center.domain.oss_sysmanage_TankInfo;
import com.kld.gsm.center.service.TankInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2016-01-04 17:43
 * @Description:
 */
@Controller
@RequestMapping("/web/realtime")
public class WebRealTimeMonitorController {
    @Resource
    private TankInfoService tankInfoService;

    @RequestMapping("/tankgun")
    public String basicdata(ModelMap modelMap) {
        List<oss_sysmanage_TankInfo> list = tankInfoService.findAll();
        //region替换油品编号为油品名称
        for (oss_sysmanage_TankInfo tankInfo : list) {
            tankInfo.setOilno(tankInfoService.findOilName(String.valueOf(tankInfo.getOilcan())));
        }
        //endregion
        modelMap.addAttribute("tankinfo", list);
        return "realtime/tankgun";
    }

}
