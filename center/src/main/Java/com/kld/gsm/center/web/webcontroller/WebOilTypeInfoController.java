package com.kld.gsm.center.web.webcontroller;

import com.kld.gsm.center.domain.oss_sysmanage_oilType;
import com.kld.gsm.center.service.AlarmSaleOutService;
import com.kld.gsm.center.service.OilTypeService;
import com.kld.gsm.center.service.SysDictService;
import com.kld.gsm.center.service.SystemManage;
import com.mangofactory.swagger.annotations.ApiIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fangzhun on 2015/12/4.
 */
@Controller
@ApiIgnore
@RequestMapping("/web/oiltypeinfo")
public class WebOilTypeInfoController {

    @Resource
    private OilTypeService oilTypeService;

    @RequestMapping("/selectOilType")
    @ResponseBody
    public List<HashMap<String,String>> selectOilType(){
       List<HashMap<String,String>> ossSysmanageOilTypes=oilTypeService.selectOilType();
        HashMap<String,String> hashMap=new HashMap();
        hashMap.put("OilName","<全部>");
        hashMap.put("OilNo","");
        ossSysmanageOilTypes.add(0,hashMap);
        return ossSysmanageOilTypes;
    }
    @Resource
    private SysDictService sysDictService;
    @RequestMapping("/seletOilAlarmType")
    @ResponseBody
    public List<HashMap<String,String>> SeletOilAlarmType()
    {
        List<HashMap<String,String>> oilAlarmType=sysDictService.selectByParentId("45");
        //增加全部
        HashMap<String,String> hashMap=new HashMap();
        hashMap.put("name","<全部>");
        hashMap.put("value","");
        oilAlarmType.add(0,hashMap);
        return oilAlarmType;
    }
}
