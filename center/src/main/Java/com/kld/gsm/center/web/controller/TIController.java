package com.kld.gsm.center.web.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.kld.gsm.center.common.RedisUtil;
import com.kld.gsm.center.domain.*;
import com.kld.gsm.center.domain.hn.HNDelivery;
import com.kld.gsm.center.service.DselfOilService;
import com.kld.gsm.center.util.action;
import com.kld.gsm.center.util.httpClient;
import com.kld.gsm.center.util.sysOrgUnit;
/*import com.kld.gsm.util.JsonMapper;*/
import com.kld.gsm.center.util.JsonMapper;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.kld.gsm.center.service.TimeInventoryService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.*;

/*
Created BY niyang
Created Date 2015/11/17
Decription: 实点库存
*/
@Controller
@RequestMapping("/TI")
public class TIController {

    @Resource
    private TimeInventoryService timeInventoryService;

    @Resource
    private DselfOilService dselfOilService;

    private static final Logger LOG = Logger.getLogger(TIController.class);

    @RequestMapping(value = "GetHello")
    @ApiIgnore
    public void GetHello(@RequestBody Result result ,HttpServletResponse response) {
        try {
             new RedisUtil().save("name","123",60);
            String s= new RedisUtil().getValue("name");
            response.getWriter().write(s + result.getMsg());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "Geth2")
    @ApiIgnore
    public void GetHello2(@RequestParam("name") String name ,HttpServletResponse response) {
        try {
            response.getWriter().write(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "GetH2")
    @ResponseBody
    @ApiIgnore
    public Object GetH2(@RequestBody Result result){
        result.setMsg("test123");
        return result;
    }

    @RequestMapping(value = "GetH3",method = RequestMethod.POST)
    @ResponseBody
    @ApiIgnore
    public Object GetH3(@RequestBody Result result,@RequestParam("name") int name){
        result.setMsg("test123");
        result.setTotal(name);
        return result;
    }

    @RequestMapping(value = "GetH4")
    @ResponseBody
    @ApiIgnore
    public Object GetH4(@RequestParam("name") int name){
        Result result=new Result();
        result.setMsg("test123");
        result.setTotal(name);
        return result;
    }

    /**
     * 整点库存
     */
    @RequestMapping(value="/InsertInventory")
    @ResponseBody
    public Object InsertInventory(@RequestBody List<oss_monitor_Inventory> monitorInventories,@RequestParam("NodeNo") String NodeNo)
    {
        //System.out.println("整点库存开始");
        Result result=new Result();
        action ac=new action();
        int flag=ac.getSwitch("resource.switch.TI.InsertInventory");
        result.setResult(true);
        try {
            for (oss_monitor_Inventory item:monitorInventories) {
                item.setNodeno(NodeNo);
            }
            if(flag==0){
                result.setResult(true);
            }
            if (flag==1){
                int num = timeInventoryService.InsertInventory(monitorInventories);
                if (num > 0) {
                  result.setResult(true);
                } else {
                    result.setResult(false);
                }
            }
            if (flag==2){
                return PassHn_Inventory(monitorInventories);
            }

            if (flag==3) {
                int num = timeInventoryService.InsertInventory(monitorInventories);
                if (num > 0) {
                    //调用数据传输
                    return PassHn_Inventory(monitorInventories);
                } else {
                    result.setResult(false);
                }
            }
            return result;
        }catch (Exception e)
        {
            result.setMsg(e.getMessage());
            result.setResult(false);
            return result;
        }
    }
    //将整点库存抛给湖南
    private Result PassHn_Inventory(List<oss_monitor_Inventory> monitorInventories)
    {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.hn.TI.DeliveryInventory");
        httpClient client=new httpClient();
        Map<String,String> hm=new HashMap<String, String>();
        Result result;
        try {
            String jsonResult= client.request(path, monitorInventories, hm);
            result=new JsonMapper().fromJson(jsonResult,Result.class);
            if (result!=null&&result.isResult())
            {
                for (oss_monitor_Inventory item:monitorInventories)
                {
                    item.setTranstatus("1");
                }
                timeInventoryService.InsertInventory(monitorInventories);
                return  result;
            }
        }
        catch(Exception e)
        {
            result=new Result();
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return  result;
    }
    /**
     *时点库存表
     */
    @RequestMapping(value="/AddTimeInventory")
    @ResponseBody
    public Object AddTimeInventory(@RequestBody List<oss_monitor_TimeInventory> monitorTimeInventories,@RequestParam("NodeNo") String NodeNo)
    {
        //System.out.println("时点库存开始");
        action ac=new action();
        Result result=new Result();
        int flag=ac.getSwitch("resource.switch.TI.AddTimeInventory");
        try {
            for (oss_monitor_TimeInventory item:monitorTimeInventories) {
                item.setNodeno(NodeNo);
            }
            if(flag==0){
                result.setResult(true);
            }
            if (flag==1){
                int num = timeInventoryService.AddTimeInventory(monitorTimeInventories);
                //int num=1;
                if (num > 0) {
                    result.setResult(true);
                } else {
                    result.setResult(false);
                }
            }
            if (flag==2){
                return PassHn_TimeInventory(monitorTimeInventories);//调用数据传输
            }
            if (flag==3){
                int num = timeInventoryService.AddTimeInventory(monitorTimeInventories);
                //int num=1;
                if (num > 0) {
                    result.setResult(true);
                    return PassHn_TimeInventory(monitorTimeInventories);//调用数据传输
                } else {
                    result.setResult(false);
                }
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
    //将时点库存抛给湖南
    private Result PassHn_TimeInventory(List<oss_monitor_TimeInventory> monitorTimeInventories)
    {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.hn.TI.PassTimeInventory");
        httpClient client=new httpClient();
        Map<String,String> hm=new HashMap<String, String>();
        Result result=new Result();
        try {
            String jsonResult= client.request(path, monitorTimeInventories, hm);
            result=new JsonMapper().fromJson(jsonResult,Result.class);
            if (result!=null&&result.isResult())
            {
                for (oss_monitor_TimeInventory item:monitorTimeInventories)
                {
                    item.setTranstatus("1");
                    //timeInventoryService
                }
                result.setResult(true);
            }
        }
        catch(Exception e)
        {
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     *保存油枪实时状态到缓存
     */
    @RequestMapping(value="/addqsszt2")
    @ResponseBody
    public  Object AddMacLogInfo(@RequestBody List<MacLogInfo> MacLogInfoLst,@RequestParam("NodeNo") String NodeNo){
        String Qsszt=new JsonMapper().toJson(MacLogInfoLst);
        RedisUtil pRedisUtil=new RedisUtil();
        Result result=new Result();
        try {
        if(pRedisUtil.isExists(NodeNo+"gun")) {
            pRedisUtil.update(NodeNo + "gun", Qsszt, 24 * 3600);
        }
        else {
            pRedisUtil.save(NodeNo + "gun", Qsszt, 24 * 3600);
        }
            result.setResult(true);
        }catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     *保存罐实时状态到缓存
     */
    @RequestMapping(value="/addgsszt2")
    @ResponseBody
    public  Object AddMonitorTimeInventory(@RequestBody List<atg_stock_data_out_t>  stockdataLst,@RequestParam("NodeNo") String NodeNo){
        String Gsszt=new JsonMapper().toJson(stockdataLst);
        RedisUtil pRedisUtil=new RedisUtil();
        Result result=new Result();
        try {
            if (pRedisUtil.isExists(NodeNo + "can")) {
                pRedisUtil.update(NodeNo + "can", Gsszt, 24 * 3600);
            }
            else {
                pRedisUtil.save(NodeNo + "can", Gsszt, 24 * 3600);
            }
            if (pRedisUtil.isExists(NodeNo + "time")){
                pRedisUtil.update(NodeNo + "time", new Date().getTime()+"", 24 * 3600);
            }else {
                pRedisUtil.save(NodeNo + "time", new Date().getTime()+"", 24 * 3600);
            }
                result.setResult(true);
        }catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return  result;
    }

/*
    */
/**
     *从缓存获取油枪实时状态
     *//*

    @RequestMapping(value="/getsszt")
    @ResponseBody
    public  List<LogInfoTimeInventory> GetMacLogInfoMonitorTimeInventory(@RequestParam("NodeNo") String NodeNo){
        String Oucode = new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
        RedisUtil pRedisUtil=new RedisUtil();
        String jsonResult= pRedisUtil.getValue(Oucode+"gun");
        List<LogInfoTimeInventory> pLogInfoTimeInventory=new ArrayList<LogInfoTimeInventory>();
        pLogInfoTimeInventory=new JsonMapper().fromJson(jsonResult,  pLogInfoTimeInventory.getClass());
        return  pLogInfoTimeInventory;

    }
*/

    @RequestMapping(value="/gettankandcun")
    @ResponseBody
    public TanksAndGuns gettankandgun(String nodeno){
        RedisUtil pRedisUtil=new RedisUtil();
        String gunjson= pRedisUtil.getValue(nodeno + "gun");
        String tankjson=pRedisUtil.getValue(nodeno+"can");
        TanksAndGuns tanksAndGuns=new TanksAndGuns();

        JsonMapper jm=new JsonMapper();
        JavaType jt=jm.createCollectionType(List.class, MacLogInfo.class);
        if (gunjson!=null&&!gunjson.isEmpty()&&!"".equals(gunjson)){
        List<MacLogInfo> guns=jm.fromJson(gunjson, jt);
        tanksAndGuns.setGuns(guns);
        }

        jt=jm.createCollectionType(List.class,atg_stock_data_out_t.class);
        if (tankjson!=null&&!tankjson.isEmpty()&&!"".equals(tankjson)) {
            List<atg_stock_data_out_t> tanks = jm.fromJson(tankjson, jt);
            tanksAndGuns.setTanks(tanks);
        }
        //System.out.println(tanksAndGuns.toString());
        return  tanksAndGuns;
    }


    @RequestMapping(value="/gettanks")
    @ResponseBody
    @ApiOperation(value ="根据站编码,获取罐实时数据")
    public ResultMsg gettanks(String nodeno) {
        ResultMsg resultMsg=new ResultMsg();
        RedisUtil pRedisUtil=new RedisUtil();
        String tankjson=pRedisUtil.getValue(nodeno+"can");
        JsonMapper jm=new JsonMapper();
        JavaType jt=jm.createCollectionType(List.class,atg_stock_data_out_t.class);
        if (tankjson!=null&&!tankjson.isEmpty()&&!"".equals(tankjson)) {
            List<atg_stock_data_out_t> tanks = jm.fromJson(tankjson, jt);
            resultMsg.setTotal(tanks.size());
            resultMsg.setRows(tanks);
        }
        return resultMsg;
    }


    @RequestMapping(value="/getguns")
    @ResponseBody
    @ApiOperation(value ="根据站编码,获取枪实时数据")
    public ResultMsg getguns(String nodeno) {
        ResultMsg resultMsg=new ResultMsg();
        RedisUtil pRedisUtil=new RedisUtil();
        String gunjson= pRedisUtil.getValue(nodeno + "gun");
        JsonMapper jm=new JsonMapper();
        JavaType jt=jm.createCollectionType(List.class, MacLogInfo.class);
        if (gunjson!=null&&!gunjson.isEmpty()&&!"".equals(gunjson)){
            List<MacLogInfo> guns=jm.fromJson(gunjson, jt);
            resultMsg.setTotal(guns.size());
            resultMsg.setRows(guns);
        }
        return resultMsg;
    }


    @RequestMapping(value = "/addcards")
    @ResponseBody
    @ApiOperation("自用油卡号导入接口")
    public Result addbills(@RequestBody List<oss_daily_SelfOil> deliveries){
        Result result=new Result();

        try {
            for (oss_daily_SelfOil item:deliveries) {
                item.setCreateUser("");
                item.setCreateDate(new Date());
                item.setUpdateUser("");
                item.setUpdateDate(new Date());
                dselfOilService.insertAll(item);
            }
            result.setResult(true);
        }
        catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return  result;
    }
}
