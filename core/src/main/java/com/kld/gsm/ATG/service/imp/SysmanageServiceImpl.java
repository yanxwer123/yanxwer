package com.kld.gsm.ATG.service.imp;

import com.fasterxml.jackson.databind.JavaType;
import com.kld.gsm.ATG.dao.*;
import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.ATG.service.SysmanageService;
import com.kld.gsm.ATG.utils.action;
import com.kld.gsm.ATG.utils.httpClient;
import com.kld.gsm.ATG.utils.param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.kld.gsm.util.*;

/*
Created BY niyang
Created Date 2015/11/22
*/
@Service
@SuppressWarnings("all")
public class SysmanageServiceImpl implements SysmanageService {

    @Resource
    private SysManageCubageDao cubageDao;
    @Resource
    private SysManageCubageInfoDao cubageInfoDao;

    @Resource
    private SysManageAlarmParameterDao alarmParameterDao;

    @Resource
    private SysManageDepartmentDao sysManageDepartmentDao;

    @Autowired
    private SysManageCanInfoDao sysManageCanInfoDao;
    /*
    * 站级系统接口 实现 更新容积表主表 明细表
    * */
    @Transactional(rollbackFor = Exception.class)
    public boolean GetCubgeByNodeNo(String host,String nodeno) {
        //获取action地址
        action ac=new action();
        String path=ac.getUri(host, "resource.services.sys.getcubebynodeno");

        Map<String,String> hm=new param().getparam();
        hm.put("NodeNo",nodeno);
        //发送请求数据
        httpClient client=new httpClient();
        try {
            String js= client.request(path,null,hm);
            JsonMapper jm=new JsonMapper();
            JavaType jt=jm.createCollectionType(List.class,SysmanageCubageMain.class);
            List<SysmanageCubageMain> sysmanageCubageMains=jm.fromJson(js,jt);
            if (sysmanageCubageMains.isEmpty()) return true;

            //delete cubage and cubageinfo
           /* cubageDao.deleteAll();
            cubageInfoDao.deleteAll();*/
            for (SysmanageCubageMain item:sysmanageCubageMains){
                //get cubage
                SysManageCubageKey key=new SysManageCubageKey();
                key.setOilcan(item.getCubage().getOilcan());
                key.setVersion(item.getCubage().getVersion());
                SysManageCubage sysManageCubage=cubageDao.selectByPrimaryKey(key);
                if (sysManageCubage!=null)continue;
                //insert cubage
                item.getCubage().setSettime(new Date());
                item.getCubage().setInused(0);
                item.getCubage().setSetstate(0);
                cubageDao.insert(item.getCubage());
                // insert cubageinfo
                for (SysManageCubageInfo info:item.getCubageInfos()){
                    cubageInfoDao.insert(info);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
    * 站级系统 获取报警参数
    * */
    @Transactional(rollbackFor = Exception.class)
    public boolean GetAlarmPar(String host, String nodeno) {
        //获取action地址
        action ac=new action();
        String path=ac.getUri(host, "resource.services.sys.getalarmpar");

        Map<String,String> hm=new param().getparam();
        hm.put("NodeNo",nodeno);
        //发送请求数据
        httpClient client=new httpClient();
        try {
            String js= client.request(path,null,hm);
            JsonMapper jm=new JsonMapper();
            JavaType jt=jm.createCollectionType(List.class,SysManageAlarmParameter.class);
            List<SysManageAlarmParameter> alarms=jm.fromJson(js,jt);

            if (alarms.isEmpty()) return true;

            for (SysManageAlarmParameter item:alarms){
                alarmParameterDao.merge(item);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /*
    * 获取部门信息
    * */
    @Override
    public SysManageDepartment getdeptinfo() {

        List<SysManageDepartment> sysManageDepartments=sysManageDepartmentDao.selectfirst();
        if (!sysManageDepartments.isEmpty()){
                return sysManageDepartments.get(0);
        }else{
            //System.out.println("sysManageDepartments empty");
            return null;
        }

    }


    /**
     * @description 获取正在使用的容积表
     * */
    @Override
    public List<SysManageCubage> selectCubageInused() {
        return cubageDao.selectCubageInused();
    }


    @Override
    public int GetCubgeByNodeNobackInt(String host, String nodeno) {
        //获取action地址
        action ac=new action();
        String path=ac.getUri(host, "resource.services.sys.getUntranCubgeInfos");

        Map<String,String> hm=new param().getparam();
        hm.put("NodeNo", nodeno);
        List<SysManageCubage> cubageList = cubageDao.getMaxVersion();
        StringBuffer CV = new StringBuffer();
        for(SysManageCubage cubage:cubageList){
            //格式为：罐号|版本号，
            CV.append(cubage.getOilcan()+"|"+cubage.getVersion()+",");
        }
        hm.put("CV",CV.toString());
        //发送请求数据
        httpClient client=new httpClient();
        try {
            String js= client.request(path,null,hm);
            JsonMapper jm=new JsonMapper();
            JavaType jt=jm.createCollectionType(List.class,SysmanageCubageMain.class);
            List<SysmanageCubageMain> sysmanageCubageMains=jm.fromJson(js,jt);
            if (sysmanageCubageMains.isEmpty()) return 0;

            for (SysmanageCubageMain item:sysmanageCubageMains){
                //get cubage
                SysManageCubageKey key=new SysManageCubageKey();
                key.setOilcan(item.getCubage().getOilcan());
                key.setVersion(item.getCubage().getVersion());
                SysManageCubage sysManageCubage=cubageDao.selectByPrimaryKey(key);
                if (sysManageCubage!=null)continue;
                //insert cubage
                item.getCubage().setSettime(new Date());
                item.getCubage().setInused(0);
                item.getCubage().setSetstate(0);
                cubageDao.insert(item.getCubage());
                // insert cubageinfo
                for (SysManageCubageInfo info:item.getCubageInfos()){
                    cubageInfoDao.insert(info);
                }
            }
            return sysmanageCubageMains.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public int GetAlarmParbackInt(String host, String nodeno) {

        //获取action地址
        action ac=new action();
        String path=ac.getUri(host, "resource.services.sys.getalarmpar");

        Map<String,String> hm=new param().getparam();
        hm.put("NodeNo",nodeno);
        //发送请求数据
        httpClient client=new httpClient();
        try {
            String js= client.request(path,null,hm);
            JsonMapper jm=new JsonMapper();
            JavaType jt=jm.createCollectionType(List.class,SysManageAlarmParameter.class);
            List<SysManageAlarmParameter> alarms=jm.fromJson(js,jt);

            if (alarms.isEmpty()) return 0;

            for (SysManageAlarmParameter item:alarms){
                alarmParameterDao.merge(item);
            }
            return alarms.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<SysManageCanInfo> getCaninfos() {
        return  sysManageCanInfoDao.selectAll();
    }
}
