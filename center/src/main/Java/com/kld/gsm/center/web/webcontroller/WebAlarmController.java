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
import java.io.FileOutputStream;
import java.io.IOException;
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

    @RequestMapping("/summary")
    public ModelAndView summary(){
        return new ModelAndView("alarminfos/summary");
    }

    @RequestMapping("/earlywarning")
     public  ModelAndView earlywarning(){return new ModelAndView("alarminfos/earlywarning");}
    @RequestMapping("/rtmonitor")
    public ModelAndView rtmonitor(){
        return new ModelAndView("alarminfos/rtmonitor");
    }
    @RequestMapping("/tank")
    public ModelAndView tankalram(){
        return new ModelAndView("alarminfos/tank");
    }

    @RequestMapping("/saleout")
    public ModelAndView saleoutalram(){
        return new ModelAndView("alarminfos/saleout");
    }

    @RequestMapping("/iq")
    public ModelAndView iqalarm(){
        return new ModelAndView("alarminfos/iq");
    }

    @RequestMapping("/shiftlost")
    public ModelAndView shiftlostalram(){
        return new ModelAndView("alarminfos/shiftlost");
    }

    @RequestMapping("/dailylost")
    public ModelAndView dailylosttalram(){
        return new ModelAndView("alarminfos/dailylost");
    }

    @RequestMapping("/oilinlost")
    public ModelAndView oilinlosttalram(){
        return new ModelAndView("alarminfos/oilinlost");
    }

    @RequestMapping("/staticlevel")
    public ModelAndView staticlevelEx(){
        return new ModelAndView("alarminfos/staticlevel");
    }

    @RequestMapping("/dynamiclevel")
    public ModelAndView dynamiclevelEx(){
        return new ModelAndView("alarminfos/dynamiclevel");
    }


    @RequestMapping("/daybalance")
    public ModelAndView daybalance(){return new ModelAndView("alarminfos/daybalance");}

    @RequestMapping("/inventory")
    public ModelAndView inventory(){return new ModelAndView("alarminfos/inventory");}

    @RequestMapping("/timeinventory")
    public ModelAndView timeinventory(){return new ModelAndView("alarminfos/timeinventory");}
    @RequestMapping("/shiftreport")
    public ModelAndView shiftreport(){return new ModelAndView("alarminfos/shiftreport");}

    @RequestMapping("/dailylostdetail")
    public ModelAndView dailylostdetail(){return new ModelAndView("alarminfos/dailylostdetail");}



    @Resource
    private DPumpDigitShiftService dPumpDigitShiftService;
    @RequestMapping( value = "/getPumpList",method = RequestMethod.GET)
    @ResponseBody()
    public ResultMsg GetPumpList(@RequestParam(value = "shift", required = false) String shift){
        ResultMsg result=dPumpDigitShiftService.selectByShift(shift);
        return  result;
    }

    @Resource
    private DTankShiftService dTankShiftService;
    @RequestMapping( value = "/getTankShift",method = RequestMethod.GET)
    @ResponseBody()
    public ResultMsg GetTankShift(@RequestParam(value = "shift", required = false) String shift){
        ResultMsg result=dTankShiftService.selectByShift(shift);
        return  result;
    }

    @Resource
    private DopotCountService dopotCountService;
    @RequestMapping( value = "/getOpot",method = RequestMethod.GET)
    @ResponseBody()
    public ResultMsg GetOpot(@RequestParam(value = "shift", required = false) String shift){
        ResultMsg result=dopotCountService.selectByShift(shift);
        return  result;
    }
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
        ResultMsg resultJson=new ResultMsg();
        resultJson.setData(list);
        resultJson.setRows(list);
        resultJson.setTotal(alarmGaTContrastService.selectGatAllInfo(begin, end, oucode, result).size());
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
        HashMap hashMap = new HashMap();
        hashMap.put("oucode", oucode+"%");
        List<HashMap<String,Object>> listcount=ossMonitorTimeInventoryMapper.selectPageCount(hashMap);

        if (list!=null)
        {
            Map<String, Object> result = new HashMap<String, Object>() ;
            result.put("total",listcount.size());
            result.put("rows", list);
            return result;
        }
        return  null;
    }

    //region 获取远程盘点历史记录信息 xhz 2015-12-23
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
    @Resource
    private oss_daily_StationShiftInfoMapper ossDailyStationShiftInfoMapper;
    @Resource
    private DStationShiftInfoService dStationShiftInfoService;
    //班报
    @RequestMapping("/ExcelShift")
    @ResponseBody
    public void ExcelShift(HttpServletResponse response, String oucode, String begin, String end,String FileName)
    {
        response.setContentType("application/binary;charset=ISO8859_1");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            String fileName =  java.net.URLDecoder.decode(FileName, "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
            String[] titles = { "油站名称", "班次", "接班人","接班时间","交班人","交班时间" };
            HashMap map = new HashMap();
            map.put("begin", begin);
            map.put("end", end);
            map.put("oucode", oucode + "%");
            List<HashMap<String,Object>> list=ossDailyStationShiftInfoMapper.selectShiftInfo(map);
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
            dStationShiftInfoService.YZBB(list, titles, outputStream);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出静态罐存
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
            String[] titles = { "油站名称", "油罐编号", "泄漏状态","泄漏速率","起始日期","起始油水总高","起始水高","起始5点温度1","起始5点温度2","起始5点温度3","起始5点温度4","起始5点温度5","结束时间","结束油水总高","结束水高","结束5点温度1","结束5点温度2","结束5点温度3","结束5点温度4","结束5点温度5" };

            List<HashMap<String, Object>> list=alarmMeasureLeakService.selectmeasurebywhere(start, end, oucode);
            for (HashMap<String,Object> hashMap:list) {
                oss_sys_OrgUnit model=sysOrgUnitService.selectByOUCode(hashMap.get("OUCode").toString());
                if(model!=null) {
                    hashMap.put("ouname", model.getOuname());
                }
                else
                {
                    hashMap.put("ouname", "");
                }

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
    //region 导出动态罐存 xhz 2015-12-21
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
            String[] titles = { "油站名称", "油罐编号", "第一次检测时间","第一次检测库存","第二次检测时间","第二次检测库存","两次检测时间内销量","间隔时间","差异","状态" };
            List<HashMap<String, Object>> list=alarmGaTContrastService.selectGatAllInfo(start, end, oucode, result);

            alarmGaTContrastService.ExportGatContrast(list, titles, outputStream);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //end region
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
            String[] titles = { "油站名称", "出库单号", "原发升数","实收升数","损耗量","损耗率"};
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

    //end region
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
            String[] titles = { "油站名称", "油管编号", "油品","接班油高（mm）","接班油量（L）","交班油高（mm）","测量交班油量","实际交班油量","交班水高（mm）","交班水量（L）","交班温度","卸油量","销售量","库存","损溢","状态"};
            List<HashMap<String, Object>> list=alarmShiftLostService.selectshiftlostbywhere(start, end, oucode);
            for (HashMap<String,Object> hashMap:list) {
                oss_sys_OrgUnit model=sysOrgUnitService.selectByOUCode(hashMap.get("OUCode").toString());
                if(model!=null) {
                    hashMap.put("ouname", model.getOuname());
                }
                else
                {
                    hashMap.put("ouname", "");
                }
                if ("1".equals(hashMap.get("State").toString()))
                {
                    hashMap.put("cnstate","已处理");
                }
                else
                {
                    hashMap.put("cnstate","未处理");
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
            }
            alarmShiftLostService.ExportShiftLost(list, titles, outputStream);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }



    //导出静态罐存
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
            String[] titles = { "油站名称", "上日罐存(帐存)", "出库单号","进货数量","本日付出","本日库存","实测库存","损溢量","损溢率" };
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date=new Date();
            date = fmt.parse(end);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
            end = fmt.format(calendar.getTime());
            List<HashMap<String, Object>> list=dDailyBalanceService.selectbalancebywhere(start, end, oucode);
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
            dDailyBalanceService.ExportBalance(list, titles, outputStream);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //end region
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

        List<HashMap<String, Object>> list = alarmMeasureLeakService.querypage(intPage, intPageSize,oucode,start,end);
        for (HashMap<String,Object> hashMap:list) {
           oss_sys_OrgUnit model=sysOrgUnitService.selectByOUCode(hashMap.get("OUCode").toString());
            if(model!=null) {
                hashMap.put("ouname", model.getOuname());
            }
            else
            {
                hashMap.put("ouname", "");
            }
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
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        try {
            date = fmt.parse(end);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
            end = fmt.format(calendar.getTime());

        }
        catch (Exception ex)
        {

        }
        List<HashMap<String, Object>> list = dDailyBalanceService.selectbalancebywhere(start, end, oucode);
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
            result.setTotal(alarmMeasureLeakService.selectmeasurebywhere(start, end, oucode).size());
            return result;
        }
        return null;
    }

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

        List<HashMap<String, Object>> list = alarmOilInContrastService.querypage(intPage, intPageSize,oucode,start,end);
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
            result.setTotal(alarmOilInContrastService.selectoilincontrastbywhere(start, end, oucode).size());
            return result;
        }
        return null;
    }

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

        List<HashMap<String, Object>> list = alarmShiftLostService.querypage(intPage, intPageSize,oucode,start,end);
        for (HashMap<String,Object> hashMap:list) {
            oss_sys_OrgUnit model=sysOrgUnitService.selectByOUCode(hashMap.get("OUCode").toString());
            if(model!=null) {
                hashMap.put("ouname", model.getOuname());
            }
            else
            {
                hashMap.put("ouname", "");
            }
            if ("1".equals(hashMap.get("State").toString()))
            {
                hashMap.put("cnstate","已处理");
            }
            else
            {
                hashMap.put("cnstate","未处理");
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
        }

        if(list!=null){
            ResultMsg result = new ResultMsg();
            result.setData(list);
            result.setRows(list);
            result.setTotal(alarmShiftLostService.selectshiftlostbywhere(start, end, oucode).size());
            return result;
        }
        return null;
    }
    //region 获取日结损益信息 xhz 2015-12-23
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
        for (HashMap<String,Object> hashMap:list) {
            oss_sys_OrgUnit model=sysOrgUnitService.selectByOUCode(hashMap.get("OUCode").toString());
            if(model!=null) {
                hashMap.put("ouname", model.getOuname());
            }
            else
            {
                hashMap.put("ouname", "");
            }
            HashMap map=new HashMap();
            map.put("oilno",hashMap.get("OilNo").toString());
            if (ossSysmanageOilTypeMapper.selectByoilNo(map)!=null) {
                hashMap.put("oilname",ossSysmanageOilTypeMapper.selectByoilNo(map) );
            }
            else
            {
                hashMap.put("oilname","");
            }
        }
        if(list!=null){
            ResultMsg result = new ResultMsg();
            result.setData(list);
            result.setRows(list);
            HashMap hashMap = new HashMap();
            hashMap.put("begin",begin);
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
    //region 导出日结损益信息 xhz 2015-12-23
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
            String[] titles = { "油站名称", "上日罐存(帐存)", "出库单号","进货数量","本日付出","本日库存","实测库存","损溢量","损溢率" };
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
            map.put("oucode", oucode + "%");
            List<HashMap<String, Object>> list=alarmDailyLostService.selectDailyLost(map);
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
            alarmDailyLostService.ExportDailyLost(list, titles, outputStream);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
//#endregion  x

    //region  获取库存报警信息 xhz
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

    //region 导出库存报警信息 xhz
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
            String[] titles = { "油站名称", "油管编号", "油品类型","报警类型","开始报警时间","结束报警时间"};
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date=new Date();
            date = fmt.parse(end);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
            end = fmt.format(calendar.getTime());
            List<HashMap<String, Object>> list=alarmInventoryService.selectInventoryAllInfo(begin, end, oucode, alarmtype);
            alarmInventoryService.ExportIntenvory(list, titles, outputStream);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //endregion
}
