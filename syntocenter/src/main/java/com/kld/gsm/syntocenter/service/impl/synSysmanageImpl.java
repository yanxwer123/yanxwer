package com.kld.gsm.syntocenter.service.impl;


import com.kld.gsm.ATG.dao.SysManageCanInfoDao;
import com.kld.gsm.ATG.dao.SysManageOilTypeDao;
import com.kld.gsm.ATG.dao.SysManagePaTRelationDao;
import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.syntocenter.service.synSysManage;
import com.kld.gsm.syntocenter.util.action;
import com.kld.gsm.syntocenter.util.httpClient;
import com.kld.gsm.syntocenter.util.param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/*
Created BY niyang
Created Date 2015/11/19
*/
@SuppressWarnings("all")
@Service
public class synSysmanageImpl implements synSysManage {

    @Autowired
    private com.kld.gsm.ATG.dao.SysManageAlarmParameterDao sysManageAlarmParameterDao;
    public boolean synAlarmPar() {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.sys.synAlarmPar");
        //获取站级数据
        List<SysManageAlarmParameter> sysManageAlarmParameters= sysManageAlarmParameterDao.selectByTrans("0");
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,sysManageAlarmParameters,hm);
            for (SysManageAlarmParameter item:sysManageAlarmParameters){
                item.setTranstatus("1");
                sysManageAlarmParameterDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;


    }

    @Autowired
    private com.kld.gsm.ATG.dao.SysManageCubageDao sysManageCubageDao;
    public boolean syncub() {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.sys.syncub");
        //获取站级数据
        List<SysManageCubage> sysManageAlarmParameters= sysManageCubageDao.selectByTrans("0");
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,sysManageAlarmParameters,hm);
            for (SysManageCubage item:sysManageAlarmParameters){
                //item.setTranstatus("1");
                sysManageCubageDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Autowired
    private com.kld.gsm.ATG.dao.SysManageCubageInfoDao sysManageCubageInfoDao;
    public boolean syncubinfo() {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.sys.syncubinfo");
        //获取站级数据
        List<SysManageCubageInfo> sysManageAlarmParameters= sysManageCubageInfoDao.selectByTrans("0");
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,sysManageAlarmParameters,hm);
            for (SysManageCubageInfo item:sysManageAlarmParameters){
                //item.setTranstatus("1");
                sysManageCubageInfoDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Autowired
    private com.kld.gsm.ATG.dao.SysManageDepartmentDao sysManageDepartmentDao;
    @Override
    public boolean syndept() {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.sys.syndept");
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
    @Override
    public boolean syniq() {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.sys.syniq");
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

    @Override
    public boolean synoilgun() {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.sys.synoilgun");
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
    @Override
    public boolean synoilmac() {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.sys.synoilmac");
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
    private SysManageOilTypeDao sysManageOilTypeDao;
    @Override
    public boolean synoiltype() {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.sys.synoiltype");
        //获取站级数据
        List<SysManageOilType> sysManageAlarmParameters= sysManageOilTypeDao.selectByTrans("0");
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,sysManageAlarmParameters,hm);
            for (SysManageOilType item:sysManageAlarmParameters){
                item.setTranstatus("1");
                sysManageOilTypeDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @Autowired
    private SysManagePaTRelationDao sysManagePaTRelationDao;
    @Override
    public boolean synpatrel() {

        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.sys.synpatrel");
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
    @Override
    public boolean synprobebar() {

        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.sys.synprobebar");
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
    private SysManageCanInfoDao sysManageCanInfoDao;
    @Override
    public boolean syntankinfo() {

        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.sys.syntankinfo");
        //获取站级数据
        List<SysManageCanInfo> sysManageAlarmParameters= sysManageCanInfoDao.selectByTrans("0");
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,sysManageAlarmParameters,hm);
            for (SysManageCanInfo item:sysManageAlarmParameters){
                item.setTranstatus("1");
                sysManageCanInfoDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Autowired
    private com.kld.gsm.ATG.dao.SysManageTimeSaleOutDao sysManageTimeSaleOutDao;
    @Override
    public boolean syntimesaleout() {

        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.sys.syntimesaleout");
        //获取站级数据
        List<SysManageTimeSaleOut> sysManageAlarmParameters= sysManageTimeSaleOutDao.selectByTrans("0");
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,sysManageAlarmParameters,hm);
            for (SysManageTimeSaleOut item:sysManageAlarmParameters){
                item.setTranStatus("1");
                sysManageTimeSaleOutDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @Autowired
    private com.kld.gsm.ATG.dao.SysManageDataUploadListDao sysManageDataUploadListDao;
    @Override
    public boolean synuplist() {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.sys.synuplist");
        //获取站级数据
        List<SysManageDataUploadList> sysManageAlarmParameters= sysManageDataUploadListDao.selectByTrans("0");
        if (sysManageAlarmParameters==null||sysManageAlarmParameters.size()==0){
            return false;
        }
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,sysManageAlarmParameters,hm);
            for (SysManageDataUploadList item:sysManageAlarmParameters){
                item.setTranstatus("1");
                sysManageDataUploadListDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<SysManageCanInfo> getCaninfos() {
        return  sysManageCanInfoDao.selectAll();
    }
}
