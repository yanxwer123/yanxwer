package com.kld.gsm.center.web.webcontroller;


import com.kld.gsm.center.common.RedisUtil;
import com.kld.gsm.center.service.SysOrgUnitService;
import com.kld.gsm.center.service.SystemManage;
import com.mangofactory.swagger.annotations.ApiIgnore;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.kld.gsm.center.domain.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/*
Created BY niyang
Created Date 2015/11/24
description： 基础信息
*/
@Controller
@ApiIgnore
@RequestMapping("/web/basic")
@SuppressWarnings("all")
public class WebBasicInfoController  extends WebBaseController{
    private static final Logger LOG = Logger.getLogger("taskpolling");
    @Resource
    private SystemManage systemManage;

    @RequestMapping("/getcitys")
    @ResponseBody
    public List<oss_sysmanage_City> GetCitys(){
        return systemManage.getAllCitys();
    }

    //region creted by xhz 2015/12/16 按照parentOUCde获取OrgUnit信息

    @Resource
    private SysOrgUnitService sysOrgUnitService;
    @RequestMapping("/getorgunitsbypoucode")
    @ResponseBody
    public List<oss_sys_OrgUnit> getOrgUnitsByPOUCode(@RequestParam("parentoucode")String parentoucode)
    {
       return sysOrgUnitService.selectByPOUCode(parentoucode);
    }
    //endregion

    @RequestMapping("/getareas")
    @ResponseBody
    public List<oss_sysmanage_Area> getareasbycity(@RequestParam("cno")String cno){
        return systemManage.getAreasByCity(cno);
    }

    @RequestMapping("/getstation")
    @ResponseBody
    public List<oss_sysmanage_Station> getstation(@RequestParam("ano")String ano){
        return systemManage.getStationsByArea(ano);
    }

    @RequestMapping("/getlossstation")
    @ResponseBody
    public ResultMsg getkeys(String keys){

        LOG.info(new Date().toString());
        ResultMsg resultMsg=new ResultMsg();
        RedisUtil pRedisUtil=new RedisUtil();

        Set gunjsons=null;
        if (keys==null||keys.equals("")) {
            gunjsons=pRedisUtil.getValues("*time");
        }else{
            gunjsons=pRedisUtil.getValues(keys.trim()+"time");
        }
        if (gunjsons==null) return null;
        Iterator it = gunjsons.iterator();
        List<StationStatus> stationStatuses=new ArrayList<StationStatus>();
         while (it.hasNext()) {
            String key = (String) it.next();
            String value = pRedisUtil.getValue(key);
            StationStatus stationStatus=new StationStatus();
             stationStatus.setNodeno(key.replace("time", ""));
             stationStatus.setUpdateTime(new Date(Long.parseLong(value)));
             stationStatus.setLosttime(new Date().getTime() - Long.parseLong(value));
             if (stationStatus.getLosttime()>5*60*1000) {
                 stationStatuses.add(stationStatus);
             }
         }
        Collections.reverse(stationStatuses);
        if (stationStatuses!=null&&stationStatuses.size()>0) {
            List<oss_sysmanage_department> departments = systemManage.selectbyid(stationStatuses);
            for (StationStatus item : stationStatuses) {
                for (oss_sysmanage_department dept : departments) {
                    if (item.getNodeno().equals(dept.getSinopecnodeno())) {
                        item.setAreano(dept.getAreadesc());
                        item.setNodename(dept.getNodetag());
                        departments.remove(dept);
                        break;
                    }
                }
            }
        }
        LOG.info(new Date().toString());
        resultMsg.setRows(stationStatuses);
        if (stationStatuses==null) {
            resultMsg.setTotal(0);
        }else  {
            resultMsg.setTotal(stationStatuses.size());
        }
        return resultMsg;
    }

}


