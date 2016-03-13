package com.kld.gsm.center.web.webcontroller;

import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.oss_sys_OrgUnit;
import com.kld.gsm.center.service.*;
import com.mangofactory.swagger.annotations.ApiIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/12/26.
 */
@Controller
@ApiIgnore
@RequestMapping("/web/desktop")
public class WebDeskTopController extends WebBaseController{
    @RequestMapping("/desktop")
    public ModelAndView desktop(){
        return new ModelAndView("main/desktop");
    }
    @RequestMapping("/shi")
    public ModelAndView shi(){
        return new ModelAndView("main/shi");
    }
    @RequestMapping("/xian")
      public ModelAndView xian(){
        return new ModelAndView("main/xian");
    }
    @RequestMapping("/test")
    public ModelAndView test(){
        return new ModelAndView("main/test");
    }





    @RequestMapping("/city")
    public String city(ModelMap modelMap,String pcode){

        modelMap.addAttribute("pcode", pcode);
        return "main/city";
       // return new ModelAndView("main/city");
    }


    @RequestMapping("/county")
    public String county(ModelMap modelMap,String pcode){

        modelMap.addAttribute("pcode", pcode);
        return "main/county";
        // return new ModelAndView("main/city");
    }



    @RequestMapping("/station")
    public String station(ModelMap modelMap,String pcode, String vpage){

        modelMap.addAttribute("pcode", pcode);

        if (vpage!=null && vpage!=null) {
            //加载罐枪实时监控信息
            if(vpage.equals("gqss"))
            {
                modelMap.addAttribute("gqss", monitorSummaryService.GetGqss(pcode));
            }
            return "main/"+vpage;
        }
        else
        {
            return "main/station";
        }

        // return new ModelAndView("main/city");
    }




    //region 按照油站获取脱销预警的报警次数 xhz 2015-12-27
    @Resource
    private AlarmSaleOutService alarmSaleOutService;
    @RequestMapping("/selectAlarmSaleOutCount")
    @ResponseBody
    public ResultMsg selectAlarmSaleOutCount(HttpServletRequest request,String begin, String end,String oucode){
        /*List<HashMap<String, Object>> list = alarmSaleOutService.queryJYCountPrepayPageList(null, StartAlarmTime, EndAlarmTime, null, null, oucode);//传进去的page要进行处
        ResultMsg result = new ResultMsg();
        if(list!=null){
            result.setTotal(list.size());
        }
        else
        {
            result.setTotal(0);
        }
        return result;*/
        // return null;

        HashMap hashMap = new HashMap();
        hashMap.put("start",begin);
        hashMap.put("end", end);
        if (oucode!=null && oucode!=null) {
            hashMap.put("oucode", oucode + "%");
        }
        else
        {
            hashMap.put("oucode", oucode);
        }
        ResultMsg resultJson=new ResultMsg();
        List<HashMap<String, Object>> SaleOutlist=alarmSaleOutService.selectSaleOut(hashMap);
        ResultMsg result=new ResultMsg();
        if (SaleOutlist!=null) {
            result.setTotal(SaleOutlist.size());
        }
        else
        {
            result.setTotal(0);
        }
        return result;

    }
    //endregion




    //region //库存、销量、进货量、提枪次数、液位仪使用率
    @Resource
    private MonitorSummaryService monitorSummaryService;
    @RequestMapping("/selectMonitor_Summary")
    @ResponseBody
    public ResultMsg selectMonitor_Summary(HttpServletRequest request,String oucode){

        HashMap hashMap = new HashMap();
        if (oucode!=null && oucode!=null) {
            hashMap.put("oucode", oucode + "%");
        }
        else
        {
            hashMap.put("oucode", oucode);
        }
//        List<HashMap<String, Object>> SaleOutlist=alarmSaleOutService.selectSaleOut(hashMap);
        List<HashMap<String, Object>> mylist= monitorSummaryService.selectSummary(hashMap);
        ResultMsg result=new ResultMsg();
        result.setRows(mylist);

        return result;

    }
    //endregion



    //region //得到整点库存列表
  //  private MonitorSummaryService monitorSummaryService;
    @RequestMapping("/getInventoryList")
    @ResponseBody
    public ResultMsg getInventoryList(HttpServletRequest request,@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "rows",required = false) Integer rows,String begin,String end,String oucode){

        ResultMsg result=new ResultMsg();

        if (page != null && rows != null) {
            page = page < 1 ? 1 : page;
            int firstRow = (page - 1) * rows;
            HashMap hashMap = new HashMap();
            hashMap.put("firstRow", firstRow);
            hashMap.put("pageSize", rows);
            hashMap.put("begin",begin);
            hashMap.put("end", end);

            if (oucode!=null && oucode!=null) {
                hashMap.put("oucode", oucode + "%");
            }
            else
            {
                hashMap.put("oucode", oucode);
            }

            List<HashMap<String, Object>> mylist= monitorSummaryService.getInventoryList(hashMap);

            result.setRows(mylist);
            result.setTotal(monitorSummaryService.getInventoryAllList(hashMap).size());

           // return result;
        }

        return result;

    }
    //endregion



    //region //得到整点库存列表
    //  private MonitorSummaryService monitorSummaryService;
    @RequestMapping("/getInventoryAllList")
    @ResponseBody
    public ResultMsg getInventoryAllList(HttpServletRequest request,String begin,String end,String oucode){

            ResultMsg result=new ResultMsg();
            HashMap hashMap = new HashMap();

            hashMap.put("begin",begin);
            //hashMap.put("begin","20150201");
            hashMap.put("end", end);

            if (oucode!=null && oucode!=null) {
                hashMap.put("oucode", oucode + "%");
            }
            else
            {
                hashMap.put("oucode", oucode);
            }
            List<HashMap<String, Object>> mylist= monitorSummaryService.getInventoryAllList(hashMap);

            List xlist=new ArrayList();
            List ylist=new ArrayList();
            for(HashMap<String, Object> itemdata:mylist)
            {

                xlist.add(itemdata.get("alltime"));
               // double dy=3.22;
                double  dy  =  Double.parseDouble(itemdata.get("OilCubage").toString()) ;
                String Ystr= new java.text.DecimalFormat("#.00").format(dy);
                ylist.add(Ystr);

            }


           HashMap<String, Object> mynewlist=new HashMap<String, Object>();
           mynewlist.put("xlist", xlist);
           mynewlist.put("ylist", ylist);

           result.setData(mynewlist);
           result.setTotal(mylist.size());
            // return result;
           return result;

    }
    //endregion


    @RequestMapping("/downloadzdkc")
    @ResponseBody
    public void downloadzdkc(HttpServletRequest request,String begin,String end,String oucode,String FileName)
    {

        HashMap hashMap = new HashMap();

        hashMap.put("begin",begin);
        hashMap.put("end", end);

        if (oucode!=null && oucode!=null) {
            hashMap.put("oucode", oucode + "%");
        }
        else
        {
            hashMap.put("oucode", oucode);
        }

        //FileName="mydownload";
        response.setContentType("application/binary;charset=ISO8859_1");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            String fileName =  java.net.URLDecoder.decode(FileName, "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
            String[] titles = { "油罐编号", "油品", "时间","标准体积（L）","油水总高（mm）" ,"净油体积（L）", "水高（mm）", "水量","平均温度（℃）","空体积（L）"};
            List<HashMap<String, Object>> list= monitorSummaryService.getInventoryAllList(hashMap);
            monitorSummaryService.downloadzdkc(list, titles, outputStream);
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }




    //构造导航信息

    @RequestMapping("/getMenu")
    @ResponseBody
    public ResultMsg getMenu(HttpServletRequest request,String oucode){

        HashMap hashMap = new HashMap();
        if (oucode!=null && oucode!=null) {

            int oucodelenth=oucode.length();
            if (oucodelenth==6)  //最大12 ，3位一级
            {
                oss_sys_OrgUnit myorg= sysOrgUnitService.selectByOUCode(oucode);
                hashMap.put("sheng", myorg.getOuname() );
            }
            else if(oucodelenth==9)
            {
                oss_sys_OrgUnit sheng= sysOrgUnitService.selectByOUCode(oucode.substring(0,6));
                hashMap.put("sheng", sheng.getOuname() );
                oss_sys_OrgUnit qu= sysOrgUnitService.selectByOUCode(oucode);
                hashMap.put("qu", qu.getOuname() );
            }
            else if(oucodelenth==12)
            {
                oss_sys_OrgUnit sheng= sysOrgUnitService.selectByOUCode(oucode.substring(0,6));
                hashMap.put("sheng", sheng.getOuname() );
                oss_sys_OrgUnit qu= sysOrgUnitService.selectByOUCode(oucode.substring(0,9));
                hashMap.put("qu", qu.getOuname() );
                oss_sys_OrgUnit zhan= sysOrgUnitService.selectByOUCode(oucode);
                hashMap.put("zhan", zhan.getOuname() );
            }


        }

        ResultMsg result=new ResultMsg();
        result.setData(hashMap);
        result.setTotal(hashMap.size());
        result.setMsg(hashMap.size() + "");
        return result;

    }
    //endregion



    //region //库存、销量、进货量、提枪次数、液位仪使用率

    @RequestMapping("/selectSummaryCountbyParent")
    @ResponseBody
    public ResultMsg selectSummaryCountbyParent(HttpServletRequest request,@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "rows",required = false) Integer rows,String Pcode){

        try {
            if (page != null && rows != null) {
                page = page < 1 ? 1 : page;
                int firstRow = (page - 1) * rows;
                HashMap hashMap = new HashMap();
                hashMap.put("firstRow", firstRow);
                hashMap.put("pageSize", rows);

                if (Pcode!=null && Pcode!=null) {
                    hashMap.put("Pcode", Pcode);
                }

                List<HashMap<String, Object>> mylist= monitorSummaryService.selectSummarybyParent(hashMap);
                ResultMsg result=new ResultMsg();
                result.setRows(mylist);
                result.setTotal(monitorSummaryService.selectSummaryCountbyParent(hashMap).size());

                return result;
            }
            return null;
        }
        catch (Exception ex)
        {
            return  null;
        }








    }
    //endregion



    //region //库存、销量、进货量、提枪次数、液位仪使用率

    @RequestMapping("/selectSummarybyParent")
    @ResponseBody
    public ResultMsg selectSummarybyParent(HttpServletRequest request,@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "rows",required = false) Integer rows,String Pcode){

        try {
            if (page != null && rows != null) {
                page = page < 1 ? 1 : page;
                int firstRow = (page - 1) * rows;
                HashMap hashMap = new HashMap();
                hashMap.put("firstRow", firstRow);
                hashMap.put("pageSize", rows);

                if (Pcode!=null && Pcode!=null) {
                    hashMap.put("Pcode", Pcode);
                }

                List<HashMap<String, Object>> mylist= monitorSummaryService.selectSummarybyParent(hashMap);
                ResultMsg result=new ResultMsg();
                result.setRows(mylist);
                result.setTotal(mylist.size());

                return result;
            }
            return null;
        }
        catch (Exception ex)
        {
            return  null;
        }








    }
    //endregion






    @RequestMapping("/testAddSummaryData")
    public  void  testAddSummaryData()
    {
        monitorSummaryService.AddSummaryData();

    }





    //region 交接班损益预警当天报警次数 xhz 2015-12-27
    @Resource
    private AlarmShiftLostService alarmShiftLostService;
    @RequestMapping( value = "/selectShiftLostCout",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg selectShiftLostCout(HttpServletResponse response,String start,String end,String oucode)
    {
       List<HashMap<String,Object>> listShift=alarmShiftLostService.selectshiftlostbywhere(start, end, oucode);
        ResultMsg result=new ResultMsg();
        if (listShift!=null) {
            result.setTotal(listShift.size());
        }
        else
        {
            result.setTotal(0);
        }
        return result;
    }
    //endregion
    //region 日结损益预警次数 xhz 2015-12-27
    @Resource
    private AlarmDailyLostService alarmDailyLostService;
    @RequestMapping( value = "/selectDailyLostCout",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg selectDailyLostCout(HttpServletResponse response,String begin,String end,String oucode)
    {
        HashMap hashMap = new HashMap();
        hashMap.put("begin",begin);
        hashMap.put("end", end);
        if (oucode!=null && oucode!=null) {
            hashMap.put("oucode", oucode + "%");
        }
        else
        {
            hashMap.put("oucode", oucode);
        }
        List<HashMap<String,Object>> listDailyLost= alarmDailyLostService.selectDailyLost(hashMap);
        ResultMsg result=new ResultMsg();
        if (listDailyLost!=null) {
            result.setTotal(listDailyLost.size());
        }
        else
        {
            result.setTotal(0);
        }
        return result;
    }
    //endregion



    //region 进油损益预警次数
    @Resource
    private AlarmOilInContrastService alarmOilInContrastService;
    @RequestMapping( value = "/selectOilInContrastCout",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg selectOilInContrastCout(HttpServletResponse response,String begin,String end,String oucode)
    {

        List<HashMap<String,Object>> lisOilInLost= alarmOilInContrastService.selectoilincontrastbywhere(begin,end,oucode);
        ResultMsg result=new ResultMsg();
        if (lisOilInLost!=null) {
            result.setTotal(lisOilInLost.size());
        }
        else
        {
            result.setTotal(0);
        }
        return result;
    }
    //endregion





    //region  油管报警次数 xhz 2015-12-27
    @Resource
    private AlarmInventoryService alarmInventoryService;
    @RequestMapping( value = "/selectAalrmInventoryCount",method = RequestMethod.GET)
    @ResponseBody  //Integer current, Integer pagesize, String begin, String end, String oucode,String result
    public ResultMsg selectAalrmInventoryCount(@RequestParam(value = "begin",required = false) String begin,@RequestParam(value = "end",required = false) String end,@RequestParam(value = "oucode",required = false) String oucode){

        List<HashMap<String, Object>> list=alarmInventoryService.selectInventoryAllInfo(begin, end, oucode, null);
        ResultMsg resultJson=new ResultMsg();
        if (list!=null) {
            resultJson.setTotal(list.size());
        }
        else
        {
            resultJson.setTotal(0);
        }
        return  resultJson;
    }
    //endregion
    //region 设备故障当天报警次数 xhz 2015-12-27
    @Resource
    private AlarmEquipmentService alarmEquipmentService;
    @RequestMapping( value = "/selectEqCout",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg selectEqCout(HttpServletResponse response,String start,String end,String oucode)
    {
        List<HashMap<String,Object>> list=alarmEquipmentService.selectEqbywhere(start, end, oucode);
        ResultMsg result=new ResultMsg();
        if (list!=null) {
            result.setTotal(list.size());
        }
        else
        {
            result.setTotal(0);
        }
        return result;
    }
    //endregion
    //region 交接班损溢预警次数 xhz 2015-12-27
    @RequestMapping( value = "/selectAlarmCount",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg selectAlarmCount(HttpServletResponse response,String start,String oucode)
    {
        List<HashMap<String,Object>> list=alarmShiftLostService.selectAlarmCount(start, oucode);
        ResultMsg result=new ResultMsg();
        if (list!=null)
        {
            result.setTotal(list.size());
            result.setRows(list);
        }

        return result;
    }
    //endregion
    //region 获取汽油柴油销量 xhz 2015-12-28
    @Resource
    private DTradeAccountService dTradeAccountService;
    @RequestMapping(value = "/selectOilLiter",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg selectOilLiter(HttpServletResponse response,String oiltype,String start,String end,String oucode)
    {
        List<HashMap<String,Object>> list=dTradeAccountService.selectOilLiter(oiltype, start, end, oucode);
        ResultMsg result=new ResultMsg();
        if (list!=null)
        {
            if (list.size()>0) {
                result.setRows(list);
                result.setTotal(list.size());
            }
        }
        return result;
    }
    //endregion
    //region 获取柴油汽油库存量 xhz 2015-12-28
    @Resource
    private TimeInventoryService timeInventoryService;
    @RequestMapping(value = "/selectInventory",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg selectInventory(HttpServletResponse response,String oiltype,String oucode)
    {
        List<HashMap<String,Object>> list=timeInventoryService.selectInventory(oiltype,oucode);
        ResultMsg result=new ResultMsg();
        long oilL=0;
        if (list!=null)
        {
            if (list.size()>0) {
               for (HashMap<String,Object> item:list)
               {
                  oilL+=Double.parseDouble(item.get("OilL").toString());
               }
            }
            else
            {
                oilL=0;
            }
        }
        else
        {
            oilL=0;;
        }
        result.setTotal(oilL);
        return result;
    }
    //endregion
    //region 获取提枪次数 xhz 2015-12-28
    @RequestMapping(value = "/selectTqCount",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg selectTqCount(HttpServletResponse response,String start,String oiltype,String end,String oucode)
    {
        List<HashMap<String,Object>> list=dTradeAccountService.selectTqCount(start,oiltype, end, oucode);
        ResultMsg result=new ResultMsg();
        if (list!=null)
        {
            if (list.size()>0) {
                result.setRows(list);
                result.setTotal(list.size());
            }
        }
        return result;
    }
    //endregion
    //region 获取液位仪使用率 xhz 2015-12-28
    @Resource
    private SysOrgUnitService sysOrgUnitService;
    @RequestMapping(value = "/selectYwySYL",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg selectYwySYL(HttpServletResponse response,String oucode)
    {
        List<HashMap<String,Object>> list=timeInventoryService.selectUploadInventory(oucode);
        List<HashMap<String,Object>> listOU=sysOrgUnitService.selectOUInfo(oucode);
        ResultMsg result=new ResultMsg();
        if (list!=null&&listOU!=null)
        {
            if(listOU.size()==0){
                result.setTotal(0);
            }else {
                result.setTotal(list.size() / listOU.size());
            }
        }
        return result;
    }
    //endregion


    //region 获取在途汽油柴油量 xhz 2015-12-28
    @Resource
    private AcceptanceDeliveryBillService acceptanceDeliveryBillService;
    @RequestMapping(value = "/selectDBill",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg selectDBill(HttpServletResponse response,String oiltype,String oucode)
    {
        List<HashMap<String,Object>> list=acceptanceDeliveryBillService.selectDBill(oiltype, oucode);
        ResultMsg result=new ResultMsg();
        if (list!=null)
        {
            if (list.size()>0) {
                result.setRows(list);
                result.setTotal(list.size());
            }
        }
        return result;
    }
    //endregion

    //region 获取汽油柴油进货量 xhz 2015-12-28

    @RequestMapping(value = "/selectJHL",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg selectJHL(HttpServletResponse response,String oiltype,String start,String end,String oucode)
    {
        List<HashMap<String,Object>> list=acceptanceDeliveryBillService.selectJHL(oiltype, start, end, oucode);
        ResultMsg result=new ResultMsg();
        if (list!=null)
        {
            if (list.size()>0) {
                result.setRows(list);
                result.setTotal(list.size());
            }
        }
        return result;
    }
    //endregion

    //region 获取汽油柴油已验收 xhz 2015-12-28
@Resource
private AcceptanceService acceptanceService;
    @RequestMapping(value = "/selectYYS",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg selectYYS(HttpServletResponse response,String oiltype,String start,String end,String oucode)
    {
        List<HashMap<String,Object>> list=acceptanceService.selectYYS(oiltype, start, end, oucode);
        ResultMsg result=new ResultMsg();
        if (list!=null)
        {
            if (list.size()>0) {
                result.setRows(list);
                result.setTotal(list.size());
            }
        }
        return result;
    }
    //endregion

    //region 按照名称获取油量
    @RequestMapping(value = "/selectOilLiterByName",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg selectOilLiterByName(HttpServletResponse response,String oilname,String start,String end, String oucode) {
        List<HashMap<String,Object>> list=dTradeAccountService.selectOilLiterByName(oilname, start, end, oucode);
        ResultMsg result=new ResultMsg();
        if (list!=null)
        {
            if (list.size()>0) {
                result.setRows(list);
                result.setTotal(list.size());
            }
        }
        return result;
    }
    //endregion


    //region 获取柴油汽油卡销比
    @RequestMapping(value = "/selectKXB",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg selectKXB(HttpServletResponse response,String oiltype,String type,String oucode)
    {
        List<HashMap<String,Object>> list=dTradeAccountService.selectNewLiter(oiltype, type, oucode);//获取最新的卡销量
        List<HashMap<String,Object>> listtotal=dTradeAccountService.selectNewLiter(oiltype,null,oucode);
        ResultMsg result=new ResultMsg();
        double ListertotalByType=0;
        if (list!=null)
        {
            if (list.size()>0) {
                for (HashMap<String,Object> item:list) {
                    ListertotalByType+=Double.parseDouble(item.get("Liter").toString());
                }
            }
        }
        double Listertotal=0;
        if (listtotal!=null)
        {
            if (list.size()>0)
            {
                for (HashMap<String,Object> item:listtotal) {
                    Listertotal+=Double.parseDouble(item.get("Liter").toString());
                }
            }
        }
        long j=(long)(ListertotalByType/Listertotal)*100;
        double kxb=(ListertotalByType/Listertotal)*100;
        String newkxb= new java.text.DecimalFormat("#.00").format(kxb);


//        BigDecimal b = new BigDecimal(kxb);
//        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

       // result.setTotal(f1);
      //  result.setdata(newkxb);
        result.setData(newkxb);
        return result;
    }
    //endregion


    //region 获取柴油汽油卡销比
    @RequestMapping(value = "/selectAllKXB",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg selectAllKXB(HttpServletResponse response,String oiltype,String type,String oucode)
    {
        List<HashMap<String,Object>> list=dTradeAccountService.selectNewLiter(null,type,oucode);//获取最新的卡销量
        List<HashMap<String,Object>> listtotal=dTradeAccountService.selectNewLiter(null,null,oucode);
        ResultMsg result=new ResultMsg();
        double ListertotalByType=0;
        if (list!=null)
        {
            if (list.size()>0) {
                for (HashMap<String,Object> item:list) {
                    ListertotalByType+=Double.parseDouble(item.get("Liter").toString());
                }
            }
        }
        double Listertotal=0;
        if (listtotal!=null)
        {
            if (list.size()>0)
            {
                for (HashMap<String,Object> item:listtotal) {
                    Listertotal+=Double.parseDouble(item.get("Liter").toString());
                }
            }
        }
        long j=(long)(ListertotalByType/Listertotal)*100;
        result.setTotal(j);
        return result;
    }
    //endregion
}
