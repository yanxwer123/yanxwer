package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.oss_sys_OrgUnitMapper;
import com.kld.gsm.center.domain.oss_sys_OrgUnit;
import com.kld.gsm.center.service.SysOrgUnitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

}
