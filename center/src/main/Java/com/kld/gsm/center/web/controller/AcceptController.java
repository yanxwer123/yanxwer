package com.kld.gsm.center.web.controller;


import com.fasterxml.jackson.databind.JavaType;
import com.kld.gsm.center.service.AcceptanceDeliveryBillService;
import com.kld.gsm.center.dao.oss_sys_OrgUnitMapper;
import com.kld.gsm.center.dao.oss_sysmanage_StationMapper;
import com.kld.gsm.center.service.AcceptanceService;
import com.kld.gsm.center.service.OilDownService;
import com.kld.gsm.center.service.SysOrgUnitService;
import com.kld.gsm.center.service.SystemManage;
import com.kld.gsm.center.util.action;
import com.kld.gsm.center.util.httpClient;

/*import com.kld.gsm.util.JsonMapper;*/
import com.kld.gsm.center.util.JsonMapper;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.kld.gsm.center.domain.*;
import com.kld.gsm.center.domain.hn.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.Resource;

/*
Created BY niyang
Created Date 2015/11/21
*/
@Controller
@RequestMapping(value = "/accept")
public class AcceptController {

    @Autowired
    private OilDownService oilDownService;

    @Autowired
    private SystemManage systemManage;

    @Autowired
    private  SysOrgUnitService sysOrgUnitService;

    @Autowired
    private AcceptanceDeliveryBillService acceptanceDeliveryBillService;



    /*
  * Createdby niyang
  * description 根据出库单号，请求湖南接口，获取出库单，返回
  * */
    @RequestMapping(value = "/getbillbyno")
    @ApiOperation("根据出库单号，查询湖南接口，返回数据给站级,数据落地Center")
    @ResponseBody
    public  oss_acceptance_deliveryBill getbillByNo(@RequestParam("billno")String billNo ){
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resources.hn.accept.getbillbyno");
        httpClient client=new httpClient();
        Map<String,String> hm=new HashMap<String, String>();
        hm.put("billno",billNo);
        Result result=new Result();
        oss_acceptance_deliveryBill bill = new oss_acceptance_deliveryBill();
        try {
            String jsonResult= client.request(path, null, hm);
            HNDelivery item=new JsonMapper().fromJson(jsonResult,HNDelivery.class);

            if (item!=null){
            bill.setTranstatus("0");
            bill.setDeliveryno(item.getCK_ID());
            bill.setPsdId(item.getPSD_ID());
            bill.setOilno(item.getOILS_ID());
            bill.setFromoildepot(item.getYK_ID());
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date fh_date=sdf.parse(item.getFH_DATE());
            bill.setDeliverytime(fh_date);
            bill.setPlanton(item.getFH_WEIGHT());
            bill.setPlanl(item.getFH_BULK());
            bill.setCarno(item.getCP_NO());
            bill.setDensity(item.getTEST_DENSITY());

            List<oss_acceptance_deliveryBill> bills=new ArrayList<oss_acceptance_deliveryBill>();
            bills.add(bill);
            acceptanceDeliveryBillService.AddDeliveryBill(bills);
            }
            bill=acceptanceDeliveryBillService.selectBybillno(billNo);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return bill ;
    }


    @RequestMapping(value = "/getbynodeno")
    @ResponseBody
    public List<oss_acceptance_deliveryBill> getbillbyNodeNo(@RequestParam("NodeNo") String Nodeno){
        List<oss_acceptance_deliveryBill> bills= oilDownService.getOutBillsByNodeNo(Nodeno);
        return bills;
    }

    @RequestMapping(value = "/addbills")
    @ResponseBody
    @ApiOperation("湖南发送出货单接口")
    public Result addbills(@RequestBody List<HNDelivery> deliveries){
        Result result=new Result();
        //湖南接口接收出库单
        List<oss_acceptance_deliveryBill> bills=new ArrayList<oss_acceptance_deliveryBill>();
try {
    for (HNDelivery item : deliveries) {
        oss_acceptance_deliveryBill bill = new oss_acceptance_deliveryBill();
        bill.setTranstatus("0");
        bill.setDeliveryno(item.getCK_ID());
        bill.setPsdId(item.getPSD_ID());
        bill.setOilno(item.getOILS_ID());
        //bill.setFromoildepot();
        bill.setFromdepotname(item.getYK_ID());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date fh_date=sdf.parse(item.getFH_DATE());
        bill.setDeliverytime(fh_date);
        bill.setPlanton(item.getFH_WEIGHT());
        bill.setPlanl(item.getFH_BULK());
        bill.setCarno(item.getCP_NO());
        bill.setDeliverytemp(item.getTEST_TEMP());
        bill.setDensity(item.getTEST_DENSITY());
        bill.setTonodeno(item.getNodeno());
        bill.setIscomplete("0");


        /*oss_sysmanage_Station station = systemManage.getstationbylsgskcdd(item.getLSGC(), item.getKCDD());
        if (station != null) {
            bill.setTonodeno(station.getStationCode());
            oss_sys_OrgUnit org = sysOrgUnitService.GetOrgByNodeNo(station.getStationCode());
            if (org != null) {
                bill.setOucode(org.getOucode());
            }
        }*/
        bills.add(bill);
    }

    acceptanceDeliveryBillService.AddDeliveryBill(bills);
    result.setResult(true);
}
catch (Exception e){
    result.setResult(false);
    result.setMsg(e.getMessage());
        }
        return  result;
    }
    /*
    *Created BY xhz Created Date 2015/11/21
    *  从湖南销售省级监控平台下载配货单
    */
    @Resource
    private AcceptanceService acceptanceService;
    @RequestMapping(value = "/adddeliveryplan", method = RequestMethod.POST)
    @ResponseBody
    public Object adddeliveryplan(@RequestBody List<HNDeliveryPlan> hnDeliveryPlans)
    {
        Result result=new Result();
        try {
            //将湖南下发的配送单表转化为中心库中的对象
            List<oss_acceptance_deliveryPlan> oss_acceptance_deliveryPlans = new ArrayList<oss_acceptance_deliveryPlan>();
            for (HNDeliveryPlan item : hnDeliveryPlans) {
                oss_acceptance_deliveryPlan ossAcceptanceDeliveryPlan = new oss_acceptance_deliveryPlan();
                ossAcceptanceDeliveryPlan.setPsdId(item.getPSD_ID());
                ossAcceptanceDeliveryPlan.setOilId(item.getOILS_ID());
                ossAcceptanceDeliveryPlan.setDepotCode(item.getDEPOT_CODE());  //DEPOT_CODE
                ossAcceptanceDeliveryPlan.setXql(item.getXQL());
                ossAcceptanceDeliveryPlan.setXqunit(item.getXQUNIT());
                ossAcceptanceDeliveryPlan.setCpNo(item.getCP_NO());
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                Date ps_date=sdf.parse(item.getPS_DATE());
                ossAcceptanceDeliveryPlan.setPsDate(ps_date);
                ossAcceptanceDeliveryPlan.setLsgc(item.getLSGC());
                ossAcceptanceDeliveryPlan.setKcdd(item.getKCDD());
                ossAcceptanceDeliveryPlan.setNodeno(item.getNodeno());
                oss_sysmanage_Station ossSysmanageStation = acceptanceService.GetStationInfo(item.getLSGC(), item.getKCDD());
                if (ossSysmanageStation != null) {
                    ossAcceptanceDeliveryPlan.setNodeno(ossSysmanageStation.getStationCode());//NodeNo
                    oss_sys_OrgUnit ossSysOrgUnit = acceptanceService.GetOrgUnit(ossSysmanageStation.getStationCode());
                    if (ossSysOrgUnit != null) {
                        ossAcceptanceDeliveryPlan.setOucode(ossSysOrgUnit.getOucode());
                    }
                }
                ossAcceptanceDeliveryPlan.setTranstatus("0");
                oss_acceptance_deliveryPlans.add(ossAcceptanceDeliveryPlan);
            }
            int num=acceptanceService.GetDeliveryPlan(oss_acceptance_deliveryPlans);
            if (num>0)
            result.setResult(true);
            else
            result.setResult(false);
            return result;
        }
        catch (Exception e)
        {
            result.setMsg(e.getMessage());
            result.setResult(false);
            return result;
        }
    }


    @RequestMapping(value = "/addOdReg", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("站级系统上传卸油数据，并转发湖南")
    public Object addOdReg(@RequestBody List<AcceptOdRegMain> acceptOdRegMains,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        try {
            String oucode="";
            oss_sys_OrgUnit oss=sysOrgUnitService.GetOrgByNodeNo(NodeNo);
            if (oss!=null){
                oucode=oss.getOucode();
            }
            acceptanceService.addOdregAndregInfo(acceptOdRegMains,NodeNo,oucode);
            addOdReg(acceptOdRegMains);
            result.setResult(true);
        }catch (Exception e){
            result.setMsg(e.getMessage());
            result.setResult(false);
        }
        return result;
    }

    private List<HNodRegMain> TransToHn(List<AcceptOdRegMain> acceptOdRegMains){
        List<HNodRegMain>  hnmains=new ArrayList<HNodRegMain>();
        for (AcceptOdRegMain item:acceptOdRegMains){
            oss_acceptance_odRegister odr=item.getAcceptanceOdRegister();
            //regionfor main
            HNodRegMain main=new HNodRegMain();
            HNodRegister register=new HNodRegister();
            register.setIndemnityloss(odr.getIndemnityloss());
            register.setInstationtime(odr.getInstationtime());
            register.setBackbankno(odr.getBackbankno());
            register.setPlanl(odr.getPlanl());
            register.setBegintime(odr.getBegintime());
            register.setCalculateoperator(odr.getCalculateoperator());
            register.setCantrucktemp(odr.getCantrucktemp());
            register.setDeliveryno(odr.getDeliveryno());
            register.setDischargeloss(odr.getDischargeloss());
            register.setDischargelossv20(odr.getDischargelossv20());
            register.setDischargerate(odr.getDischargerate());
            register.setDuringsales(odr.getDuringsales());
            register.setEndtime(odr.getEndtime());
            register.setHeightempey(odr.getHeightempey());
            register.setHeighttotal(odr.getHeighttotal());
            register.setHeightwater(odr.getHeightwater());
            register.setManualno(odr.getManualno());
            register.setNodeno(odr.getNodeno());
            register.setDischargeratev20(odr.getDischargeratev20());
            register.setRealgetl(odr.getRealgetl());
            register.setRealreceivel(odr.getRealreceivel());
            register.setRealgetlv20(odr.getRealgetlv20());
            register.setShift(odr.getShift());
            register.setOilno(odr.getOilno());
            register.setPlumbunbankoperator(odr.getPlumbunbankoperator());
            //endregion
            main.setOdRegister(register);
            //region for info
            List<HNodRegisterInfo> registerInfos=new ArrayList<HNodRegisterInfo>();
            for (oss_acceptance_odRegisterInfo info:item.getAcceptanceOdRegisterInfos()){
                HNodRegisterInfo  info1=new HNodRegisterInfo();
                info1.setOilno(info.getOilno());
                info1.setManualno(info.getManualno());
                info1.setShift(info.getShift());
                info1.setBeginentertype(info.getEntertype());
                info1.setEndentertype(info.getEntertype());
                info1.setBeginoilheight(info.getBeginoilheight());
                info1.setBeginoill(info.getBeginoill());
                info1.setDeliveryno(info.getDeliveryno());
                info1.setBegintime(info.getBegintime());
                info1.setEndtime(info.getEndtime());
                info1.setDischargel(info.getDischargel());
                info1.setBeginv20l(info.getBeginv20l());
                info1.setBegintemperature(info.getBegintemperature());
                info1.setEndoilheight(info.getEndoilheight());
                info1.setEndwaterheight(info.getEndwaterheight());
                info1.setEndoill(info.getEndoill());
                info1.setEndv20l(info.getEndv20l());
                info1.setEndtemperature(info.getEndtemperature());
                info1.setDuringsales(info.getDuringsales());
                info1.setForcecancelstable(info.getForcecancelstable());
                info1.setOilcan(info.getOilcan());
                registerInfos.add(info1);
            }
            //endregion
            main.setRegisterInfos(registerInfos);

            hnmains.add(main);
        }
        return hnmains;
    }

    //发送数据到湖南
    private void addOdReg(List<AcceptOdRegMain> acceptOdRegMains){
        List<HNodRegMain>  hnmains=TransToHn(acceptOdRegMains);
        action ac=new action();
        String path=ac.getUri("resources.hn.accept.sendodreg");
        httpClient client=new httpClient();
        Map<String,String> hm=new HashMap<String, String>();
        Result result=new Result();
        try {
            String jsonResult= client.request(path, hnmains, hm);
            result=new JsonMapper().fromJson(jsonResult,Result.class);
            if (result.isResult())
            {
                //发送成功 数据更新传输状态
                for (AcceptOdRegMain item:acceptOdRegMains){
                    item.getAcceptanceOdRegister().setTranstatus("1");
                    acceptanceService.updateByPrimaryKeySelective(item.getAcceptanceOdRegister());
                }

            }
        }
        catch(Exception e)
        {

        }
    }


    @RequestMapping(value = "/addOdReg2", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("站级系统上传卸油数据，并转发湖南")
    public Object addOdReg2(@RequestBody List<HNodRegMain> acceptOdRegMains,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        try {
            //acceptanceService.addOdregAndregInfo(acceptOdRegMains);
            addOdReg2(acceptOdRegMains);
            result.setResult(true);
        }catch (Exception e){
            result.setMsg(e.getMessage());
            result.setResult(false);
        }
        return result;
    }

    //发送数据到湖南
    private void addOdReg2(List<HNodRegMain> acceptOdRegMains){
        action ac=new action();
        String path=ac.getUri("resources.hn.accept.sendodreg");
        httpClient client=new httpClient();
        Map<String,String> hm=new HashMap<String, String>();
        Result result=new Result();
        try {
            String jsonResult= client.request(path, acceptOdRegMains, hm);
            result=new JsonMapper().fromJson(jsonResult,Result.class);
            if (result.isResult())
            {
                //发送成功 数据更新传输状态
               /* for (HNodRegMain item:acceptOdRegMains){
                    item.getAcceptanceOdRegister().setTranstatus("1");

                    acceptanceService.updateByPrimaryKeySelective(item.getAcceptanceOdRegister());
                }*/

            }
        }
        catch(Exception e)
        {

        }
    }


    @RequestMapping("/transtatus")
    @ResponseBody
    @ApiOperation("转送进货验收状态至湖南接口")
    public Result tranodregstatus(@RequestBody List<OdregStatus> odregStatus){
        Result result=new Result();
        action ac=new action();
        String path=ac.getUri("resources.hn.accept.transtatus");
        httpClient client=new httpClient();
        Map<String,String> hm=new HashMap<String, String>();

        try {
            client.request(path, odregStatus, hm);
            result.setResult(true);
        }
        catch(Exception e)
        {
            result.setResult(false);
        }
        return result;
    }


    @RequestMapping("/deliverynotice")
    @ResponseBody
    @ApiOperation("通知中心接收成功")
    public Result noticeCenterbillno(@RequestParam String billnos){
        Result result=new Result();
        try {
            String[] bs=billnos.split(",");
            List<oss_acceptance_odRegister> odRegisters = new ArrayList<oss_acceptance_odRegister>();
            for (String item : bs) {
                oss_acceptance_deliveryBill odRegister = acceptanceDeliveryBillService.selectBybillno(item);
                if (odRegister != null) {
                    odRegister.setTranstatus("1");
                    acceptanceDeliveryBillService.updatedeliverybill(odRegister);
                }
            }
            result.setResult(true);
        }catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping("/delnotice")
    @ResponseBody
    @ApiOperation("通知中心作废进货验收")
    public Result noticeCenterDelbill(@RequestParam String billno,@RequestParam String nodeno){
        Result result=new Result();
        try {
            acceptanceService.delAcceptBydeliverynoandnodeno(nodeno,billno);
            result.setResult(true);
        }catch (Exception e){
            result.setResult(false);
        }
        return result;
    }
}
