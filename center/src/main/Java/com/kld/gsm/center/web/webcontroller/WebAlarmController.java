package com.kld.gsm.center.web.webcontroller;



import com.kld.gsm.center.dao.oss_daily_StationShiftInfoMapper;
import com.kld.gsm.center.dao.oss_daily_remoteinventoryMapper;
import com.kld.gsm.center.dao.oss_monitor_TimeInventoryMapper;
import com.kld.gsm.center.dao.oss_sysmanage_oilTypeMapper;
import com.kld.gsm.center.domain.*;
import com.kld.gsm.center.domain.hn.HNGunInfo;
import com.kld.gsm.center.domain.hn.HNRemote;
import com.kld.gsm.center.service.*;
import com.mangofactory.swagger.annotations.ApiIgnore;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
Created BY niyang
Created Date 2015/11/24
description:报警信息
*/
@Controller
@ApiIgnore
@RequestMapping("/web/alarm")
public class WebAlarmController  extends WebBaseController{
    //region "MVC"

    @RequestMapping("/summary")
    public ModelAndView summary(){return new ModelAndView("alarminfos/summary");
    }
    @RequestMapping("/earlywarning")
    public  ModelAndView earlywarning(){return new ModelAndView("alarminfos/earlywarning");}
    @RequestMapping("/rtmonitor")
    public ModelAndView rtmonitor(){return new ModelAndView("alarminfos/rtmonitor");
    }
    @RequestMapping("/tank")
    public ModelAndView tankalram(){return new ModelAndView("alarminfos/tank");
    }
    @RequestMapping("/saleout")
    public ModelAndView saleoutalram(){return new ModelAndView("alarminfos/saleout");
    }
    @RequestMapping("/iq")
    public ModelAndView iqalarm(){return new ModelAndView("alarminfos/iq");
    }
    @RequestMapping("/shiftlost")
    public ModelAndView shiftlostalram(){return new ModelAndView("alarminfos/shiftlost");
    }
    @RequestMapping("/dailylost")
    public ModelAndView dailylosttalram(){return new ModelAndView("alarminfos/dailylost");
    }
    @RequestMapping("/oilinlost")
    public ModelAndView oilinlosttalram(){return new ModelAndView("alarminfos/oilinlost");
    }
    @RequestMapping("/staticlevel")
    public ModelAndView staticlevelEx(){return new ModelAndView("alarminfos/staticlevel");
    }
    @RequestMapping("/dynamiclevel")
    public ModelAndView dynamiclevelEx(){return new ModelAndView("alarminfos/dynamiclevel");
    }
    @RequestMapping("/daybalance")
    public ModelAndView daybalance(){return new ModelAndView("alarminfos/daybalance");}
    @RequestMapping("/inventory")
    public ModelAndView inventory(){return new ModelAndView("alarminfos/inventory");}
    @RequestMapping("/shiftreport")
    public ModelAndView shiftreport(){return new ModelAndView("alarminfos/shiftreport");}
//endregion
    //region 远程盘点历史记录 xhz 2015-12-23
    @Resource
    private Daily dailyservice;
    @RequestMapping( value = "/getRemoteInfo",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg GetRemoteInfo(@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "rows",required = false) Integer rows,@RequestParam(value = "begin",required = false) String begin,@RequestParam(value = "end",required = false) String end,@RequestParam(value = "oucode",required = false) String oucode) {
        //设置当前页
        int intPage=page==null||page<=0?1:page;
        //设置每页显示的数量
        int intPageSize=rows==null||rows<=0?10:rows;
        ResultMsg resultJson=dailyservice.selectRemoteInfo(intPage, intPageSize, begin, end, oucode);
        return  resultJson;
    }
    //endregion
    //region 向数据库中插入盘点信息记录
    @Resource
    private HNGunInfoService hnGunInfoService;
    @Resource
    private oss_daily_remoteinventoryMapper ossDailyRemoteinventoryMapper;
    @Resource
    private SysOrgUnitService sysOrgUnitService;

    @Resource
    private HNRemoteToHnService hnRemoteToHnService;

    @RequestMapping(value = "/remote",method = RequestMethod.POST)
    @ResponseBody
    public ResultMsg Remote(@RequestParam("oucode") String oucode)
    {


        ResultMsg result=new ResultMsg();
        HashMap map=new HashMap();
        map.put("oucode",oucode);
         List<HNRemote> list= hnRemoteToHnService.GetRemotetoHnInfo(oucode);
        oss_daily_remoteinventory model=new oss_daily_remoteinventory();


        if (list.size()>0)
        {
            try {
                for (HNRemote item:list) {
                    for (int i=0;i<item.getPdinfos().size();i++) {
                        UUID uuid = UUID.randomUUID();
                        model.setRid(uuid.toString());
                        model.setOucode(item.getNodeno());
                        model.setStarttime(item.getPdinfos().get(i).getStartTime());
                        model.setEndtime(item.getPdinfos().get(i).getEndTime());
                        model.setStartstock(String.valueOf(item.getPdinfos().get(i).getStartStock()));
                        model.setEndstock(String.valueOf(item.getPdinfos().get(i).getEndStock()));
                        double bqjc = item.getPdinfos().get(i).getStartStock() + item.getPdinfos().get(i).getunloadQty() - item.getPdinfos().get(i).getSaleQty();//期初库存+本期收入-本期付出
                        model.setBqjc(String.valueOf(bqjc));

                        model.setLossqty(String.valueOf(item.getPdinfos().get(i).getLossQty()));

                        model.setLossrate(String.valueOf(item.getPdinfos().get(i).getlossRate()));

                        model.setOilname(dailyservice.selectOilName(String.valueOf(item.getPdinfos().get(i).getOilcan()), oucode));
                        String x = item.getNodeno();
                        oss_sys_OrgUnit sysOrgUnit = sysOrgUnitService.selectByOUCode(item.getNodeno());
                        if (sysOrgUnit != null) {
                            model.setOuname(sysOrgUnit.getOuname());
                        }

                        model.setUnloadqty(String.valueOf(item.getPdinfos().get(i).getunloadQty()));
                        model.setSaleqty(String.valueOf(item.getPdinfos().get(i).getSaleQty()));
                        model.setBacktankqty(String.valueOf(item.getPdinfos().get(i).getBackTankQty()));
                        ossDailyRemoteinventoryMapper.insert(model);
                    }


                }
                result.setResult(true);

                return result;
            }
            catch(Exception e)
            {

                result.setResult(false);
                result.setMsg(e.getMessage());
                return result;
            }
        }
        else
        {
            result.setResult(false);
            result.setMsg("暂无数据信息！");
            return result;
        }

    }
    //endregion

    //region  库存异常报警
    @Resource
    private AlarmInventoryService alarmInventoryService;
    @RequestMapping( value = "/getalarmInventoryInfo",method = RequestMethod.GET)
    @ResponseBody  //Integer current, Integer pagesize, String begin, String end, String oucode,String result
    public ResultMsg GetAalrmInventory(@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "rows",required = false) Integer rows,@RequestParam(value = "begin",required = false) String begin,@RequestParam(value = "end",required = false) String end,@RequestParam(value = "oucode",required = false) String oucode,@RequestParam(value = "alarmtype",required = false) String alarmtype){
        //设置当前页
        int intPage=page==null||page<=0?1:page;
        //设置每页显示的数量
        int intPageSize=rows==null||rows<=0?10:rows;
        List<HashMap<String, Object>> list=alarmInventoryService.selectInventoryInfo(intPage, intPageSize, begin, end, oucode, alarmtype);
        ResultMsg resultJson=new ResultMsg();
        resultJson.setData(list);
        resultJson.setRows(list);
        resultJson.setTotal(alarmInventoryService.selectInventoryAllInfo(begin, end, oucode, alarmtype).size());
        return  resultJson;
    }
    //endregion
    //region 导出库存报警信息
    @RequestMapping("/excelAlarmInventory")
    @ResponseBody
    public void ExcelAalrmInventory(HttpServletResponse response, String begin,String end,String oucode,String alarmtype,String FileName)
    {
        response.setContentType("application/binary;charset=ISO8859_1");
        try
        {
            ServletOutputStream outputStream = response.getOutputStream();
            String fileName =  java.net.URLDecoder.decode(FileName, "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
            String[] titles = { "油站名称", "油罐编号", "油品类型","报警类型","开始报警时间","结束报警时间"};
            List<HashMap<String, Object>> list=alarmInventoryService.selectInventoryAllInfo(begin, end, oucode, alarmtype);
            alarmInventoryService.ExportIntenvory(list, titles, outputStream);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //endregion
    //region 设备故障报警
    @RequestMapping("/selectIqPageList")
    @ResponseBody
    public ResultMsg selectIqPageList(@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "rows",required = false) Integer rows,@RequestParam(value = "begin",required = false) String begin,@RequestParam(value = "end",required = false) String end,@RequestParam(value = "oucode",required = false) String oucode,@RequestParam(value = "FailureType",required = false) String FailureType){
        //设置当前页
        int intPage=page==null||page<=0?1:page;
        //设置每页显示的数量
        int intPageSize=rows==null||rows<=0?10:rows;


        List<HashMap<String,Object>> list=alarmEquipmentService.queryPrepayPageList(intPage, intPageSize, FailureType, begin, end,oucode);//传进去的page要进行处理

        ResultMsg resultJson=new ResultMsg();
        resultJson.setData(list);
        resultJson.setRows(list);
        resultJson.setTotal(alarmEquipmentService.queryPrepayCount(FailureType, begin, end, oucode));
        return resultJson;

    }
    //endregion
    //region 导出设备故障报警
    @RequestMapping("/excelIQ")
    @ResponseBody
    public void ExcelIQ(HttpServletResponse response, String begin,String end,String oucode,String FailureType,String FileName)
    {
        response.setContentType("application/binary;charset=ISO8859_1");
        try
        {
            ServletOutputStream outputStream = response.getOutputStream();
            String fileName =  java.net.URLDecoder.decode(FileName, "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式

            String[] titles = { "油站名称", "油罐编号", "开始报警时间","结束报警时间","故障类型","设备代码","设备品牌","探棒型号","备注"};
            List<HashMap<String, Object>> list=alarmEquipmentService.queryPrepayPageList(null,null,FailureType, begin, end, oucode);
            alarmEquipmentService.ExcelIQ(list, titles, outputStream);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //endregion
    //region 进油损溢异常
    @Resource
    private AlarmOilInContrastService alarmOilInContrastService;
    @RequestMapping("/getOilInInfo")
    @ResponseBody
    public ResultMsg GetOilInInfo(HttpServletResponse response,Integer page,Integer rows,String start,String end,String oucode)
    {
        //设置当前页
        int intPage=page==null||page<=0?1:page;
        //设置每页显示的数量
        int intPageSize=rows==null||rows<=0?10:rows;

        List<HashMap<String, Object>> list = alarmOilInContrastService.querypage(intPage, intPageSize, oucode, start, end);
        if(list!=null){
            ResultMsg result = new ResultMsg();
            result.setData(list);
            result.setRows(list);
            result.setTotal(alarmOilInContrastService.selectoilincontrastbywhere(start, end, oucode).size());
            return result;
        }
        return null;
    }
    //endregion
    //region 导出进油损益报警 xhz 2015-12-22
    @RequestMapping("/excelOilIn")
    @ResponseBody
    public void ExcelOilIn(HttpServletResponse response, String start,String end,String oucode,String result,String FileName)
    {
        response.setContentType("application/binary;charset=ISO8859_1");

        try
        {

            ServletOutputStream outputStream = response.getOutputStream();
            String fileName =  java.net.URLDecoder.decode(FileName, "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
            String[] titles = { "油站名称","油品类型","进货验收日期", "出库单号", "原发升数(L)","实收升数(L)","损耗量(L)","损耗率(%)"};
            List<HashMap<String, Object>> list=alarmOilInContrastService.selectoilincontrastbywhere(start, end, oucode);
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
            alarmOilInContrastService.ExportOilInContrast(list, titles, outputStream);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //endregion
    //region 班结损溢异常
    @Resource
    private AlarmShiftLostService alarmShiftLostService;
    @Resource
    private oss_sysmanage_oilTypeMapper ossSysmanageOilTypeMapper;
    @RequestMapping("/getShiftLostInfo")
    @ResponseBody
    public ResultMsg GetShiftLostInfo(HttpServletResponse response,Integer page,Integer rows,String start,String end,String oucode)
    {
        //设置当前页
        int intPage=page==null||page<=0?1:page;
        //设置每页显示的数量
        int intPageSize=rows==null||rows<=0?10:rows;

        List<HashMap<String, Object>> list = alarmShiftLostService.querypage(intPage, intPageSize, oucode, start, end);
        /*for (HashMap<String,Object> hashMap:list) {
            oss_sys_OrgUnit model=sysOrgUnitService.selectByOUCode(hashMap.get("OUCode").toString());
            if(model!=null) {
                hashMap.put("ouname", model.getOuname());
            }
            else
            {
                hashMap.put("ouname", "");
            }
            HashMap map=new HashMap();
            map.put("oucode",hashMap.get("OUCode").toString());
            map.put("oilcan",hashMap.get("OilCanNo").toString());
            if (ossSysmanageOilTypeMapper.selectByoilCanNo(map)!=null) {
                hashMap.put("oilname",ossSysmanageOilTypeMapper.selectByoilCanNo(map) );
            }
            else
            {
                hashMap.put("oilname","");
            }
            //ProfitLossRatio --站上应该计算，中心数据库为空查看下是站上的代码否存在问题
            //损耗量Loss
            //付油量Sale
            Double dLoss=Double.parseDouble(hashMap.get("Loss").toString());
            Double dSale=Double.parseDouble(hashMap.get("Sale").toString());
            Double dRatio = dLoss / dSale*100;
            DecimalFormat df = new DecimalFormat("0.0");
            hashMap.put("ProfitLossRatio",df.format(dRatio)+"%");
        }*/

        if(list!=null){
            ResultMsg result = new ResultMsg();
            result.setData(list);
            result.setRows(list);
            result.setTotal(alarmShiftLostService.selectshiftlostbywhere(start, end, oucode).size());
            return result;
        }
        return null;
    }
    //endregion
    //region 导出班结损益报警 xhz 2015-12-22
    @RequestMapping("/excelShiftLost")
    @ResponseBody
    public void ExcelShiftLost(HttpServletResponse response, String start,String end,String oucode,String result,String FileName)
    {
        response.setContentType("application/binary;charset=ISO8859_1");
        try
        {
            ServletOutputStream outputStream = response.getOutputStream();
            String fileName =  java.net.URLDecoder.decode(FileName, "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
            String[] titles = { "油站名称", "班次","交接班时间","油罐编号", "油品","接班油高(mm)","接班油量(L)","交班油高(mm)","交班油量(L)","卸油量(L)","付油量(L)","损耗量(L)","损溢率(%)"};
            List<HashMap<String, Object>> list=alarmShiftLostService.selectshiftlostbywhere(start, end, oucode);
            alarmShiftLostService.ExportShiftLost(list, titles, outputStream);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    //endregion
    //region 日结损溢异常
    @Resource
    private AlarmDailyLostService alarmDailyLostService;
    @RequestMapping( value = "/getDailyLostList",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg GetDailyLostList(@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "rows",required = false) Integer rows,@RequestParam(value = "begin",required = false) String begin,@RequestParam(value = "end",required = false) String end,@RequestParam(value = "oucode",required = false) String oucode) {
        //设置当前页
        int intPage=page==null||page<=0?1:page;
        //设置每页显示的数量
        int intPageSize=rows==null||rows<=0?10:rows;
        List<HashMap<String, Object>> list=alarmDailyLostService.selectPageDailyLost(intPage, intPageSize, begin, end, oucode);
        if(list!=null){
            ResultMsg result = new ResultMsg();
            result.setData(list);
            result.setRows(list);

            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date=new Date();
            try {
                date = fmt.parse(end);
            }
            catch (Exception e)
            {

            }
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
            end = fmt.format(calendar.getTime());

            HashMap hashMap = new HashMap();
            hashMap.put("begin", begin);
            hashMap.put("end", end);
            if (oucode!=null && oucode!="") {
                hashMap.put("oucode", oucode + "%");
            }
            else
            {
                hashMap.put("oucode", oucode);
            }
            result.setTotal(alarmDailyLostService.selectDailyLost(hashMap).size());
            return result;
        }
        return  null;
    }
    //endregion
    //region 导出日结损益信息
    @RequestMapping("/excelDailyLost")
    @ResponseBody
    public void ExcelDailyLost(HttpServletResponse response, String begin,String end,String oucode,String FileName)
    {
        response.setContentType("application/binary;charset=ISO8859_1");
        try
        {
            ServletOutputStream outputStream = response.getOutputStream();
            String fileName =  java.net.URLDecoder.decode(FileName, "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
            String[] titles = { "油站名称", "油品类型", "日结时间","期初库存(L)", "出库单号","进货数量(L)","期间付出(L)","期末库存(L)","实测库存(L)","损溢量(L)","损溢率(%)" };
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date=new Date();
            date = fmt.parse(end);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
            end = fmt.format(calendar.getTime());
            HashMap map = new HashMap();
            map.put("begin", begin);
            map.put("end", end);
            if (oucode!=null && oucode!="") {
                map.put("oucode", oucode + "%");
            }
            else
            {
                map.put("oucode", oucode);
            }
            List<HashMap<String, Object>> list=alarmDailyLostService.selectDailyLost(map);
            alarmDailyLostService.ExportDailyLost(list, titles, outputStream);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
//#endregion  x
    //region 静态液位异常
    @Resource
    private AlarmMeasureLeakService alarmMeasureLeakService;
    @RequestMapping("/getMeasureInfo")
    @ResponseBody
    public ResultMsg GetMeasureInfo(HttpServletResponse response,Integer page,Integer rows,String start,String end,String oucode)
    {
        //设置当前页
        int intPage=page==null||page<=0?1:page;
        //设置每页显示的数量
        int intPageSize=rows==null||rows<=0?10:rows;

        List<HashMap<String, Object>> list = alarmMeasureLeakService.querypage(intPage, intPageSize, oucode, start, end);
        for (HashMap<String,Object> hashMap:list) {
            if("0".equals(hashMap.get("RevealStatus"))){
                hashMap.put("Status","不泄漏");
            }
            if("1".equals(hashMap.get("RevealStatus"))){
                hashMap.put("Status","渗漏");
            }
            if("2".equals(hashMap.get("RevealStatus"))){
                hashMap.put("Status","漏油");
            }
            if("3".equals(hashMap.get("RevealStatus"))) {
                hashMap.put("Status", "盗油");
            }

        }

        if(list!=null){
            ResultMsg result = new ResultMsg();
            result.setData(list);
            result.setRows(list);
            result.setTotal(alarmMeasureLeakService.selectmeasurebywhere(start, end, oucode).size());
            return result;
        }
        return null;
    }
    //endregion
    //region 导出静态液位异常
    @RequestMapping("/excelStaticLevel")
    @ResponseBody
    public void ExcelStaticLevel(HttpServletResponse response, String start,String end,String oucode,String FileName)
    {
        response.setContentType("application/binary;charset=ISO8859_1");
        try
        {
            ServletOutputStream outputStream = response.getOutputStream();
            String fileName =  java.net.URLDecoder.decode(FileName, "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
            String[] titles = { "油站名称", "油罐编号", "泄漏状态","泄漏速率(L/h)","起始日期","起始油水总高(mm)","起始水高(mm)","起始5点温度1(℃)","起始5点温度2(℃)","起始5点温度3(℃)","起始5点温度4(℃)","起始5点温度5(℃)","结束时间","结束油水总高(mm)","结束水高(mm)","结束5点温度1(℃)","结束5点温度2(℃)","结束5点温度3(℃)","结束5点温度4(℃)","结束5点温度5(℃)" };

            List<HashMap<String, Object>> list=alarmMeasureLeakService.selectmeasurebywhere(start, end, oucode);
            for (HashMap<String,Object> hashMap:list) {
                if("0".equals(hashMap.get("RevealStatus"))){
                    hashMap.put("Status","不泄漏");
                }
                if("1".equals(hashMap.get("RevealStatus"))){
                    hashMap.put("Status","渗漏");
                }
                if("2".equals(hashMap.get("RevealStatus"))){
                    hashMap.put("Status","漏油");
                }
                if("3".equals(hashMap.get("RevealStatus"))) {
                    hashMap.put("Status", "盗油");
                }

            }

            alarmMeasureLeakService.ExportMeasureLeak(list, titles, outputStream);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    //endregion
    //region 枪出罐出对比-动态液位异常
    @Resource
    private AlarmGaTContrastService alarmGaTContrastService;
    @RequestMapping( value = "/getGatInfo",method = RequestMethod.GET)
    @ResponseBody  //Integer current, Integer pagesize, String begin, String end, String oucode,String result
    public ResultMsg GetGatInfo(@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "rows",required = false) Integer rows,@RequestParam(value = "begin",required = false) String begin,@RequestParam(value = "end",required = false) String end,@RequestParam(value = "oucode",required = false) String oucode,@RequestParam(value = "result",required = false) String result){
        //设置当前页
        int intPage=page==null||page<=0?1:page;
        //设置每页显示的数量
        int intPageSize=rows==null||rows<=0?10:rows;
        List<HashMap<String, Object>> list=alarmGaTContrastService.selectGatInfo(intPage, intPageSize, begin, end, oucode, result);
        for (HashMap<String,Object> hashMap:list) {
            Integer iTime=Double.valueOf(hashMap.get("IntervalTime").toString()).intValue();
            hashMap.put("IntervalTime",(iTime/60)+"分"+(iTime%60)+"秒");
        }
        ResultMsg resultJson=new ResultMsg();
        resultJson.setData(list);
        resultJson.setRows(list);
        resultJson.setTotal(alarmGaTContrastService.selectCountAllInfo(begin, end, oucode, result));
        return  resultJson;
    }

    @RequestMapping( value = "/getAllGatInfo",method = RequestMethod.GET)
    @ResponseBody  //Integer current, Integer pagesize, String begin, String end, String oucode,String result
    public ResultMsg GetAllGatInfo(@RequestParam(value = "begin",required = false) String begin,@RequestParam(value = "end",required = false) String end,@RequestParam(value = "oucode",required = false) String oucode,@RequestParam(value = "result",required = false) String result){
        ResultMsg resultJson=new ResultMsg();
        List<HashMap<String, Object>> list=alarmGaTContrastService.selectGatAllInfo(begin, end, oucode, result);
        resultJson.setData(list);
        resultJson.setRows(list);
        resultJson.setTotal(list.size());
        return  resultJson;
    }
    //endregion
    //region 导出动态液位仪异常 xhz 2015-12-21
    @RequestMapping("/excelGaTContrast")
    @ResponseBody
    public void ExcelGaTContrast(HttpServletResponse response, String start,String end,String oucode,String result,String FileName)
    {
        response.setContentType("application/binary;charset=ISO8859_1");
        try
        {
            ServletOutputStream outputStream = response.getOutputStream();
            String fileName =  java.net.URLDecoder.decode(FileName, "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
            String[] titles = { "油站名称", "油罐编号", "第一次检测时间","第一次检测库存","第二次检测时间","第二次检测库存","两次检测时间内销量","间隔时间","差异" };
            List<HashMap<String, Object>> list=alarmGaTContrastService.selectGatAllInfo(start, end, oucode, result);

            alarmGaTContrastService.ExportGatContrast(list, titles, outputStream);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //endregion
    //region 脱销异常报警
    @Resource
    private AlarmSaleOutService alarmSaleOutService;
    @Resource
    private SysDictService sysDictService;
    @Resource
    private AlarmEquipmentService alarmEquipmentService;
    @RequestMapping( value = "/getSaleOut",method = RequestMethod.GET)
    @ResponseBody  //Integer current, Integer pagesize, String begin, String end, String oucode,String result
    public ResultMsg getSaleOut(@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "rows",required = false) Integer rows,@RequestParam(value = "begin",required = false) String begin,@RequestParam(value = "end",required = false) String end,@RequestParam(value = "oucode",required = false) String oucode,@RequestParam(value = "result",required = false) String result){
        //设置当前页
        int intPage=page==null||page<=0?1:page;
        //设置每页显示的数量
        int intPageSize=rows==null||rows<=0?10:rows;
        List<HashMap<String, Object>> list=alarmSaleOutService.queryPrepayPageList(intPage, intPageSize,oucode, begin, end);
        ResultMsg resultJson=new ResultMsg();
        resultJson.setData(list);
        resultJson.setRows(list);
        resultJson.setTotal(alarmSaleOutService.queryPrepayCount(oucode,begin, end));
        return  resultJson;
    }

    @RequestMapping( value = "/getAllSaleOut",method = RequestMethod.GET)
    @ResponseBody  //Integer current, Integer pagesize, String begin, String end, String oucode,String result
    public ResultMsg getAllSaleOut(@RequestParam(value = "begin",required = false) String begin,@RequestParam(value = "end",required = false) String end,@RequestParam(value = "oucode",required = false) String oucode,@RequestParam(value = "result",required = false) String result){
        ResultMsg resultJson=new ResultMsg();
        List<HashMap<String, Object>> list=alarmGaTContrastService.selectGatAllInfo(begin, end, oucode, result);
        resultJson.setData(list);
        resultJson.setRows(list);
        resultJson.setTotal(list.size());
        return  resultJson;
    }
    //endregion
    //region 导出脱销报警
    @RequestMapping("/exportSaleOut")
    @ResponseBody
    public void exportSaleOut(HttpServletResponse response, String oucode,String begin,String end,String FileName)
    {
        response.setContentType("application/binary;charset=ISO8859_1");
        try
        {
            ServletOutputStream outputStream = response.getOutputStream();
            String fileName =  java.net.URLDecoder.decode(FileName, "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
            String[] titles = { "油站名称", "油品类型", "开始报警时间","结束报警时间","当前体积(L)","可销售体积(L)","平均每小时销售量(L)","预计销售时间(h)" };
            List<HashMap<String, Object>> list=alarmSaleOutService.queryPrepayPageList(null, null, oucode, begin, end);
            alarmSaleOutService.ExcelSaleOut(list, titles, outputStream);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //endregion

}
