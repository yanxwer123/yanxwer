package com.kld.gsm.center.web.webcontroller;

import com.kld.gsm.center.service.OilTypeService;
import com.kld.gsm.center.service.SysDictService;
import com.mangofactory.swagger.annotations.ApiIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fangzhun on 2015/12/4.
 */
@Controller
@ApiIgnore
@RequestMapping("/web/dictinfo")
public class WebDictInfoController {
    @Resource
    private SysDictService sysDictService;

    @RequestMapping("/selectYJLX")
    @ResponseBody
    public List<HashMap<String,String>>  selectYJLX()
    {
        List<HashMap<String,String>> dictType=sysDictService.selectYJLX();
        //增加全部
        HashMap<String,String> hashMap=new HashMap();
        hashMap.put("Name","<全部>");
        hashMap.put("Value","");
        dictType.add(0,hashMap);
        return dictType;
    }
}
