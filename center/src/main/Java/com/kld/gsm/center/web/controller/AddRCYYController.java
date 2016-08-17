package com.kld.gsm.center.web.controller;

import com.kld.gsm.center.domain.*;
import com.kld.gsm.center.service.*;
import com.kld.gsm.center.util.JsonMapper;
import com.kld.gsm.center.util.action;
import com.kld.gsm.center.util.httpClient;
import com.kld.gsm.center.util.sysOrgUnit;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*import com.kld.gsm.util.JsonMapper;*/

/**
 * Created by 5 on 2015/11/18.
 */
@Controller
@RequestMapping("/RCYX")
public class AddRCYYController {
    @Resource
    private DopotCountService opotCountService;
    @Resource
    private DopotDailyStatementService opotDailyStatementService;
    @Resource
    private DDailyBalanceService DailyBalanceService;
    @Resource
    private DOilDailyRecordService OilDailyRecordService;
    @Resource
    private DOilTankRegisterService OilTankRegisterService;
    @Resource
    private DStationShiftInfoService StationShiftInfoService;
    @Resource
    private DopoCountService opoCountService;
    @Resource
    private DTradeAccountService TradeAccountService;
    @Resource
    private DTradeInventoryService TradeInventoryService;
    @Resource
    private DTankShiftService TankShiftService;
    @Resource
    private DPumpDigitShiftService PumpDigitShiftService;
    @Resource
    private OildaybillService ObillService;
    @Resource
    private DShiftStockService ShiftStockService;
    @Resource
    private DTrankOilService dTrankOilService;
    @Resource
    private DOilGunService dOilGunService;


    /** @RequestMapping("/AddTest")
    public  void AddTest(@RequestParam("Name")String Name,HttpServletResponse response){
    try {
    //DopotCountService opotCountServices=new DopotCountServiceImpl();
    List<oss_daily_opotCount> testopotCountLst=new ArrayList<oss_daily_opotCount>();
    oss_daily_opotCount testopotCount=new oss_daily_opotCount();
    testopotCount.setAmount(1.1);
    testopotCount.setLiter(2.2);
    testopotCount.setNodeno(Name);
    testopotCount.setOilname(Name);
    testopotCount.setOucode(Name);
    testopotCount.setPrice(1.1);
    testopotCount.setTranstatus("1");
    testopotCount.setShift(Name);
    testopotCount.setOilno(Name);
    testopotCount.setType("1");
    testopotCountLst.add(testopotCount);
    int result=   opotCountService.AddDopotCount(testopotCountLst);
    if (result>0)
    {
    response.getWriter().write("success");
    }
    else
    {
    response.getWriter().write("fail");
    }
    }
    catch(IOException ex) {
    ex.printStackTrace();
    }

    }**/
/*
 Created by fangzhun on 2015/11/19.
  付油量分类统计表
 */
    @RequestMapping("/AddopotCount")
    @ResponseBody
    public  Object AddopotCount(@RequestBody List<oss_daily_opotCount> opotCountModelLst,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        try {
            if (opotCountModelLst != null && opotCountModelLst.size() > 0) {
                String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
                for (oss_daily_opotCount item:opotCountModelLst) {
                    item.setNodeno(NodeNo);
                    item.setOucode(Oucode);
                }
                int resultCont = opotCountService.AddDopotCount(opotCountModelLst);
                if (resultCont > 0)
                    result.setResult(true);
            }
        }
        catch(Exception ex){
            result.setResult(false);
            result.setMsg(ex.getMessage());
        }
        return  result;
    }
    /*
  Created by fangzhun on 2015/11/19.
  付油量分类日结表
   */
    @RequestMapping("/AddopotDailyStatement")
    @ResponseBody
    public Object AddopotDailyStatement(@RequestBody List<oss_daily_opotDailyStatement> opotDailyStatementModelLst,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        try {
            if(opotDailyStatementModelLst!=null&&opotDailyStatementModelLst.size()>0) {
                String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
                for(oss_daily_opotDailyStatement item:opotDailyStatementModelLst) {
                    item.setNodeno(NodeNo);
                    item.setOucode(Oucode);
                }
                int resultCont=opotDailyStatementService.AddDopotDailyStatement(opotDailyStatementModelLst);
                if (resultCont>0)
                    result.setResult(true);
            }
        }
        catch(Exception ex) {
            result.setResult(false);
            result.setMsg(ex.getMessage());
        }
        return result;
    }
    /*
    Created by fangzhun on 2015/11/19.
   日平衡表
     */
    @RequestMapping("/AddDailyBalance")
    @ResponseBody
    public  Object AddDailyBalance(@RequestBody List<oss_daily_DailyBalance> DailyBalanceModelLst,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        action ac=new action();
        int flag=ac.getSwitch("resource.switch.RCYX.AddDailyBalance");
        result.setResult(true);
        try {
            String Oucode = new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
            if (DailyBalanceModelLst != null && DailyBalanceModelLst.size() > 0) {
                for (oss_daily_DailyBalance item : DailyBalanceModelLst) {
                    item.setNodeno(NodeNo);
                    item.setOucode(Oucode);
                }
                //DDailyBalanceService DailyBalanceService = new DDailyBalanceServiceImpl();
                if (flag==0){
                    result.setResult(true);
                }
                if (flag==1){
                    int resultCont = DailyBalanceService.AddDailyBalance(DailyBalanceModelLst);
                    if (resultCont > 0) {
                        result.setResult(true);
                    }else{
                        result.setResult(false);
                    }
                }
                if (flag==2){
                    try {
                        PassHn_DailyBalance(DailyBalanceModelLst);
                        result.setResult(true);
                    }catch (Exception e){
                        result.setResult(false);
                        result.setMsg(e.getMessage());
                    }
                }
                if (flag==3){
                    try {
                        int resultCont = DailyBalanceService.AddDailyBalance(DailyBalanceModelLst);
                        if (resultCont > 0) {
                            result.setResult(true);
                            PassHn_DailyBalance(DailyBalanceModelLst);
                        }
                    }catch (Exception e){
                        result.setResult(false);
                        result.setMsg(e.getMessage());
                    }
                }

                int resultCont = DailyBalanceService.AddDailyBalance(DailyBalanceModelLst);
                if (resultCont > 0) {
                    result.setResult(true);
                    PassHn_DailyBalance(DailyBalanceModelLst);
                }
            }
        }
        catch(Exception ex) {
            result.setResult(false);
            result.setMsg(ex.getMessage());
        }
        return result;
    }
    /*
    将日平衡表发给湖南
    * */
    private  void PassHn_DailyBalance(List<oss_daily_DailyBalance> DailyBalanceModelLst) throws Exception {
        action ac=new action();
        String ClassKnotDataPath=ac.getUri("resources.hn.RCYX.DailyBalance");
        Map<String,String> hm=new HashMap<String, String>();
        //发送到湖南
        httpClient client=new httpClient();
        String JsonResult=client.request(ClassKnotDataPath, DailyBalanceModelLst, hm);
        Result pResult=new JsonMapper().fromJson(JsonResult, Result.class);
        if(pResult.isResult()) {
            for (oss_daily_DailyBalance item : DailyBalanceModelLst) {
                item.setTranstatus("1");
            }
        }
    }
    /*
    Created by fangzhun on 2015/11/19.
   成品油日结存报表
     */
    @RequestMapping("/AddOilDailyRecord")
    @ResponseBody
    public  Object AddOilDailyRecord(@RequestBody List<oss_daily_OilDailyRecord> OilDailyRecordModelLst,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        try{
            if(OilDailyRecordModelLst!=null&&OilDailyRecordModelLst.size()>0) {
                String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
                for(oss_daily_OilDailyRecord item:OilDailyRecordModelLst){
                    item.setNodeno(NodeNo);
                    item.setOucode(Oucode);
                }
                //DOilDailyRecordService OilDailyRecordService = new DOilDailyRecordServiceImpl();
                int resultCont=OilDailyRecordService.AddOilDailyRecord(OilDailyRecordModelLst);
                if (resultCont>0)
                    result.setResult(true);
            }
        }catch (Exception ex) {
            result.setResult(false);
            result.setMsg(ex.getMessage());
        }
        return result;
    }
    /*
    Created by fangzhun on 2015/11/19.
    加油站油品分罐保管登记帐
     */
    @RequestMapping("/AddOilTankRegister")
    @ResponseBody
    public  Object AddOilTankRegister(@RequestBody List<oss_daily_OilTankRegister> OilTankRegisterModelLst,@RequestParam("NodeNo") String NodeNo) {
        Result result=new Result();
        try{
            if(OilTankRegisterModelLst!=null&&OilTankRegisterModelLst.size()>0) {
                String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
                for(oss_daily_OilTankRegister item:OilTankRegisterModelLst) {
                    item.setNodeno(NodeNo);
                    item.setOucode(Oucode);
                }
                //DOilTankRegisterService OilTankRegisterService = new DOilTankRegisterServiceImpl();
                int resultCont=OilTankRegisterService.AddOilTankRegister(OilTankRegisterModelLst);
                if (resultCont>0)
                    result.setResult(true);
            }

        }
        catch (Exception ex) {
            result.setResult(false);
            result.setMsg(ex.getMessage());
        }
        return result;
    }
    /*
  Created by fangzhun on 2015/11/19.
  付油数量统计交接表
   */
    @RequestMapping("/AddopoCount")
    @ResponseBody
    public Object AddopoCount(@RequestBody List<oss_daily_opoCount> opoCountModelLst,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        try {
            if(opoCountModelLst!=null&&opoCountModelLst.size()>0) {
                String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
                for(oss_daily_opoCount item:opoCountModelLst) {
                    item.setNodeno(NodeNo);
                    item.setOucode(Oucode);
                }
                //DopoCountService opoCountService = new DopoCountServiceImpl();
                int resultCont=opoCountService.AddOPOCount(opoCountModelLst);
                if (resultCont>0)
                    result.setResult(true);
            }
        }
        catch(Exception ex) {
            result.setResult(false);
            result.setMsg(ex.getMessage());
        }
        return result;
    }
    /*
     Created by fangzhun on 2015/11/19.
    油站班报表
      */
    @RequestMapping("/AddStationShiftInfo")
    @ResponseBody
    public  Object AddStationShiftInfo(@RequestBody List<oss_daily_StationShiftInfo> StationShiftInfoModelLst,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        try {
            if(StationShiftInfoModelLst!=null&&StationShiftInfoModelLst.size()>0) {
                String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
                //DStationShiftInfoService StationShiftInfoService = new DStationShiftInfoServiceImpl();
                for(oss_daily_StationShiftInfo item:StationShiftInfoModelLst) {
                    item.setNodeno(NodeNo);
                    item.setOucode(Oucode);
                }
                int resultCont=StationShiftInfoService.AddStationShiftInfo(StationShiftInfoModelLst);
                if(resultCont>0)
                    result.setResult(true);
            }
        }
        catch(Exception ex) {
            result.setResult(false);
            result.setMsg(ex.getMessage());
        }
        return result;
    }
    /*
  Created by fangzhun on 2015/11/19.
  交易加油流水表
   */
    @RequestMapping("/AddTradeAccount")
    @ResponseBody
    public  Object AddTradeAccount(@RequestBody List<oss_daily_TradeAccount> TradeAccountModelLst,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        action ac=new action();
        int flag=ac.getSwitch("resource.switch.RCYX.AddTradeAccount");
        result.setResult(true);
        try {
            if(TradeAccountModelLst!=null&&TradeAccountModelLst.size()>0) {
                String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
                for (oss_daily_TradeAccount item : TradeAccountModelLst) {
                    item.setNodeno(NodeNo);
                    item.setOucode(Oucode);
                }
                if (flag==0){
                    result.setResult(true);
                }
                if (flag==1){
                    int resultCont=TradeAccountService.AddTradeAccount(TradeAccountModelLst);
                    if(resultCont>0) {
                        result.setResult(true);
                    }
                }

            }
        }
        catch(Exception ex) {
            result.setResult(false);
            result.setMsg(ex.getMessage());
        }
        return result;
    }
    /*
    将交易加油流水表发给湖南
    * */
    private  void passHn_TradeAccount(List<oss_daily_TradeAccount> TradeAccountModelLst) throws Exception {
        action ac=new action();
        String TradeAccountPath=ac.getUri("resources.hn.TradeAccount.TradeAccount");
        Map<String,String> hm=new HashMap<String, String>();
        //发送到湖南
        httpClient client=new httpClient();
        String JsonReault=client.request(TradeAccountPath, TradeAccountModelLst, hm);
        Result pResult=new JsonMapper().fromJson(JsonReault, Result.class);
        if(pResult.isResult()) {
            for (oss_daily_TradeAccount item:TradeAccountModelLst){
                item.setTransflag("1");
            }

        }
    }
    /*
   Created by fangzhun on 2015/11/19.
   交易库存表
    */
    @RequestMapping("/AddTradeInventory")
    @ResponseBody
    public Object AddTradeInventory(@RequestBody List<oss_daily_TradeInventory> TradeInventoryModelLst,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        action ac=new action();
        int flag=ac.getSwitch("resource.switch.RCYX.AddTradeInventory");

        result.setResult(true);
        if(flag==0){
            return result;
        }
        try {
            if(TradeInventoryModelLst!=null&&TradeInventoryModelLst.size()>0) {
                String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
                //DTradeInventoryService TradeInventoryService = new DTradeInventoryServiceImpl();
                for(oss_daily_TradeInventory item:TradeInventoryModelLst) {
                    item.setNodeno(NodeNo);
                    item.setOucode(Oucode);
                }

                if (flag==1) {
                    try {
                        int resultCont = TradeInventoryService.AddTradeInventory(TradeInventoryModelLst);
                        return result;
                    }catch (Exception e){
                        result.setResult(false);
                        result.setMsg(e.getMessage());
                        return result;
                    }

                }
                if (flag==2){
                    try {
                        if (ac.getOpenSelfOilSet()=="0") {
                            List<oss_daily_TradeInventoryOld> TradeInventoryModelOldList = trans(TradeInventoryModelLst);
                            PassHn_TradeInventory(TradeInventoryModelOldList);
                        }
                        if (ac.getOpenSelfOilSet()=="1"){
                            PassHn_TradeInventory1(TradeInventoryModelLst);
                        }
                        result.setResult(false);
                        return result;
                    }catch (Exception e){
                        result.setResult(false);
                        result.setMsg(e.getMessage());
                        return result;
                    }
                }
                if (flag==3) {
                    try {
                        int resultCont = TradeInventoryService.AddTradeInventory(TradeInventoryModelLst);
                        if (resultCont == 1) {
                            result.setResult(true);
                            List<oss_daily_TradeInventoryOld> TradeInventoryModelOldList = trans(TradeInventoryModelLst);
                            PassHn_TradeInventory(TradeInventoryModelOldList);
                        }
                        if (resultCont == 2) {
                            result.setResult(true);
                            PassHn_TradeInventory1(TradeInventoryModelLst);
                        }
                        result.setResult(true);
                        return  result;
                    }catch (Exception e){
                        result.setResult(false);
                        result.setMsg(e.getMessage());
                        return result;
                    }
                }
            }
        }
        catch(Exception ex) {
            result.setResult(false);
            result.setMsg(ex.getMessage());
        }
        return result;
    }


    public List<oss_daily_TradeInventoryOld>  trans(List<oss_daily_TradeInventory> TradeInventoryModelLst) {
        List<oss_daily_TradeInventoryOld> TradeInventoryModelOldList = new ArrayList<oss_daily_TradeInventoryOld>();
        for (oss_daily_TradeInventory oss : TradeInventoryModelLst) {
            oss_daily_TradeInventoryOld old = new oss_daily_TradeInventoryOld();
            old.setMacno(oss.getMacno());
            old.setTtc(oss.getTtc());
            old.setTakedate(oss.getTakedate());
            old.setOilgun(oss.getOilgun());
            old.setNodeno(oss.getNodeno());
            old.setOilcan(oss.getOilcan());
            old.setOilno(oss.getOilno());
            old.setOpetime(oss.getOpetime());
            old.setStockdate(oss.getStockdate());
            old.setStocktime(oss.getStocktime());
            old.setOill(oss.getOill());
            old.setStandardl(oss.getStandardl());
            old.setEmptyl(oss.getEmptyl());
            old.setHeighttotal(oss.getHeighttotal());
            old.setHeightwater(oss.getHeightwater());
            old.setOiltemp(oss.getOiltemp());
            old.setWaterl(oss.getWaterl());
            old.setDensity(oss.getDensity());
            old.setDensitystandard(oss.getDensitystandard());
            old.setShift(oss.getShift());
            old.setRemark(oss.getRemark());
            old.setTranstatus(oss.getTranstatus());
            old.setOucode(oss.getOucode());
            old.setLiter(oss.getLiter());
            old.setPumpno(oss.getPumpno());
            old.setBackmatchflag(oss.getBackmatchflag());
            TradeInventoryModelOldList.add(old);
        }
        return TradeInventoryModelOldList;
    }

    /*
 Created by mjxu .
 更新交易库存表
  */
    @RequestMapping("/UpdateTradeInventory")
    @ResponseBody
    public Object UpdateTradeInventory(@RequestBody List<oss_daily_TradeInventory> TradeInventoryModelLst,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        action ac=new action();
        int flag=ac.getSwitch("resource.switch.RCYX.UpdateTradeInventory");
        result.setResult(true);
        try {
            if(TradeInventoryModelLst!=null&&TradeInventoryModelLst.size()>0) {
                String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
                for(oss_daily_TradeInventory item:TradeInventoryModelLst) {
                    item.setNodeno(NodeNo);
                    item.setOucode(Oucode);
                }
                if(flag==0){
                    result.setResult(true);
                }
                if (flag==1){
                    try {
                        int resultCont=TradeInventoryService.updateTradeInventory(TradeInventoryModelLst);
                        if(resultCont>0) {
                            result.setResult(true);
                        }
                    }catch (Exception e){
                        result.setResult(false);
                        result.setMsg(e.getMessage());
                    }
                }
                if(flag==2){
                    try {
                        PassHn_UpdateTradeInventory(TradeInventoryModelLst);
                    }catch (Exception e){
                        result.setResult(true);
                        result.setMsg(e.getMessage());
                    }
                }
                if(flag==3){
                    try {
                        int resultCont = TradeInventoryService.updateTradeInventory(TradeInventoryModelLst);
                        if (resultCont > 0) {
                            result.setResult(true);
                            PassHn_UpdateTradeInventory(TradeInventoryModelLst);
                        }
                    }catch (Exception e){
                        result.setResult(false);
                        result.setMsg(e.getMessage());
                    }
                }
            }
        }
        catch(Exception ex) {
            result.setResult(false);
            result.setMsg(ex.getMessage());
        }
        return result;
    }
    private  void  PassHn_UpdateTradeInventory(List<oss_daily_TradeInventory> TradeInventoryModelLst)throws Exception{
        action ac=new action();
        String TradeInventoryPath=ac.getUri("resources.hn.RCYX.TradeUpdateInventory");
        Map<String,String> hm=new HashMap<String, String>();
        //发送到湖南
        httpClient client=new httpClient();
        String JsonReault= null;
        JsonReault = client.request(TradeInventoryPath, TradeInventoryModelLst, hm);
        Result pResult=new JsonMapper().fromJson(JsonReault, Result.class);
        if(pResult.isResult()) {
            for (oss_daily_TradeInventory item:TradeInventoryModelLst){
                item.setTranstatus("2");
            }

        }
    }


    private  void  PassHn_TradeInventory(List<oss_daily_TradeInventoryOld> TradeInventoryModelOldList)throws Exception{
        action ac=new action();
        String TradeInventoryPath=ac.getUri("resources.hn.RCYX.TradeInventory");
        Map<String,String> hm=new HashMap<String, String>();
        //发送到湖南
        httpClient client=new httpClient();
        String JsonReault= null;
        JsonReault = client.request(TradeInventoryPath, TradeInventoryModelOldList, hm);
        Result pResult=new JsonMapper().fromJson(JsonReault, Result.class);
        if(pResult.isResult()) {
            for (oss_daily_TradeInventoryOld item:TradeInventoryModelOldList){
                item.setTranstatus("1");
            }

        }
    }
    private  void  PassHn_TradeInventory1(List<oss_daily_TradeInventory> TradeInventoryModelLst)throws Exception{
        action ac=new action();
        String TradeInventoryPath=ac.getUri("resources.hn.RCYX.TradeInventory1");
        Map<String,String> hm=new HashMap<String, String>();
        //发送到湖南
        httpClient client=new httpClient();
        String JsonReault= null;
        JsonReault = client.request(TradeInventoryPath, TradeInventoryModelLst, hm);
        Result pResult=new JsonMapper().fromJson(JsonReault, Result.class);
        if(pResult.isResult()) {
            for (oss_daily_TradeInventory item:TradeInventoryModelLst){
                item.setTranstatus("1");
            }

        }
    }

    /*
      Created by fangzhun on 2015/11/19.
      油罐交接表
       */
    @RequestMapping("/Addtankshift")
    @ResponseBody
    public Object Addtankshift(@RequestBody List<oss_daily_tankshift> tankshiftModelLst,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        try {
            if(tankshiftModelLst!=null&&tankshiftModelLst.size()>0) {
                String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
                // DTankShiftService TankShiftService = new DTankShiftServiceImpl();
                for(oss_daily_tankshift item:tankshiftModelLst) {
                    item.setNodeno(NodeNo);
                    item.setOucode(Oucode);
                }
                int resultCont=TankShiftService.AddTankShift(tankshiftModelLst);
                if(resultCont>0)
                    result.setResult(true);
            }
        }
        catch(Exception ex) {
            result.setResult(false);
            result.setMsg(ex.getMessage());
        }
        return result;
    }
    /*
    Created by fangzhun on 2015/11/19.
    泵码交接表
    */
    @RequestMapping("/AddpumpDigitShift")
    @ResponseBody
    public Object AddpumpDigitShift(@RequestBody List<oss_daily_pumpDigitShift> pumpDigitShiftModelLst,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        try {
            if(pumpDigitShiftModelLst!=null&&pumpDigitShiftModelLst.size()>0) {
                String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
                //DPumpDigitShiftService PumpDigitShiftService = new DPumpDigitShiftServiceImpl();
                for(oss_daily_pumpDigitShift item:pumpDigitShiftModelLst) {
                    item.setNodeno(NodeNo);
                    item.setOucode(Oucode);
                }
                int resultCont=PumpDigitShiftService.AddPumpDigitShift(pumpDigitShiftModelLst);
                if(resultCont>0)
                    result.setResult(true);
            }
        }
        catch(Exception ex) {
            result.setResult(false);
            result.setMsg(ex.getMessage());
        }
        return result;
    }
    /*
      Created by fangzhun on 2015/11/19.
      泵码日结表
      */
    @RequestMapping("/Addoildaybill")
    @ResponseBody
    public Object Addoildaybill(@RequestBody List<oss_daily_oildaybill> oildaybillModelLst,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        try {
            if(oildaybillModelLst!=null&&oildaybillModelLst.size()>0) {
                String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
                //OildaybillService ObillService = new OildaybillServiceImpl();
                for(oss_daily_oildaybill item:oildaybillModelLst) {
                    item.setNodeno(NodeNo);
                    item.setOucode(Oucode);
                }
                int resultCont=ObillService.AddOildaybill(oildaybillModelLst);
                if(resultCont>0)
                    result.setResult(true);
            }
        }
        catch(Exception ex) {
            result.setResult(false);
            result.setMsg(ex.getMessage());
        }
        return result;
    }
    /*
      Created by fangzhun on 2015/11/19.
      班结库存表
      */
    @RequestMapping("/AddShiftStock")
    @ResponseBody
    public Object AddShiftStock(@RequestBody List<oss_daily_ShiftStock> ShiftStockModelLst,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        try {
            if(ShiftStockModelLst!=null&&ShiftStockModelLst.size()>0) {
                String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
                //DShiftStockService ShiftStockService = new DShiftStockServiceImpl();
                for(oss_daily_ShiftStock item:ShiftStockModelLst) {
                    item.setNodeno(NodeNo);
                    item.setOucode(Oucode);
                }
                int resultCont=ShiftStockService.AddShiftStock(ShiftStockModelLst);
                if(resultCont>0)
                    result.setResult(true);
            }
        }
        catch(Exception ex) {
            result.setResult(false);
            result.setMsg(ex.getMessage());
        }
        return result;
    }
    /*
      Created by fangzhun on 2015/11/19.
      油罐静态库存
      */
    @RequestMapping("/AddTankoil")
    @ResponseBody
    @Transactional(rollbackFor=Exception.class)
    public Object AddTankoil(@RequestBody List<oss_monitor_tankOilGun> oss_monitor_tankoilguns,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        action ac=new action();
        int flag=ac.getSwitch("resource.switch.RCYX.AddTankoil");
        result.setResult(true);

        try {
            if(oss_monitor_tankoilguns!=null&&oss_monitor_tankoilguns.size()>0) {
                for (oss_monitor_tankOilGun itemoil:oss_monitor_tankoilguns)
                    if (itemoil.getMonitorTankOil() != null) {
                        String Oucode = new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
                        oss_monitor_tankoil tankoil=itemoil.getMonitorTankOil();
                        tankoil.setNodeno(NodeNo);
                        tankoil.setOucode(Oucode);
                        dTrankOilService.AddTankoil(tankoil);
                        for (oss_monitor_oilgun item :itemoil.getMonitorOilgun())
                        {
                            item.setNodeno(NodeNo);
                            item.setOucode(Oucode);
                            dOilGunService.AddOilgun(item);
                        }
                    }
            }
            result.setResult(true);
        }
        catch(Exception ex) {
            result.setResult(false);
            result.setMsg(ex.getMessage());
        }
        return result;
    }
    /*
   Created by fangzhun on 2015/11/20.
   	油站班报表名oss_daily_stationshiftinfo
   	付油量分类统计表名oss_daily_opotcount
   	泵码交接表名oss_daily_pumpdigitshift
   	油罐交接表oss_daily_tankshift
   	付油数量统计交接表oss_daily_opocount
   */
    @RequestMapping("/AddClassKnotData")
    @ResponseBody
    @Transactional(rollbackFor=Exception.class)
    public Object AddClassKnotData(@RequestBody oss_daily_ClassKnotData classknotdata,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        action ac=new action();
        int flag=ac.getSwitch("resource.switch.RCYX.AddClassKnotData");
        result.setResult(true);
        try {
            if (flag==0){
                result.setResult(true);
                return result;
            }
            if(classknotdata!=null) {
                String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
                //付油量分类统计表名
                for (oss_daily_opoCount item : classknotdata.getOpoCountLost()) {
                    item.setNodeno(NodeNo);
                    item.setOucode(Oucode);
                }
                opoCountService.AddOPOCount(classknotdata.getOpoCountLost());
                //付油数量统计交接表
                for (oss_daily_opotCount item : classknotdata.getOpotCountLst()) {
                    item.setNodeno(NodeNo);
                    item.setOucode(Oucode);
                }
                opotCountService.AddDopotCount(classknotdata.getOpotCountLst());
                //泵码交接表名
                for (oss_daily_pumpDigitShift item : classknotdata.getPumpDigitShiftLost()) {
                    item.setNodeno(NodeNo);
                    item.setOucode(Oucode);
                }
                PumpDigitShiftService.AddPumpDigitShift(classknotdata.getPumpDigitShiftLost());
                //油罐交接表
                for (oss_daily_tankshift item : classknotdata.getTankShiftLost()) {
                    item.setNodeno(NodeNo);
                    item.setOucode(Oucode);
                }
                TankShiftService.AddTankShift(classknotdata.getTankShiftLost());
                //油站班报表名
                for (oss_daily_StationShiftInfo item : classknotdata.getStationShiftInfoLst()) {
                    item.setNodeno(NodeNo);
                    item.setOucode(Oucode);
                }
                StationShiftInfoService.AddStationShiftInfo(classknotdata.getStationShiftInfoLst());
                result.setResult(true);
                return PassHn_SJSJ(classknotdata);
            }
        }
        catch(Exception ex) {
            result.setResult(false);
            result.setMsg(ex.getMessage());
        }
        return result;

    }
    /*
    付油量分类统计表名
    付油数量统计交接表
    泵码交接表名
    油罐交接表
    油站班报表名
    * */
    private  Result PassHn_SJSJ(oss_daily_ClassKnotData classknotdata) throws Exception {
        action ac=new action();
        String ClassKnotDataPath=ac.getUri("resources.hn.RCYX.BJSJ");
        Map<String,String> hm=new HashMap<String, String>();
        //发送到湖南
        httpClient client=new httpClient();
        Result pResult=new Result();
        try {
            String JsonReault=client.request(ClassKnotDataPath, classknotdata, hm);
            pResult=new JsonMapper().fromJson(JsonReault,Result.class);
          /*  if(pResult!=null&&pResult.isResult()) {
                //付油量分类统计表名
                for (oss_daily_opoCount item : classknotdata.getOpoCountLost()) {
                    item.setTranstatus("1");
                }
                //付油数量统计交接表
                for (oss_daily_opotCount item : classknotdata.getOpotCountLst()) {
                    item.setTranstatus("1");
                }
                //泵码交接表名
                for (oss_daily_pumpDigitShift item : classknotdata.getPumpDigitShiftLost()) {
                    item.setTranstatus("1");
                }
                //油罐交接表
                for (oss_daily_tankshift item : classknotdata.getTankShiftLost()) {
                    item.setTranstatus("1");
                }
                //油站班报表名
                for (oss_daily_StationShiftInfo item : classknotdata.getStationShiftInfoLst()) {
                    item.setTranstatus("1");
                }
            }*/
        }catch (Exception e){
            pResult.setMsg(e.getMessage());
            pResult.setResult(false);

        }

        return pResult;
    }
}
