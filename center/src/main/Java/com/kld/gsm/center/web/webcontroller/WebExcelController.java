package com.kld.gsm.center.web.webcontroller;

import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.service.AlarmEquipmentService;
import com.kld.gsm.center.service.AlarmSaleOutService;
import com.kld.gsm.center.service.ExportExcelService;
import com.kld.gsm.center.service.SysDictService;
import com.mangofactory.swagger.annotations.ApiIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fangzhun on 2015/12/5.
 */
@Controller
@ApiIgnore
@RequestMapping("/web/export")
public class WebExcelController {
      @Resource
      private AlarmSaleOutService alarmSaleOutService;
      @Resource
      private AlarmEquipmentService  alarmEquipmentService;
      @Resource
      private SysDictService sysDictService;
      @Resource
      private ExportExcelService service;
      //脱销预警
      @RequestMapping("/TXYJexportExcel")
      @ResponseBody
      public void TXYJexportExcel(HttpServletResponse response, String OilNo, String StartAlarmTime, String EndAlarmTime,String FileName) {
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
             List<HashMap<String,Object>> list=alarmSaleOutService.selectSaleOut(hashmap);
             //配送建议阀值
             int PSJYFZ= sysDictService.selectPSYJFZ();

             for (HashMap<String,Object> hashMap:list) {
                 if(Integer.parseInt(hashMap.get("PredictHours").toString())>=PSJYFZ){
                     hashMap.put("PSYJ","一般");
                 }
                 else{
                     hashMap.put("PSYJ","紧急");
                 }
             }
             service.TXYJexportExcel(list, titles, outputStream);
          }
           catch (IOException e) {
             e.printStackTrace();
         }
     }


    //设备故障报警
    @RequestMapping("/SBGZYJexportExcel")
    @ResponseBody
    public void SBGZYJexportExcel(HttpServletResponse response, String FailureType, String StartAlarmTime, String EndAlarmTime,String FileName,String oucode) {
        response.setContentType("application/binary;charset=ISO8859_1");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            String fileName =  java.net.URLDecoder.decode(FileName, "ISO8859_1"); //new String(("导出excel例子").getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
            String[] titles = { "油站名称", "油罐编号", "开始报警时间","结束报警时间","故障类型","设备代码","设备品牌","探棒型号","备注" };
            HashMap hashmap=new HashMap();
            hashmap.put("failuretype",FailureType);
            hashmap.put("start", StartAlarmTime);
            hashmap.put("end", EndAlarmTime);
            if (oucode!=null && oucode!="") {
                hashmap.put("oucode", oucode + "%");
            }
            else
            {
                hashmap.put("oucode", oucode);
            }
            List<HashMap<String,Object>> list=alarmEquipmentService.selectiq(hashmap);

            service.SBGZYJexportExcel(list, titles, outputStream);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
