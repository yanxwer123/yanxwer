package com.kld.gsm.center.web.webcontroller;

import com.kld.gsm.center.dao.oss_daily_StationShiftInfoMapper;
import com.kld.gsm.center.dao.oss_monitor_TimeInventoryMapper;
import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.oss_sys_OrgUnit;
import com.kld.gsm.center.service.*;
import com.mangofactory.swagger.annotations.ApiIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    //region 班报查询与导出
    /*
    * 班结
    * */
    @RequestMapping("/shift")
    public ModelAndView shift(){
        return new ModelAndView("/business/shift");
    }

    @RequestMapping( value = "/getShiftList",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg GetShiftList(@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "rows",required = false) Integer rows,@RequestParam(value = "begin",required = false) String begin,@RequestParam(value = "end",required = false) String end,@RequestParam(value = "oucode",required = false) String oucode) {
        //设置当前页
        int intPage=page==null||page<=0?1:page;
        //设置每页显示的数量
        int intPageSize=rows==null||rows<=0?10:rows;
        List<HashMap<String,Object>> list=dStationShiftInfoService.selectPageShiftInfo(intPage, intPageSize, begin, end, oucode);
        if(list!=null){
            ResultMsg result = new ResultMsg();
            result.setData(list);
            result.setRows(list);
            result.setTotal(dStationShiftInfoService.getShiftList(begin, end, oucode).size());
            return result;
        }
        else {
            return null;
        }
    }

    @Resource
    private oss_daily_StationShiftInfoMapper ossDailyStationShiftInfoMapper;
    @RequestMapping("/ExcelShift")
    @ResponseBody
    public void ExcelShift(HttpServletResponse response, String oucode, String begin, String end,String FileName)
    {
        response.setContentType("application/binary;charset=ISO8859_1");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            String fileName =  java.net.URLDecoder.decode(FileName, "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
            String[] titles = { "油站名称", "班次", "接班人","交班人","交接班时间" };
            List<HashMap<String,Object>> list=dStationShiftInfoService.getShiftList(begin, end, oucode);
            dStationShiftInfoService.YZBB(list, titles, outputStream);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //班报明细数据
    @RequestMapping("/shiftdetail")
    public ModelAndView dailylostdetail(){return new ModelAndView("business/shiftdetail");}
    @Resource
    private DPumpDigitShiftService dPumpDigitShiftService;
    @RequestMapping( value = "/getPumpList",method = RequestMethod.GET)
    @ResponseBody()
    public ResultMsg GetPumpList(@RequestParam(value = "shift", required = false) String shift,@RequestParam(value = "oucode", required = false) String oucode){
        ResultMsg result=dPumpDigitShiftService.selectByShift(shift,oucode);
        return  result;
    }

    @Resource
    private DTankShiftService dTankShiftService;
    @RequestMapping( value = "/getTankShift",method = RequestMethod.GET)
    @ResponseBody()
    public ResultMsg GetTankShift(@RequestParam(value = "shift", required = false) String shift,@RequestParam(value = "oucode", required = false) String oucode){
        ResultMsg result=dTankShiftService.selectByShift(shift,oucode);
        return  result;
    }

    @Resource
    private DopotCountService dopotCountService;
    @RequestMapping( value = "/getOpot",method = RequestMethod.GET)
    @ResponseBody()
    public ResultMsg GetOpot(@RequestParam(value = "shift", required = false) String shift,@RequestParam(value = "oucode", required = false) String oucode){
        ResultMsg result=dopotCountService.selectByShift(shift, oucode);
        return  result;
    }
    //endregion
    //region 日结查询与导出
    /*
    * 日结
    * */
    @RequestMapping("/daily")
    public ModelAndView daily(){
        return new ModelAndView("/business/daily");
    }
    @Resource
    private DDailyBalanceService dDailyBalanceService;
    @RequestMapping("/getBalanceInfo")
    @ResponseBody
    public ResultMsg GetBalanceInfo(HttpServletResponse response,Integer page,Integer rows,String start,String end,String oucode)
    {
        //设置当前页
        int intPage=page==null||page<=0?1:page;
        //设置每页显示的数量
        int intPageSize=rows==null||rows<=0?10:rows;
        List<HashMap<String, Object>> list = dDailyBalanceService.querypage(page,rows,start, end, oucode);
        if(list!=null){
            ResultMsg result = new ResultMsg();
            result.setData(list);
            result.setRows(list);
            result.setTotal(dDailyBalanceService.selectbalancebywhere(start, end, oucode).size());
            return result;
        }
        return null;
    }
    @RequestMapping("/excelBalance")
    @ResponseBody
    public void ExcelBalance(HttpServletResponse response, String start,String end,String oucode,String FileName)
    {
        response.setContentType("application/binary;charset=ISO8859_1");
        try
        {
            ServletOutputStream outputStream = response.getOutputStream();
            String fileName =  java.net.URLDecoder.decode(FileName, "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
            String[] titles = { "油站名称","日结时间", "期初库存(L)", "出库单号","进货数量(L)","本期付出(L)","期末库存(L)","实测库存(L)","损溢量(L)","损溢率(%)" };
            List<HashMap<String, Object>> list=dDailyBalanceService.selectbalancebywhere(start, end, oucode);
            dDailyBalanceService.ExportBalance(list, titles, outputStream);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //endregion

    //region 时点库存信息
    /*
    * 实时库存
    * */
    @RequestMapping("/timeinventory")
    public ModelAndView timeinventory(){return new ModelAndView("/business/timeinventory");}

    @RequestMapping("/realstock")
    public ModelAndView RTStock(){
        return new ModelAndView("/business/realstock");
    }

    @Resource
    private oss_monitor_TimeInventoryMapper ossMonitorTimeInventoryMapper;
    @Resource
    private TimeInventoryService timeInventoryService;
    @RequestMapping( value = "/getTimeInventoryInfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> GetTimeInventoryInfo(@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "rows",required = false) Integer rows,@RequestParam(value = "oucode",required = false) String oucode){
        //设置当前页
        int intPage=page==null||page<=0?1:page;
        //设置每页显示的数量
        int intPageSize=rows==null||rows<=0?10:rows;
        List<HashMap<String,Object>> list=timeInventoryService.selectTimeInventoryInfo(intPage, intPageSize, oucode);
        int iCount=timeInventoryService.selectTimeInventoryCount(oucode);
        if (list!=null)
        {
            Map<String, Object> result = new HashMap<String, Object>() ;
            result.put("total",iCount);
            result.put("rows", list);
            return result;
        }
        return  null;
    }
    //endregion


    /*
    * 远程盘点
    * */
    @RequestMapping("/remotechk")
    public ModelAndView RemoteCheck(){
        return new ModelAndView("/business/remotechk");
    }

}
