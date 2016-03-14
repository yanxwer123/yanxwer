package com.kld.gsm.center.web.controller;

import com.kld.gsm.center.domain.*;
import com.kld.gsm.center.domain.hn.HNMonitor_Oilgun;
import com.kld.gsm.center.domain.hn.HNMonitor_Tankoil;
import com.kld.gsm.center.domain.hn.JTGC;
import com.kld.gsm.center.service.*;
import com.kld.gsm.center.util.action;
import com.kld.gsm.center.util.httpClient;
import com.kld.gsm.center.util.sysOrgUnit;
/*import com.kld.gsm.util.JsonMapper;*/
import com.kld.gsm.center.util.JsonMapper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by xhz on 2015/11/18.
 * 报警提醒
 */
@Controller
@RequestMapping("/Alarm")
public class AlarmController {
    /**
     * 日结损溢预警表
     */
    @Resource
    private AlarmDailyLostService alermService;
    @RequestMapping(value = "/InsertDailyLost")
    @ResponseBody
    public Object InsertDailyLost(@RequestBody List<oss_alarm_DailyLost> dailyLost,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        try {
            for (oss_alarm_DailyLost item:dailyLost) {
                sysOrgUnit orgUnit=new sysOrgUnit();
                item.setOucode(orgUnit.GetOuCodeByNodeNo(NodeNo));
                item.setNodeno(NodeNo);
            }
            int num = alermService.AddDailyLost(dailyLost);
            if (num > 0) {

               result.setResult(true);
                //PassHn_DailyLost(dailyLost);//调用传输数据
            } else {
                result.setResult(false);
            }
            return result;
        }

        catch (Exception e)
        {
            result.setMsg(e.getMessage());
            result.setResult(false);
            return result;
        }
    }
    @Resource
    private HNGunInfoService hnGunInfoService;
    @RequestMapping(value = "/PassHn_Gun")
    @ResponseBody
    public Object PassHn_Gun(@RequestParam("NodeNo") String NodeNo)
    {
        //获取action地址
        //action ac=new action();
        //String path=ac.getUri("resource.hn.Alarm.PassHn_Gun");
        //httpClient client=new httpClient();
        //Map<String,String> hm=new HashMap<String, String>();
        //hm.put("nodeno",NodeNo);
        return  hnGunInfoService.GetGunInfo(NodeNo);
    }
    //日结损益预警表抛给湖南
    private void PassHn_DailyLost(List<oss_alarm_DailyLost> dailyLost)
    {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.hn.Alarm.PassDailyLost");
        httpClient client=new httpClient();
        Map<String,String> hm=new HashMap<String, String>();
        Result result=new Result();
        try {
            String jsonResult= client.request(path, dailyLost, hm);
            result=new JsonMapper().fromJson(jsonResult,Result.class);
            if (result.isResult())
            {
                for (oss_alarm_DailyLost item:dailyLost)
                {
                    item.setTranstatus("1");
                }
            }
        }
        catch(Exception e)
        {

        }
    }

    /**
     * 交接班损溢预警
     */
    @Resource
    private AlarmShiftLostService ShiftLostService;
    @RequestMapping(value = "/AddShifLost")
    @ResponseBody
    public Object AddShifLost(@RequestBody List<oss_alarm_ShiftLost> shiftLosts,@RequestParam("NodeNo") String NodeNo)
    {
        Result result=new Result();
        try {
            for (oss_alarm_ShiftLost item:shiftLosts) {
                sysOrgUnit orgUnit=new sysOrgUnit();
                item.setOucode(orgUnit.GetOuCodeByNodeNo(NodeNo));
                item.setNodeno(NodeNo);
            }
            int num = ShiftLostService.AddAlarmShiftLost(shiftLosts);
            if (num > 0) {
                result.setResult(true);
                //PassHN_ShiftLost(shiftLosts);//调用传输方法
            } else {
                result.setResult(false);
            }
            return result;
        }

        catch (Exception e)
        {
            result.setMsg(e.getMessage());
            result.setResult(false);
            return result;
        }
    }
    //将交接班损益数据抛给湖南
    private  void PassHN_ShiftLost(List<oss_alarm_ShiftLost> shiftLosts)
    {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.hn.Alarm.PassShiftLost");
        httpClient client=new httpClient();
        Map<String,String> hm=new HashMap<String, String>();
        Result result=new Result();
        try {
           String jsonResult= client.request(path, shiftLosts, hm);
            result=new JsonMapper().fromJson(jsonResult,Result.class);
            if (result.isResult())
            {
                for (oss_alarm_ShiftLost item:shiftLosts)
                {
                    item.setTranstatus("1");
                }
            }
        }
        catch(Exception e)
        {

        }
    }
    /**
     * 库存预警
     */
    @Resource
    private AlarmInventoryService InventoryService;
    @RequestMapping(value = "/AddInventory")
    @ResponseBody
    public Object AddInventory(@RequestBody List<oss_alarm_Inventory> inventories,@RequestParam("NodeNo") String NodeNo)
    {
        Result result=new Result();
        try {
            for (oss_alarm_Inventory item:inventories) {

                sysOrgUnit orgUnit=new sysOrgUnit();
                item.setOucode(orgUnit.GetOuCodeByNodeNo(NodeNo));
                item.setNodeno(NodeNo);
            }
            int num = InventoryService.AddInventory(inventories);
            if (num > 0) {

                result.setResult(true);
                PassHn_Inventory(inventories);//调用传输方式
            } else {
                result.setResult(false);
            }
            return result;
        }

        catch (Exception e)
        {
            result.setMsg(e.getMessage());
            result.setResult(false);
            return result;
        }
    }
    //将库存预警抛给湖南
    private void PassHn_Inventory(List<oss_alarm_Inventory> inventories)
    {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.hn.Alarm.PassInventory");
        httpClient client=new httpClient();
        Map<String,String> hm=new HashMap<String, String>();
        //hm.put("list", URLEncoder.encode(new JsonMapper().toJson(inventories)));
        Result result=new Result();
        try {
            String jsonResult= client.request(path, inventories, hm);
            result=new JsonMapper().fromJson(jsonResult,Result.class);
            if (result.isResult())
            {
                for (oss_alarm_Inventory item:inventories)
                {
                    item.setTranstatus("1");
                }
            }
        }
        catch(Exception e)
        {

        }
    }

    /**
     * 枪出罐出对比
     */
    @Resource
    private AlarmGaTContrastService GaTContrastService;
    @RequestMapping(value = "/AddGaTContrast")
    @ResponseBody
    public Object AddGaTContrast(@RequestBody List<oss_alarm_GaTContrast> gaTContrasts,@RequestParam("NodeNo") String NodeNo)
    {
        Result result=new Result();
        try {
            for (oss_alarm_GaTContrast item:gaTContrasts) {

                sysOrgUnit orgUnit=new sysOrgUnit();
                item.setOucode(orgUnit.GetOuCodeByNodeNo(NodeNo));
                item.setNodeno(NodeNo);
            }
            int num = GaTContrastService.AddGaTContrastService(gaTContrasts);
            if (num > 0) {
                result.setResult(true);
                PassHn_GaTContrast(gaTContrasts);
            } else {
                result.setResult(false);
            }
            return result;
        }

        catch (Exception e)
        {
            result.setMsg(e.getMessage());
            result.setResult(false);
            return result;
        }
    }
    //将枪出罐出对比数据抛给湖南
    private void PassHn_GaTContrast(List<oss_alarm_GaTContrast> gaTContrasts)
    {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.hn.Alarm.PassGaTContrast");
        httpClient client=new httpClient();
        Map<String,String> hm=new HashMap<String, String>();
        Result result=new Result();
        try {
            String jsonResult= client.request(path, gaTContrasts, hm);
            result=new JsonMapper().fromJson(jsonResult,Result.class);
            if (result.isResult())
            {
                for (oss_alarm_GaTContrast item:gaTContrasts)
                {
                    item.setTranstatus("1");
                }
            }
        }
        catch(Exception e)
        {

        }
    }
    /**
     * 测漏表
     */
    @Resource
    private AlarmMeasureLeakService MeasureLeakService;
    @RequestMapping(value = "/AddMeasureLeak")
    @ResponseBody
    public Object AddMeasureLeak(@RequestBody List<oss_alarm_measureLeak> measureLeaks,@RequestParam("NodeNo") String NodeNo)
    {
        Result result=new Result();
        try {
            for (oss_alarm_measureLeak item:measureLeaks) {

                sysOrgUnit orgUnit=new sysOrgUnit();
                item.setOucode(orgUnit.GetOuCodeByNodeNo(NodeNo));
                item.setNodeno(NodeNo);
            }
            int num = MeasureLeakService.AddMeasureLeak(measureLeaks);
            if (num > 0) {

                result.setResult(true);
                //PassHn_MeasureLeak(measureLeaks);//调用数据传输
            } else {
                result.setResult(false);
            }
            return result;
        }

        catch (Exception e)
        {
            result.setMsg(e.getMessage());
            result.setResult(false);
            return result;
        }
    }
    //将侧漏表数据抛给湖南
    private void PassHn_MeasureLeak(List<oss_alarm_measureLeak> measureLeaks)
    {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.hn.Alarm.PassMeasureLeak");
        httpClient client=new httpClient();
        Map<String,String> hm=new HashMap<String, String>();
        Result result=new Result();
        try {
            String jsonResult= client.request(path, measureLeaks, hm);
            result=new JsonMapper().fromJson(jsonResult,Result.class);
            if (result.isResult())
            {
                for (oss_alarm_measureLeak item:measureLeaks)
                {
                    item.setTranstatus("1");
                }
            }
        }
        catch(Exception e)
        {

        }
    }

    /**
     * 脱销预警
     */
    @Resource
    private AlarmSaleOutService SaleOutService;
    @RequestMapping(value = "/AddSaleOut")
    @ResponseBody
    public Object AddSaleOut(@RequestBody List<oss_alarm_SaleOut> saleOuts,@RequestParam("NodeNo") String NodeNo)
    {
        Result result=new Result();
        try {
            for (oss_alarm_SaleOut item:saleOuts) {

                sysOrgUnit orgUnit=new sysOrgUnit();
                item.setOucode(orgUnit.GetOuCodeByNodeNo(NodeNo));
                item.setNodeno(NodeNo);
            }
            int num = SaleOutService.AddSaleOut(saleOuts);
            if (num > 0) {

                result.setResult(true);
                //PassSaleOut(saleOuts);//调用传输数据
            } else {
                result.setResult(false);
            }
            return result;
        }

        catch (Exception e)
        {
            result.setMsg(e.getMessage());
            result.setResult(false);
            return result;
        }
    }
   //脱销预警数据抛给湖南
    private void PassSaleOut( List<oss_alarm_SaleOut> saleOuts)
    {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.hn.Alarm.PassSaleOut");
        httpClient client=new httpClient();
        Map<String,String> hm=new HashMap<String, String>();
        Result result=new Result();
        try {
            String jsonResult= client.request(path, saleOuts, hm);
            result=new JsonMapper().fromJson(jsonResult,Result.class);
            if (result.isResult())
            {
                for (oss_alarm_SaleOut item:saleOuts)
                {
                    item.setTranstatus("1");
                }
            }
        }
        catch(Exception e)
        {

        }
    }

    /**
     * 设备故障表
     */
    @Resource
    private AlarmEquipmentService EquipmentService;
    @RequestMapping(value = "/AddEquipment")
    @ResponseBody
    public Object AddEquipment(@RequestBody List<oss_alarm_Equipment> equipments,@RequestParam("NodeNo") String NodeNo)
    {
        Result result=new Result();
        try {
            for (oss_alarm_Equipment item:equipments) {

                sysOrgUnit orgUnit=new sysOrgUnit();
                item.setOucode(orgUnit.GetOuCodeByNodeNo(NodeNo));
                item.setNodeno(NodeNo);
            }
            int num = EquipmentService.AddEquipment(equipments);
            if (num > 0) {

                result.setResult(true);
                //调用传输数据
                PassHn_Equipment(equipments);
            } else {
                result.setResult(false);
            }
            return result;
        }

        catch (Exception e)
        {
            result.setMsg(e.getMessage());
            result.setResult(false);
            return result;
        }
    }
    //设备故障抛给湖南
    private void PassHn_Equipment(List<oss_alarm_Equipment> equipments)
    {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.hn.Alarm.PassEquipment");
        httpClient client=new httpClient();
        Map<String,String> hm=new HashMap<String, String>();
        Result result=new Result();
        try {
            String jsonResult= client.request(path, equipments, hm);
            result=new JsonMapper().fromJson(jsonResult,Result.class);
            if (result.isResult())
            {
                for (oss_alarm_Equipment item:equipments)
                {
                    item.setTranstatus("1");
                }
            }
        }
        catch(Exception e)
        {

        }
    }

    /**
     * 进油损耗对比表
     */
    @Resource
    private AlarmOilInContrastService OilInContrastService;
    @RequestMapping(value = "/AddOilInContrast")
    @ResponseBody
    public Object AddOilInContrast(@RequestBody List<oss_alarm_OilInContrast> oilInContrasts,@RequestParam("NodeNo") String NodeNo)
    {
        Result result=new Result();
        try {
            for (oss_alarm_OilInContrast item:oilInContrasts) {

                sysOrgUnit orgUnit=new sysOrgUnit();
                item.setOucode(orgUnit.GetOuCodeByNodeNo(NodeNo));
                item.setNodeno(NodeNo);
            }
            int num = OilInContrastService.AddOilInContrast(oilInContrasts);
            if (num > 0) {

                result.setResult(true);
                //调用数据传输
                //PassHn_OilInContrast(oilInContrasts);
            } else {
                result.setResult(false);
            }
            return result;
        }

        catch (Exception e)
        {
            result.setMsg(e.getMessage());
            result.setResult(false);
            return result;
        }
    }
    //将进油损耗对比抛给湖南
    private void PassHn_OilInContrast(List<oss_alarm_OilInContrast> oilInContrasts)
    {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.hn.Alarm.PassOilInContrast");
        httpClient client=new httpClient();
        Map<String,String> hm=new HashMap<String, String>();
        Result result=new Result();
        try {
            String jsonResult= client.request(path, oilInContrasts, hm);
            result=new JsonMapper().fromJson(jsonResult,Result.class);
            if (result.isResult())
            {
                for (oss_alarm_OilInContrast item:oilInContrasts)
                {
                    item.setTranstatus("1");
                }
            }
        }
        catch(Exception e)
        {

        }
    }


    @Resource
    private  Daily daily;

    //静态灌存
    @RequestMapping(value = "/addjtgc")
    @ResponseBody
    @Transactional(rollbackFor=Exception.class)
    public  Object Addtankoilgun(@RequestBody List<JTGC> tankoilgunLst,@RequestParam("NodeNo") String NodeNo){

        Result result=new Result();
        try {
            if(tankoilgunLst!=null&&tankoilgunLst.size()>0) {
                 for (JTGC item:tankoilgunLst){
                     item.getTankoil().setNodeno(NodeNo);
                 }
                List<oss_monitor_tankoil> tankoils=daily.addJTGC(tankoilgunLst,NodeNo);
                //调用数据传输给湖南
                boolean res=PassHn_JTGC(tankoilgunLst);
                if (res){
                  for (oss_monitor_tankoil item:tankoils){
                    item.setTranstatus("1");
                  }
                  daily.Updatetankoil(tankoils);
                }
                result.setResult(true);
            }
        }
        catch (Exception ex){
            result.setMsg(ex.getMessage());
            result.setResult(false);
        }
        return result;
    }
    //将静态灌存传给湖南
    private  boolean  PassHn_JTGC(List<JTGC> tankoilgunLst){
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resources.hn.Alarm.jtgc");
        httpClient client=new httpClient();
        Map<String,String> hm=new HashMap<String, String>();
        Result result=new Result();
        try {
            String jsonResult= client.request(path, tankoilgunLst, hm);
            result=new JsonMapper().fromJson(jsonResult,Result.class);
            if (result.isResult())
            {
                return true;
            }
        }
        catch(Exception e)
        {
            return false;
        }
        return false;
    }
}
