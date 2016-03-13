package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.oss_sys_OrgUnitMapper;
import com.kld.gsm.center.domain.oss_sys_OrgUnit;
import com.kld.gsm.center.domain.oss_sysmanage_department;
import com.kld.gsm.center.service.SysOrgUnitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/*
Created BY niyang
Created Date 2015/11/19
*/
@Service("SysOrgUnitService")
public class SysOrgUnitServiceImpl implements SysOrgUnitService {

    @Resource
    private oss_sys_OrgUnitMapper ossSysOrgUnitMapper;

    public oss_sys_OrgUnit GetOrgByNodeNo(String nodeNo) {
        return ossSysOrgUnitMapper.selectByPrimaryKey(nodeNo);
    }

    @Override
    public oss_sys_OrgUnit selectByOUCode(String oucode) {
        return ossSysOrgUnitMapper.selectByOUCode(oucode);
    }

    @Override
    public List<oss_sys_OrgUnit> selectByPOUCode(String parentoucode) {
        List<oss_sys_OrgUnit> list=ossSysOrgUnitMapper.selectByPOUCode(parentoucode);
      /*  oss_sys_OrgUnit model=new oss_sys_OrgUnit();
        model.setOucode("0");
        model.setOuname("--请选择--");
        list.add(model);*/
        return list;
    }

    @Override
    public List<HashMap<String, Object>> selectOUInfo(String oucode) {
        HashMap map=new HashMap();
        if (oucode!=null&&oucode!="") {
            map.put("oucode", oucode+"%");
        }
        else
        {
            map.put("oucode", oucode);
        }
        return ossSysOrgUnitMapper.selectOUInfo(map);
    }

    @Override
    public String addOrgUnit(oss_sysmanage_department department) {
        //if station exist, return
        oss_sys_OrgUnit orgUnit=ossSysOrgUnitMapper.selectByPrimaryKey(department.getNodeno());
        if (orgUnit!=null){
            return null;
        }
        //check city exist
        oss_sys_OrgUnit city=ossSysOrgUnitMapper.selectByPrimaryKey(department.getNodeno());
        if (city==null){
            city.setShortname(department.getNodename());
            city.setNodeno(department.getProvinceno());
            city.setShortname(department.getProvincedesc());


        }
        //check area exist,if not add

        //add station
        return null;
    }

    private String CalculateOuCode(oss_sysmanage_department department){

        return "";
    }

    private boolean addcity(oss_sysmanage_department department){
        try {
            //select by nodeno
            oss_sys_OrgUnit sys_orgUnit = ossSysOrgUnitMapper.selectByPrimaryKey(department.getProvinceno());
            //city exit return oucode
            if (sys_orgUnit == null) {
                sys_orgUnit = new oss_sys_OrgUnit();
                sys_orgUnit.setShortname(department.getProvincedesc());
                sys_orgUnit.setNodeno(department.getProvinceno());
                sys_orgUnit.setCreateddate(new Date());
                sys_orgUnit.setOulevel(1);
                sys_orgUnit.setIsunit(1);
                sys_orgUnit.setParentoucode("100");
                String oucode = "";
                String maxoucode = ossSysOrgUnitMapper.selectMaxOucodeByParentOucode("100");
                if (maxoucode == null || maxoucode.equals("")) {
                    oucode = sys_orgUnit.getOucode() + "001";
                } else {
                    Integer ouint = Integer.parseInt(maxoucode);
                    ouint = ouint + 1;
                    oucode = ouint.toString();
                }
                sys_orgUnit.setOucode(oucode);
                ossSysOrgUnitMapper.insert(sys_orgUnit);
            }
        }catch (Exception e){
            return false;
        }

        return true;
    }



    private boolean CheckArea(oss_sysmanage_department department){
        //get city
        oss_sys_OrgUnit city=ossSysOrgUnitMapper.selectByPrimaryKey(department.getProvinceno());
        return true;
    }

    private String GetStationCode(String AreaCode){
        return "";
    }
}
