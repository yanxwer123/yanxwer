package com.kld.gsm.center.web.controller;

 import com.fasterxml.jackson.core.type.TypeReference;
 import com.fasterxml.jackson.databind.JavaType;
 import com.fasterxml.jackson.databind.ObjectMapper;
 import com.kld.gsm.center.domain.*;
 import com.kld.gsm.center.domain.hn.*;
 import com.kld.gsm.center.service.AcceptanceService;
 import com.kld.gsm.center.service.HNRemoteToHnService;
 import com.kld.gsm.center.service.SysDictService;
 import com.kld.gsm.center.service.SystemManage;
 import com.kld.gsm.center.util.action;
 import com.kld.gsm.center.util.httpClient;
 import com.kld.gsm.center.util.sysOrgUnit;
 /*import com.kld.gsm.util.JsonMapper;*/
 import com.kld.gsm.center.util.JsonMapper;
 import com.mangofactory.swagger.annotations.ApiIgnore;
 import com.wordnik.swagger.annotations.ApiOperation;
 import net.sf.json.JSONArray;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.ResponseBody;


 import javax.annotation.Resource;
 import java.io.UnsupportedEncodingException;
 import java.net.URLDecoder;
 import java.net.URLEncoder;
 import java.text.SimpleDateFormat;
 import java.util.*;

/*
Created BY niyang
Created Date 2015/11/19
*/
@Controller
@RequestMapping("/sys")
public class SysManageCotroller {

    @Resource
    private SystemManage systemManage;

    @Resource
    private AcceptanceService acceptanceService;

    @Resource
    private SysDictService sysDictService;

/*    *//*
    *上传预报警设置表
    * *//*
    @RequestMapping("/addalarmpar")
    @ResponseBody
    public Object addAlarmPar(@RequestBody List<oss_sysmanage_AlarmParameter> ossSysmanageAlarmParameters ,@RequestParam("NodeNo") String NodeNo){
       Result result=new Result();
        try {
            String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
            for (oss_sysmanage_AlarmParameter item:ossSysmanageAlarmParameters){
                item.setNodeno(NodeNo);
                item.setOucode(Oucode);
            }
            systemManage.addAlarmParameter(ossSysmanageAlarmParameters);
            result.setResult(true);
        }
       catch (Exception e){
            result.setResult(false);
           result.setMsg(e.getMessage());
       }
        return result;
    }*/


    /*
   * 获取预警参数
   * */
    @RequestMapping("/getalarmpar")
    @ResponseBody
    @ApiOperation(value ="根据站编码,站级系统获取报警参数",httpMethod = "POST")
    public List<oss_sysmanage_AlarmParameter> getalarmparbynodeno(@RequestParam("NodeNo") String NodeNo){

        return  systemManage.getAlarmPars(NodeNo);
    }

    /*
    * 上传容积信息明细表
    * */
    @RequestMapping("/addcubinfo")
    @ResponseBody
    @ApiIgnore
    @ApiOperation(value ="站级上传容积表明细",httpMethod = "POST")
    public Object addCubInfo(@RequestBody List<oss_sysmanage_cubageInfo> ossSysmanageCubageInfos,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
        for (oss_sysmanage_cubageInfo item:ossSysmanageCubageInfos){
            item.setNodeno(NodeNo);
            item.setOucode(Oucode);
        }
        try {
            systemManage.addCubInfo(ossSysmanageCubageInfos);
            result.setResult(true);
        }
        catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /*
 * 上传容积表
 * */
    @RequestMapping("/addcub")
    @ResponseBody
    @ApiIgnore
    @ApiOperation(value ="站级上传容积表",httpMethod = "POST")
    public Object addCub(@RequestBody List<oss_sysmanage_cubage> ossSysmanageCubages,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
        for (oss_sysmanage_cubage item:ossSysmanageCubages){
            item.setNodeno(NodeNo);
            item.setOucode(Oucode);
        }
        try {
            systemManage.addCub(ossSysmanageCubages);
            result.setResult(true);
        }
        catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /*
    * 上传上传列表
    * */
    @RequestMapping("/adduplist")
    @ResponseBody
    @ApiOperation(value ="站级上传上传明细，转发湖南")
    public Object addUplist(@RequestBody List<oss_sysmanage_DataUploadList> ossSysmanageDataUploadLists,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
        for (oss_sysmanage_DataUploadList item:ossSysmanageDataUploadLists){
            item.setNodeno(NodeNo);
            item.setOucode(Oucode);
        }
        try {
            systemManage.addDataUploadList(ossSysmanageDataUploadLists);
            result.setResult(true);
        }
        catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /*
     * 上传单位信息表
     * */
    @RequestMapping("/adddept")
    @ResponseBody
    @ApiIgnore
    @ApiOperation(value ="站级单位信息表",httpMethod = "POST")
    public Object addDepet(@RequestBody List<oss_sysmanage_department> ossSysmanageDepartments,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
        for (oss_sysmanage_department item:ossSysmanageDepartments){
            item.setNodeno(NodeNo);
            item.setOucode(Oucode);
        }
        try {
            systemManage.addDepet(ossSysmanageDepartments);
            result.setResult(true);
        }
        catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /*
     * 上传液位仪表
     * */
    @RequestMapping("/addiq")
    @ResponseBody
    @ApiIgnore
    @ApiOperation(value ="站级上传液位配置接口",httpMethod = "POST")
    public Object addiquidInstrument(@RequestBody List<oss_sysmanage_iquidInstrument> ossSysmanageIquidInstruments,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
        for (oss_sysmanage_iquidInstrument item:ossSysmanageIquidInstruments){
            item.setNodeno(NodeNo);
            item.setOucode(Oucode);
        }
        try {
            systemManage.addiquidInstrument(ossSysmanageIquidInstruments);
            result.setResult(true);
        }
        catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /*
     * 上传油枪信息表
     * */
    @RequestMapping("/addoilgun")
    @ResponseBody
    @ApiOperation(value ="站级上传油枪信息表，转发湖南")
    public Object addOilGuninfo(@RequestBody List<oss_sysmanage_OilGunInfo> ossSysmanageOilGunInfos,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
        for (oss_sysmanage_OilGunInfo item:ossSysmanageOilGunInfos){
            item.setNodeno(NodeNo);
            item.setOucode(Oucode);
        }
        try {
           systemManage.addOilGuninfo(ossSysmanageOilGunInfos);
            sendoligun(ossSysmanageOilGunInfos);
            result.setResult(true);
        }
        catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    private void sendoligun(List<oss_sysmanage_OilGunInfo> ossSysmanageOilGunInfos){
       boolean is;
        action ac=new action();
        String path=ac.getUri("resources.hn.system.sendoilgun");
        httpClient client=new httpClient();
        Map<String,String> hm=new HashMap<String, String>();
        Result result=new Result();
        try {
            String jsonResult= client.request(path, ossSysmanageOilGunInfos, hm);
            result=new JsonMapper().fromJson(jsonResult,Result.class);
            if (result.isResult())
            {
                for (oss_sysmanage_OilGunInfo item:ossSysmanageOilGunInfos)
                {
                    item.setTranstatus("1");
                }
                systemManage.addOilGuninfo(ossSysmanageOilGunInfos);

            }
        }
        catch(Exception e)
        {

        }
    }


    /*
     * 上传油机信息表
     * */
    @RequestMapping("/addoilmac")
    @ResponseBody
    @ApiIgnore
    @ApiOperation(value ="站级上传容积表",httpMethod = "POST")
    public Object addoilmac(@RequestBody List<oss_sysmanage_OilMachineInfo> ossSysmanageOilMachineInfos,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
        for (oss_sysmanage_OilMachineInfo item:ossSysmanageOilMachineInfos){
            item.setNodeno(NodeNo);
            item.setOucode(Oucode);
        }
        try {
            systemManage.addOilMachineInfo(ossSysmanageOilMachineInfos);
            result.setResult(true);
        }
        catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /*
    * 上传机走油品编码表
    * */
    @RequestMapping("/addoiltype")
    @ResponseBody
    public Object addoilType(@RequestBody List<oss_sysmanage_oilType> ossSysmanageOilTypes,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
        for (oss_sysmanage_oilType item:ossSysmanageOilTypes){
            item.setNodeno(NodeNo);
            item.setOucode(Oucode);
        }
        try {
            systemManage.addoilType(ossSysmanageOilTypes);
            result.setResult(true);
        }
        catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /*
    * 探棒与油罐关系表
    * */
    @RequestMapping("/addpatrel")
    @ResponseBody
    public Object addPatRelation(@RequestBody List<oss_sysmanage_PaTRelation> ossSysmanagePaTRelations,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
        for (oss_sysmanage_PaTRelation item:ossSysmanagePaTRelations){
            item.setNodeno(NodeNo);
            item.setOucode(Oucode);
        }
        try {
            systemManage.addPatRelation(ossSysmanagePaTRelations);
            result.setResult(true);
        }
        catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /*
    * 探棒修正关系表
    * */
    @RequestMapping("/addprobebar")
    @ResponseBody
    public Object addprobebar(@RequestBody List<oss_sysmanage_probePar> ossSysmanageProbePars,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
        for (oss_sysmanage_probePar item:ossSysmanageProbePars){
            item.setNodeno(NodeNo);
            item.setOucode(Oucode);
        }
        try {
            systemManage.addprobebar(ossSysmanageProbePars);
            result.setResult(true);
        }
        catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /*
    * 油罐信息表
    * */
    @RequestMapping("/addtankinfo")
    @ResponseBody
    public Object addTankInfo(@RequestBody List<oss_sysmanage_TankInfo> ossSysmanageTankInfos,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
        for (oss_sysmanage_TankInfo item:ossSysmanageTankInfos){
            item.setNodeno(NodeNo);
            item.setOucode(Oucode);
        }
        try {
            systemManage.addTankInof(ossSysmanageTankInfos);
            sendtankinfo(ossSysmanageTankInfos);
            result.setResult(true);
        }
        catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    private void sendtankinfo(List<oss_sysmanage_TankInfo> ossSysmanageTankInfos){
        boolean is;
        action ac=new action();
        String path=ac.getUri("resources.hn.system.sendtank");
        httpClient client=new httpClient();
        Map<String,String> hm=new HashMap<String, String>();
        Result result=new Result();
        try {
            String jsonResult= client.request(path, ossSysmanageTankInfos, hm);
            result=new JsonMapper().fromJson(jsonResult,Result.class);
            if (result.isResult())
            {
                for (oss_sysmanage_TankInfo item:ossSysmanageTankInfos)
                {
                    item.setTranstatus("1");
                }
                systemManage.addTankInof(ossSysmanageTankInfos);
            }
        }
        catch(Exception e)
        {

        }
    }

    /*
    * 平均时点销量表
    * */
    @RequestMapping("/addtimesaleout")
    @ResponseBody
    public Object addTimeSaleOut(@RequestBody List<oss_sysmanage_timeSaleOut> ossSysmanageTimeSaleOuts,@RequestParam("NodeNo") String NodeNo){
        Result result=new Result();
        String Oucode=new sysOrgUnit().GetOuCodeByNodeNo(NodeNo);
        for (oss_sysmanage_timeSaleOut item:ossSysmanageTimeSaleOuts){
            item.setNodeno(NodeNo);
            item.setOucode(Oucode);
        }
        try {
            systemManage.addTimeSaleOut(ossSysmanageTimeSaleOuts);
            result.setResult(true);
        }
        catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /*
    * 市公司信息
    * */
    @RequestMapping(value = "/addcity")
    @ResponseBody
    @ApiOperation("接收湖南市公司信息接口")
   // public Object AddCity(@RequestBody List<HNCity> cities){
    public Object AddCity(String params){
        JsonMapper jm=new JsonMapper();
        JavaType jt=jm.createCollectionType(List.class,HNCity.class);
        List<HNCity> cities=jm.fromJson(params,jt);
        List<oss_sysmanage_City> ossSysmanageCities=new ArrayList<oss_sysmanage_City>();
         for (HNCity item:cities){
             oss_sysmanage_City city=new oss_sysmanage_City();
             city.setCode(item.getCode());
             city.setName(item.getName());
             ossSysmanageCities.add(city);
         }

        Result result=new Result();
        try {
            systemManage.addCity(ossSysmanageCities);
            result.setResult(true);
        }
        catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /*
    * 片区信息
    * */
    @RequestMapping("/addarea")
    @ResponseBody
    @ApiOperation("接收湖南片区信息接口")
   // public Object AddArea(@RequestBody List<HNArea> areas ){
    public Object AddArea(String params){
        JsonMapper jm=new JsonMapper();
        JavaType jt=jm.createCollectionType(List.class,HNArea.class);
        List<HNArea> areas=jm.fromJson(params,jt);
        List<oss_sysmanage_Area>ossSysmanageAreas=new ArrayList<oss_sysmanage_Area>();
        for (HNArea item:areas){
            oss_sysmanage_Area area=new oss_sysmanage_Area();
            area.setName(item.getNAME());
            area.setCode(item.getCODE());
            area.setCity(item.getCITY());

            ossSysmanageAreas.add(area);
        }
        Result result=new Result();
        try {
            systemManage.addArea(ossSysmanageAreas);
            result.setResult(true);
        }
        catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /*
    * 油站信息
    * */
    @RequestMapping("/addstation")
    @ResponseBody
    @ApiOperation("接收湖南油站信息接口")
   // public Object AddStation(@RequestBody List<HNStation> stations){
    public Object AddStation(String params){
        JsonMapper jm=new JsonMapper();
        JavaType jt=jm.createCollectionType(List.class,HNStation.class);
        List<HNStation> stations=jm.fromJson(params,jt);
        List<oss_sysmanage_Station> ossSysmanageStations=new ArrayList<oss_sysmanage_Station>();
        for (HNStation item:stations){
            oss_sysmanage_Station station=new oss_sysmanage_Station();
            station.setStationCode(item.getSTATION_CODE());
            station.setStationName(item.getSTATION_NAME());
            station.setShortName(item.getSHORT_NAME());
            station.setDsgs(item.getDSGS());
            station.setLsgc(item.getLSGC());
            station.setLspq(item.getLSPQ());
            station.setKcdd(item.getKCDD());

            ossSysmanageStations.add(station);
        }
        Result result=new Result();
        try {
            systemManage.addStation(ossSysmanageStations);
            result.setResult(true);
        }
        catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

      /*
      *  油库信息
      * */
    @RequestMapping("/adddepot")
    @ResponseBody
    @ApiOperation("接收湖南油库信息接口")
   // public Object AddDepot(@RequestBody List<HNOilDepot> oilDepots){
    public Object AddDepot(String params){
        JsonMapper jm=new JsonMapper();
        JavaType jt=jm.createCollectionType(List.class,HNOilDepot.class);
        List<HNOilDepot> oilDepots=jm.fromJson(params,jt);

        List<oss_sysmanage_Depot> ossSysmanageDepots=new ArrayList<oss_sysmanage_Depot>();
        for (HNOilDepot item:oilDepots){
            oss_sysmanage_Depot depot=new oss_sysmanage_Depot();
            depot.setYkId(item.getCODE());
            depot.setYkName(item.getNAME());

            ossSysmanageDepots.add(depot);
        }
        Result result=new Result();
        try {
            systemManage.addDepot(ossSysmanageDepots);
            result.setResult(true);
        }
        catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /*
    * 报警参数 湖南调用接口
    * */
    @RequestMapping("/addiqalarm")
    @ResponseBody
    @ApiOperation("接收湖南报警参数数据接口")
    //public  Object AddAlarmPar(@RequestBody List<HNIQ> hniqs){
    public  Object AddAlarmPar(String params){
        JsonMapper jm=new JsonMapper();
        JavaType jt=jm.createCollectionType(List.class,HNIQ.class);
        List<HNIQ> hniqs=jm.fromJson(params,jt);

        Result result=new Result();
        List<oss_sysmanage_AlarmParameter> ossSysmanageAlarmParameters=new ArrayList<oss_sysmanage_AlarmParameter>();
        try {
        for (HNIQ item:hniqs){
            oss_sysmanage_AlarmParameter  osap=new oss_sysmanage_AlarmParameter();
            osap.setNodeno(item.getNodeNo());
            osap.setTranstatus("0");
            osap.setHighalarm(item.getHighAlarm());
            osap.setHighprealarm(item.getHighPreAlarm());
            osap.setHightempalarm(item.getHighTempAlarm());
            osap.setOilcan(item.getOilcan());
            osap.setLastoptime(new Date());
            osap.setLowalarm(item.getLowAlarm());
            osap.setLowprealarm(item.getLowPreAlarm());
            osap.setLowtempalarm(item.getLowTempAlarm());
            osap.setWateralarm(item.getWaterAlarm());

            oss_sys_OrgUnit ossSysOrgUnit = acceptanceService.GetOrgUnit(item.getNodeNo());
            if (ossSysOrgUnit != null) {
                osap.setOucode(ossSysOrgUnit.getOucode());
            }
            ossSysmanageAlarmParameters.add(osap);
        }
        systemManage.addAlarmParameter(ossSysmanageAlarmParameters);
        result.setResult(true);
        }
        catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /*
    * 容积表湖南调用接口 add
    * */
    @RequestMapping("/addcubefromhunan")
    @ResponseBody
    @ApiOperation("接收湖南容积表数据接口")
    //public Object addCubeFromhunan(@RequestBody List<HNRGBMain> hnrgbMains){
    public Object addCubeFromhunan(String params){
        JsonMapper jm=new JsonMapper();
        JavaType jt=jm.createCollectionType(List.class,HNRGBMain.class);
        List<HNRGBMain> hnrgbMains=jm.fromJson(params,jt);

        Result result=new Result();
        List<oss_sysmanageCubgeMain> ossSysmanageCubgeMains=new ArrayList<oss_sysmanageCubgeMain>();
        try {
            for (HNRGBMain item : hnrgbMains) {
                oss_sysmanageCubgeMain cubgeMain = new oss_sysmanageCubgeMain();

                //主表对象转换
                oss_sysmanage_cubage cubge = new oss_sysmanage_cubage();
                HNRGB rgb = item.getRgb();
                cubge.setVersion(rgb.getVER_NO());
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date fh_date=sdf.parse(rgb.getCHECK_DATE());
                cubge.setUpdatetime(fh_date);
                cubge.setOilcan(rgb.getTANK_CODE());
                cubge.setNodeno(rgb.getSTATION_CODE());
                cubge.setTranstatus("0");
                cubge.setStatus(1);
                oss_sys_OrgUnit ossSysOrgUnit = acceptanceService.GetOrgUnit(rgb.getSTATION_CODE());
                if (ossSysOrgUnit != null) {
                    cubge.setOucode(ossSysOrgUnit.getOucode());
                }
                cubgeMain.setCubage(cubge);
                List<oss_sysmanage_cubageInfo> ossSysmanageCubageInfos = new ArrayList<oss_sysmanage_cubageInfo>();
                for (HNRGBInfo info : item.getHnrgbInfos()) {
                    //构建明细表
                    oss_sysmanage_cubageInfo cinfo = new oss_sysmanage_cubageInfo();
                    cinfo.setOucode(cubge.getOucode());
                    cinfo.setNodeno(cubge.getNodeno());
                    cinfo.setOilcan(cubge.getOilcan());
                    cinfo.setVersion(cubge.getVersion());
                    cinfo.setHeight(info.getWEIGHT());
                    cinfo.setLiter(info.getVOLUMN());
                    cinfo.setTranstatus("0");
                    ossSysmanageCubageInfos.add(cinfo);
                }
                cubgeMain.setCubageInfos(ossSysmanageCubageInfos);

                ossSysmanageCubgeMains.add(cubgeMain);
            }
            systemManage.addRGBMainFromHN(ossSysmanageCubgeMains);
            result.setResult(true);
        }
        catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /*
  * 容积表获取
  * */
    @RequestMapping("/getcubefromhunan")
    @ResponseBody
    @ApiOperation("湖南容积表获取")
    public Object getCubeFromhunan(@RequestParam String NodeNo) throws Exception {
            Result result=new Result();
            action ac = new action();
            String cubePath = ac.getUri("resources.hn.system.getcube");
            Map<String, String> hm = new HashMap<String, String>();
            hm.put("nodeno",NodeNo);
            //发送到湖南
            httpClient client = new httpClient();
            String JsonReault = client.request(cubePath, null, hm);
           /* JsonMapper jm=new JsonMapper();
            JavaType jt=jm.createCollectionType(List.class,HNRGBMain.class);*/
        //System.out.println(JsonReault);
        ObjectMapper op=new ObjectMapper();
        List<HNRGBMain> hnrgbMainLst = op.readValue(JsonReault,
                new TypeReference<List<HNRGBMain>>() {
                });
            //List<HNRGBMain>  hnrgbMainLst= new JsonMapper().fromJson(JsonReault,jt);
            List<oss_sysmanageCubgeMain> ossSysmanageCubgeMains=new ArrayList<oss_sysmanageCubgeMain>();
            try {
                for(HNRGBMain item:hnrgbMainLst) {
                    oss_sysmanageCubgeMain cubgeMain = new oss_sysmanageCubgeMain();
                    //主表对象转换
                    oss_sysmanage_cubage cubge = new oss_sysmanage_cubage();
                    HNRGB rgb = item.getRgb();
                    cubge.setVersion(rgb.getVER_NO());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date fh_date = sdf.parse(rgb.getCHECK_DATE());
                    cubge.setUpdatetime(fh_date);
                    cubge.setOilcan(rgb.getTANK_CODE());
                    cubge.setNodeno(rgb.getSTATION_CODE());
                    cubge.setTranstatus("1");//改变状态
                    cubge.setStatus(1);
                    oss_sys_OrgUnit ossSysOrgUnit = acceptanceService.GetOrgUnit(rgb.getSTATION_CODE());
                    if (ossSysOrgUnit != null) {
                        cubge.setOucode(ossSysOrgUnit.getOucode());
                    }
                    cubgeMain.setCubage(cubge);
                    List<oss_sysmanage_cubageInfo> ossSysmanageCubageInfos = new ArrayList<oss_sysmanage_cubageInfo>();
                    for (HNRGBInfo info : item.getHnrgbInfos()) {
                        //构建明细表
                        oss_sysmanage_cubageInfo cinfo = new oss_sysmanage_cubageInfo();
                        cinfo.setOucode(cubge.getOucode());
                        cinfo.setNodeno(cubge.getNodeno());
                        cinfo.setOilcan(cubge.getOilcan());
                        cinfo.setVersion(cubge.getVersion());
                        cinfo.setHeight(info.getWEIGHT());
                        cinfo.setLiter(info.getVOLUMN());
                        ossSysmanageCubageInfos.add(cinfo);
                    }
                    cubgeMain.setCubageInfos(ossSysmanageCubageInfos);

                    ossSysmanageCubgeMains.add(cubgeMain);
                }
                systemManage.addRGBMainFromHN(ossSysmanageCubgeMains);
            //插入center数据库
            result.setResult(true);
           // result.setRows(ossSysmanageCubgeMains);
        }
        catch (Exception ex){
            result.setMsg(ex.getMessage());
            result.setResult(false);
        }
        return  result;
    }


    /*
    * 根据nodeno获取容积表
    * */
    @RequestMapping("/getcubebynodeno")
    @ResponseBody
    @ApiOperation("站级系统根据站编码获取容积表")
    public List<oss_sysmanageCubgeMain> getcubgeByNodeno(@RequestParam("NodeNo")String NodeNo){

        //defined main list
        List<oss_sysmanageCubgeMain> mains=new ArrayList<oss_sysmanageCubgeMain>();

        //get cubge list
        List<oss_sysmanage_cubage> ossSysmanageCubages=systemManage.getcubgesByNodenoAndStatus(NodeNo,1);

        // for cubge list
        for (oss_sysmanage_cubage item:ossSysmanageCubages){
            //create main
            oss_sysmanageCubgeMain main=new oss_sysmanageCubgeMain();
            main.setCubage(item);
            main.setCubageInfos(systemManage.getcubgeInfosByNodenoAndVersionandcanno(item.getNodeno(),item.getVersion(),item.getOilcan()));
            mains.add(main);
        }
        return mains;
    }


    @RequestMapping("/synsys")
    @ResponseBody
    @ApiIgnore
    @ApiOperation("站级系统同步基础信息表")
    public Object synSys(@RequestBody SysmanageSynMain main,@RequestParam("NodeNo") String NodeNo){

         Result rs=new Result();
        try {
            systemManage.synMain(main,NodeNo);
            rs.setResult(true);
        }
        catch (Exception e){
            rs.setResult(false);
            rs.setMsg(e.getMessage());
        }
        return  rs;
    }

    @RequestMapping("/oiltype")
    @ResponseBody
    @ApiOperation("推送油品信息给湖南")
    public Result synOiltype()
    {
        List<oss_sysmanage_oilType> ossSysmanageOilTypes=systemManage.selectuseoiltype();
        List<HNOilType> oilTypes=new ArrayList<HNOilType>();
        for (oss_sysmanage_oilType item:ossSysmanageOilTypes){
            HNOilType hnOilType=new HNOilType();
            hnOilType.setOilno(item.getOilno());
            hnOilType.setOilname(item.getOilname());
            hnOilType.setOiltype(item.getOiltype());
            hnOilType.setOilno(item.getInuseflag());
            oilTypes.add(hnOilType);
        }
        action ac=new action();
        String path=ac.getUri("resources.hn.system.sendoiltype");

        httpClient client=new httpClient();
        Map<String,String> hm=new HashMap<String, String>();
        Result result=new Result();
        try {
            String jsonResult= client.request(path, oilTypes, hm);
            result.setResult(true);

        }
        catch(Exception e)
        {
            result.setResult(false);
        }
        return result;
    }
    @Resource
    private HNRemoteToHnService hnRemoteToHnService;

    @RequestMapping("/remotepd")
    @ResponseBody
    @ApiOperation("远程盘点,ls")
    public List<HNRemote> remotepd(@RequestParam("nodeno") String nodeno){
       /* List<HNRemote> remotes=new ArrayList<HNRemote>();
        List<HNGunInfo> info=new ArrayList<HNGunInfo>();
        HNGunInfo item=new HNGunInfo();
        item.setNodeno("123");
        item.setStartTime(new Date());
        item.setEndTime(new Date());
        item.setOilcan(1);
        item.setUnloadQty(1.1);
        item.setBackTankQty(22);
        item.setSaleQty(33);
        item.setStartStock(234);
        item.setEndStock(33);
        item.setLossQty(22);
        item.setLossRate(0.2);

        info.add(item);

        HNRemote r1=new HNRemote();
        r1.setNodeno("123");
        r1.setPdinfos(info);
        remotes.add(r1);

        List<HNGunInfo> info1=new ArrayList<HNGunInfo>();
        HNGunInfo item1=new HNGunInfo();
        item1.setNodeno("罐号");
        item1.setStartTime(new Date());
        item1.setEndTime(new Date());
        item1.setOilcan(1);
        item1.setUnloadQty(1.1);
        item1.setBackTankQty(22);
        item1.setSaleQty(33);
        item1.setStartStock(234);
        item1.setEndStock(33);
        item1.setLossQty(22);
        item1.setLossRate(0.2);
        info1.add(item1);*/

        /*HNRemote r2=new HNRemote();
        r2.setNodeno("124");
        r2.setPdinfos(info);
        remotes.add(r2);*/



       return hnRemoteToHnService.GetRemotetoHnInfo(nodeno);

    }

    @RequestMapping("/getdicbyversion")
    @ResponseBody
    @ApiOperation("根据version获取字典")
    public List<SysManageDict> getdicbyversion(@RequestParam("version") Integer version){
        List<SysManageDict> dicts=sysDictService.selectbyVersion(version);
        for (SysManageDict item:dicts){
            try {
                item.setName(URLEncoder.encode(item.getName(),"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            };
        }
        return dicts;
    }

}
