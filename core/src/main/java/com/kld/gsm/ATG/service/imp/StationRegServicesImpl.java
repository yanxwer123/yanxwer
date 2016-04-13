package com.kld.gsm.ATG.service.imp;

import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.ATG.service.StationRegServices;
import com.kld.gsm.ATG.utils.action;
import com.kld.gsm.ATG.utils.httpClient;
import com.kld.gsm.ATG.utils.param;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kld.gsm.ATG.dao.*;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/*
Created BY niyang
Created Date 2015/11/21
*/
@Service
@SuppressWarnings("all")
public class StationRegServicesImpl implements StationRegServices {
    @Autowired
    private SysManageDepartmentDao sysManageDepartmentDao;
    @Transactional(rollbackFor = Exception.class)
    public boolean syndept(String host) {
        //获取action地址
        action ac=new action();
        String path=ac.getUri(host,"resource.services.sys.syndept");
        //获取站级数据
        List<SysManageDepartment> sysManageAlarmParameters= sysManageDepartmentDao.selectByTrans("0");
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,sysManageAlarmParameters,hm);
            for (SysManageDepartment item:sysManageAlarmParameters){
                item.setTranstatus("1");
                sysManageDepartmentDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Autowired
    private com.kld.gsm.ATG.dao.SysManageIquidInstrumentDao sysManageIquidInstrumentDao;
    @Transactional(rollbackFor = Exception.class)
    public boolean syniq(String host) {
        //获取action地址
        action ac=new action();
        String path=ac.getUri(host,"resource.services.sys.syniq");
        //获取站级数据
        List<SysManageIquidInstrument> sysManageAlarmParameters= sysManageIquidInstrumentDao.selectByTrans("0");
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,sysManageAlarmParameters,hm);
            for (SysManageIquidInstrument item:sysManageAlarmParameters){
                item.setTranstatus("1");
                sysManageIquidInstrumentDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Autowired
    private com.kld.gsm.ATG.dao.SysManageOilGunInfoDao sysManageOilGunInfoDao;
    @Transactional(rollbackFor = Exception.class)
    public boolean synoilgun(String host) {
        //获取action地址
        action ac=new action();
        String path=ac.getUri(host,"resource.services.sys.synoilgun");
        //获取站级数据
        List<SysManageOilGunInfo> sysManageAlarmParameters= sysManageOilGunInfoDao.selectByTrans("0");
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,sysManageAlarmParameters,hm);
            for (SysManageOilGunInfo item:sysManageAlarmParameters){
                item.setTranstatus("1");
                sysManageOilGunInfoDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Autowired
    private com.kld.gsm.ATG.dao.SysManageOilMachineInfoDao sysManageOilMachineInfoDao;
    @Transactional(rollbackFor = Exception.class)
    public boolean synoilmac(String host) {
        //获取action地址
        action ac=new action();
        String path=ac.getUri(host,"resource.services.sys.synoilmac");
        //获取站级数据
        List<SysManageOilMachineInfo> sysManageAlarmParameters= sysManageOilMachineInfoDao.selectByTrans("0");
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,sysManageAlarmParameters,hm);
            for (SysManageOilMachineInfo item:sysManageAlarmParameters){
                item.setTranstatus("1");
                sysManageOilMachineInfoDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Autowired
    private com.kld.gsm.ATG.dao.SysManagePaTRelationDao sysManagePaTRelationDao;
    @Transactional(rollbackFor = Exception.class)
    public boolean synpatrel(String host) {
        //获取action地址
        action ac=new action();
        String path=ac.getUri(host,"resource.services.sys.synpatrel");
        //获取站级数据
        List<SysManagePaTRelation> sysManageAlarmParameters= sysManagePaTRelationDao.selectByTrans("0");
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,sysManageAlarmParameters,hm);
            for (SysManagePaTRelation item:sysManageAlarmParameters){
                item.setTranstatus("1");
                sysManagePaTRelationDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Autowired
    private  com.kld.gsm.ATG.dao.SysManageProbeParDao sysManageProbeParDao;
    @Transactional(rollbackFor = Exception.class)
    public boolean synprobebar(String host) {
        //获取action地址
        action ac=new action();
        String path=ac.getUri(host,"resource.services.sys.synprobebar");
        //获取站级数据
        List<SysManageProbePar> sysManageAlarmParameters= sysManageProbeParDao.selectByTrans("0");
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,sysManageAlarmParameters,hm);
            for (SysManageProbePar item:sysManageAlarmParameters){
                item.setTranstatus("1");
                sysManageProbeParDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Autowired
    private com.kld.gsm.ATG.dao.SysManageCanInfoDao sysManagecanInfoDao;
    @Transactional(rollbackFor = Exception.class)
     public boolean syntankinfo(String host) {
        //获取action地址
        action ac=new action();
        String path=ac.getUri(host,"resource.services.sys.syntankinfo");
        //获取站级数据

        List<SysManageCanInfo> sysManageAlarmParameters= sysManagecanInfoDao.selectByTrans("0");
         Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,sysManageAlarmParameters,hm);
            for (SysManageCanInfo item:sysManageAlarmParameters){
                item.setTranstatus("1");
                  sysManagecanInfoDao.updateByPrimaryKey(item);
             }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Resource
    private SysManageOilTypeDao typeDao;


    /**
     * @param host
     * @description 同步基础信息，主调度
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean synsys(String host) {

        //取本地数据
        SysmanageSynMain main=new SysmanageSynMain();
        main.setDepartment(sysManageDepartmentDao.selectfirst().get(0));

        main.setOilGunInfos(sysManageOilGunInfoDao.selectByTrans("0"));
        main.setOilmacs(sysManageOilMachineInfoDao.selectByTrans("0"));
        main.setTankInfos(sysManagecanInfoDao.selectByTrans("0"));
        main.setOilTypes(typeDao.selectByTrans("0"));

        //获取action地址
        action ac=new action();
        String path=ac.getUri(host, "resource.services.sys.synsys");

        Map<String,String> hm=new param().getparam();
        hm.put("NodeNo",main.getDepartment().getSinopecnodeno());

        //发送站级数据
        httpClient client=new httpClient();
        try {
            String rs= client.request(path,main,hm);
            Result rsm=new JsonMapper().fromJson(rs,Result.class);
            if (rsm.isResult()){
                //更新dept状态
                main.getDepartment().setTranstatus("1");
                sysManageDepartmentDao.updateByPrimaryKey(main.getDepartment());
                //更新 oilgun
                for(SysManageOilGunInfo item:main.getOilGunInfos()){
                    item.setTranstatus("1");
                    sysManageOilGunInfoDao.updateByPrimaryKeySelective(item);
                }
                //更新oilmachine
                for (SysManageOilMachineInfo item:main.getOilmacs()){
                    item.setTranstatus("1");
                    sysManageOilMachineInfoDao.updateByPrimaryKeySelective(item);
                }
                //更新tank
                for (SysManageCanInfo item:main.getTankInfos()){
                    item.setTranstatus("1");
                    sysManagecanInfoDao.updateByPrimaryKey(item);
                }
                //更新oiltype
                for (SysManageOilType item:main.getOilTypes()){
                    item.setTranstatus("1");
                    typeDao.updateByPrimaryKey(item);
                }
            }else  {
               // nothing
            }
        } catch (Exception e) {
            //e.printStackTrace();

            return false;
        }
        return true;


    }
}
