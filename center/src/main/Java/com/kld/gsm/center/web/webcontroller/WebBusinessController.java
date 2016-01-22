package com.kld.gsm.center.web.webcontroller;

import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.oss_sys_OrgUnit;
import com.kld.gsm.center.service.DStationShiftInfoService;
import com.kld.gsm.center.service.Daily;
import com.kld.gsm.center.service.SysOrgUnitService;
import com.mangofactory.swagger.annotations.ApiIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.jws.WebParam;
import java.util.HashMap;
import java.util.List;

/*
Created BY niyang
Created Date 2015/11/24
description：业务管理
*/
@Controller
@ApiIgnore
@RequestMapping("/web/business")
public class WebBusinessController  extends WebBaseController {


    @Resource
    private DStationShiftInfoService dStationShiftInfoService;
    @Resource
    private SysOrgUnitService sysOrgUnitService;
    /*进货验收*/
    @RequestMapping("/accept")
    public ModelAndView accept(){
        return  new ModelAndView("/business/accept");
    }

    /*
    * 油站配送
    * */
    @RequestMapping("/sd")
    public ModelAndView StationDistrbution(){
        return new ModelAndView("/business/sd");
    }

    /*
    * 班结
    * */
    @RequestMapping("/shift")
    public ModelAndView shift(){
        return new ModelAndView("/business/shift");
    }


    /*@RequestMapping( value = "/getShiftList",method = RequestMethod.GET)
    @ResponseBody()
    public ResultMsg GetShiftList(@RequestParam(value = "oucode", required = false) String oucode,@RequestParam(value = "begin",required = false) String begin,@RequestParam(value = "end",required = false) String end){
        HashMap<String,Object> map=new HashMap<String, Object>();
        map.put("oucode",oucode);
        map.put("begin",begin);
        map.put("end",end);
        ResultMsg result=dStationShiftInfoService.getShiftList(map);
        return  result;
    }
*/

    @RequestMapping( value = "/getShiftList",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg GetShiftList(@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "rows",required = false) Integer rows,@RequestParam(value = "begin",required = false) String begin,@RequestParam(value = "end",required = false) String end,@RequestParam(value = "oucode",required = false) String oucode) {
        //设置当前页
        int intPage=page==null||page<=0?1:page;
        //设置每页显示的数量
        int intPageSize=rows==null||rows<=0?10:rows;
        List<HashMap<String,Object>> list=dStationShiftInfoService.selectPageShiftInfo(intPage, intPageSize, begin, end, oucode);
        for (HashMap<String,Object> hashMap:list) {
            oss_sys_OrgUnit model=sysOrgUnitService.selectByOUCode(hashMap.get("OUCode").toString());
            if(model!=null) {
                hashMap.put("ouname", model.getOuname());
            }
            else
            {
                hashMap.put("ouname", "");
            }
        }
        if(list!=null){
            ResultMsg result = new ResultMsg();
            result.setData(list);
            result.setRows(list);
            HashMap map = new HashMap();
            map.put("begin", begin);
            map.put("end", end);
            map.put("oucode", oucode + "%");
            result.setTotal(dStationShiftInfoService.getShiftList(map).size());
            return result;
        }
        else {
            return null;
        }
    }


    /*
    * 日结
    * */
    @RequestMapping("/daily")
    public ModelAndView daily(){
        return new ModelAndView("/business/daily");
    }

    /*
    * 实时库存
    * */
    @RequestMapping("/realstock")
    public ModelAndView RTStock(){
        return new ModelAndView("/business/realstock");
    }

    /*
    * 远程盘点
    * */
    @RequestMapping("/remotechk")
    public ModelAndView RemoteCheck(){
        return new ModelAndView("/business/remotechk");
    }

}
