package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.*;
import com.kld.gsm.center.domain.*;
import com.kld.gsm.center.service.AcceptanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xhz on 2015/11/21.
 */
@Service
public class AcceptanceServiceImpl implements AcceptanceService {
    @Resource
    private oss_acceptance_deliveryPlanMapper ossAcceptanceDeliveryPlanMapper;
    @Resource
    private oss_sys_OrgUnitMapper ossSysOrgUnitMapper;
    @Resource
    private oss_sysmanage_StationMapper ossSysmanageStationMapper;
    @Resource
    private oss_acceptance_odRegisterMapper ossAcceptanceOdRegisterMapper;
    @Resource
    private oss_acceptance_odRegisterInfoMapper ossAcceptanceOdRegisterInfoMapper;
    @Resource
    private oss_acceptance_deliveryBillMapper ossAcceptanceDeliveryBillMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int GetDeliveryPlan(List<oss_acceptance_deliveryPlan> ossAcceptanceDeliveryPlans) {  //配送单下发

        for (oss_acceptance_deliveryPlan item:ossAcceptanceDeliveryPlans)
        {
            ossAcceptanceDeliveryPlanMapper.merge(item);//执行merge操作
        }
        return 1;
    }

    //获取NodeNo
     public oss_sysmanage_Station GetStationInfo(String lsgc, String kcgg) {
        oss_sysmanage_Station ossSysmanageStation=ossSysmanageStationMapper.selectByLsgcandKcdd(lsgc,kcgg);//根据零售工厂和库存站点获取NodeNo
            return ossSysmanageStation;
    }


    public oss_sys_OrgUnit GetOrgUnit(String nodeno) {
        //获取OUCode
        oss_sys_OrgUnit ossSysOrgUnit=ossSysOrgUnitMapper.selectByPrimaryKey(nodeno);//根据NodeNo获取OUCode
        return ossSysOrgUnit;
    }

    @Transactional(rollbackFor = Exception.class)
    public int addOdregAndregInfo(List<AcceptOdRegMain> acceptOdRegMains,String nodeno,String oucode) {

        for (AcceptOdRegMain item:acceptOdRegMains){
            item.getAcceptanceOdRegister().setOucode(oucode);
            item.getAcceptanceOdRegister().setNodeno(nodeno);
            ossAcceptanceOdRegisterMapper.merge(item.getAcceptanceOdRegister());
            ossAcceptanceOdRegisterInfoMapper.deleteByDeliveryNo(item.getAcceptanceOdRegister().getManualno());
            for (oss_acceptance_odRegisterInfo info:item.getAcceptanceOdRegisterInfos()){
                info.setNodeno(nodeno);
                info.setOucode(oucode);
                ossAcceptanceOdRegisterInfoMapper.insert(info);
            }
        }

        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(oss_acceptance_odRegister record) {
        ossAcceptanceOdRegisterMapper.updateByPrimaryKeySelective(record);
        return 1;
    }

    @Override
    public int delAcceptBydeliverynoandnodeno(String nodeno, String deliveryno) {
        oss_acceptance_odRegisterKey key=new oss_acceptance_odRegisterKey();
        key.setManualno(deliveryno);
        key.setNodeno(nodeno);
        //ossAcceptanceOdRegisterMapper.deleteByPrimaryKey(key);
        ossAcceptanceOdRegisterMapper.deletebybillnoAndnodeno(deliveryno,nodeno);
        ossAcceptanceOdRegisterInfoMapper.deleteByDeliveryNo(deliveryno);
        return 1;
    }

    @Override
    public List<HashMap<String, Object>> selectYYS(String oiltype,String start,String end, String oucode) {
        HashMap map=new HashMap();
        map.put("oiltype", oiltype);
        map.put("start", start);
        map.put("end", end);
        if (oucode!=null&&oucode!="") {
            map.put("oucode", oucode+"%");
        }
        else
        {
            map.put("oucode", oucode);
        }
        return ossAcceptanceOdRegisterMapper.selectYYS(map);
    }
}
