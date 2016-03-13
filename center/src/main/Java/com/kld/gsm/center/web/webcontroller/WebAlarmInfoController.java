package com.kld.gsm.center.web.webcontroller;

import com.kld.gsm.center.dao.oss_alarm_SaleOutMapper;
import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.oss_alarm_SaleOut;
import com.kld.gsm.center.domain.oss_sysmanage_City;
import com.kld.gsm.center.service.AlarmEquipmentService;
import com.kld.gsm.center.service.AlarmSaleOutService;
import com.kld.gsm.center.service.SysDictService;
import com.mangofactory.swagger.annotations.ApiIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fangzhun on 2015/12/3.
 */
@Controller
@ApiIgnore
@RequestMapping("/web/alarminfo")
public class WebAlarmInfoController  extends WebBaseController {
    @Resource
    private AlarmSaleOutService alarmSaleOutService;
    @Resource
    private SysDictService sysDictService;
    @Resource
    private AlarmEquipmentService alarmEquipmentService;

    /*@RequestMapping("/selectSaleOut")
    @ResponseBody
    public List<HashMap<String,Object>>  selectSaleOut(@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "rows", required = false) Integer rows,@RequestParam(value = "OilNo", required = false) String OilNo,
                                    @RequestParam(value="StartAlarmTime" , required = false) String StartAlarmTime, @RequestParam(value = "EndAlarmTime", required = false) String EndAlarmTime){
        HashMap hashmap=new HashMap();
        hashmap.put("oilno",OilNo);
        hashmap.put("start", StartAlarmTime);
        hashmap.put("end", EndAlarmTime);
        List<HashMap<String,Object>> list=alarmSaleOutService.selectSaleOut(hashmap);
        return list;
    }

    @RequestMapping("/selectSaleOutPageList")
    @ResponseBody
    public Map<String, Object> selectSaleOutPageList(@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "rows",required = false) Integer rows,@RequestParam(value = "oucode",required = false) String oucode){{
        //设置当前页
        int intPage=page==null||page<=0?1:page;
        //设置每页显示的数量
        int intPageSize=rows==null||rows<=0?10:rows;

        //配送建议阀值
        //int PSJYFZ= sysDictService.selectPSYJFZ();
        List<HashMap<String, Object>> list = alarmSaleOutService.queryPrepayPageList(intPage, intPageSize, oucode, begin, end);//传进去的page要进行处理
*//*        for (HashMap<String,Object> hashMap:list) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String datetime= sdf.format(hashMap.get("MesasureTime"));
            hashMap.remove("MesasureTime");
            hashMap.put("MesasureTime", datetime);
           if(Integer.parseInt(hashMap.get("PredictHours").toString())>=PSJYFZ){
               hashMap.put("PSYJ","一般");
           }
           else{
               hashMap.put("PSYJ","紧急");
           }
        }*//*


        if(list!=null){
            Map<String, Object> result = new HashMap<String, Object>() ;
            result.put("total",alarmSaleOutService.queryPrepayCount(OilNo,StartAlarmTime,EndAlarmTime));
            result.put("rows", list);
            return result;//这个就是你在ajax成功的时候返回的数据，我在那边进行了一个对象封装
        }
        return null;

    }
@Resource
private oss_alarm_SaleOutMapper ossAlarmSaleOutMapper;
    @RequestMapping("/selectJySaleOutPageList")
    @ResponseBody
    public Map<String, Object> selectJySaleOutPageList(HttpServletRequest request,Integer page,Integer rows,String OilNo,String StartAlarmTime, String EndAlarmTime,
                                                    String PSJY,oss_alarm_SaleOut pr,String oucode){
        //设置当前页
        int intPage=page==null||page<=0?1:page;
        //设置每页显示的数量
        int intPageSize=rows==null||rows<=0?10:rows;

        //配送建议阀值
        int PSJYFZ= sysDictService.selectPSYJFZ();
        List<HashMap<String, Object>> list = alarmSaleOutService.queryJYPrepayPageList(intPage, intPageSize, OilNo, StartAlarmTime,EndAlarmTime,PSJY, PSJYFZ,oucode);//传进去的page要进行处理
        for (HashMap<String,Object> hashMap:list) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String datetime= sdf.format(hashMap.get("MesasureTime"));
            hashMap.remove("MesasureTime");
            hashMap.put("MesasureTime", datetime);
            if(Integer.parseInt(hashMap.get("PredictHours").toString())>=PSJYFZ){
                hashMap.put("PSYJ","一般");
            }
            else{
                hashMap.put("PSYJ","紧急");
            }
        }


        if(list!=null){
            Map<String, Object> result = new HashMap<String, Object>() ;
            result.put("total",alarmSaleOutService.queryJYCountPrepayPageList(OilNo, StartAlarmTime, EndAlarmTime, PSJY, PSJYFZ,oucode).size());
            result.put("rows", list);
            return result;//这个就是你在ajax成功的时候返回的数据，我在那边进行了一个对象封装
        }
        return null;

    }



    //脱销预警
    @RequestMapping("/excelSaleOut")
    @ResponseBody
    public void ExcelSaleOut(HttpServletResponse response, String OilNo, String StartAlarmTime, String EndAlarmTime, String PSJY,String oucode,String FileName) {
        response.setContentType("application/binary;charset=ISO8859_1");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            String fileName =  java.net.URLDecoder.decode(FileName, "ISO8859_1"); //new String(("导出excel例子").getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
            String[] titles = { "油站名称", "油品", "测量时间","当前体积","可销售体积","日常销售量","平均每小时销售量","预计销售时间","预计销售天数","配送建议" };
            HashMap hashmap=new HashMap();
            hashmap.put("oilno",OilNo);
            hashmap.put("start", StartAlarmTime);
            hashmap.put("end", EndAlarmTime);
            hashmap.put("oucode",oucode);
            //配送建议阀值
            int PSJYFZ= sysDictService.selectPSYJFZ();

            List<HashMap<String, Object>> list = alarmSaleOutService.queryJYCountPrepayPageList(OilNo, StartAlarmTime, EndAlarmTime,PSJY,PSJYFZ,oucode );

            for (HashMap<String,Object> hashMap:list) {
                if(Integer.parseInt(hashMap.get("PredictHours").toString())>=PSJYFZ){
                    hashMap.put("PSYJ","一般");
                }
                else{
                    hashMap.put("PSYJ","紧急");
                }
            }
            alarmSaleOutService.ExcelSaleOut(list, titles, outputStream);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }*/


}
