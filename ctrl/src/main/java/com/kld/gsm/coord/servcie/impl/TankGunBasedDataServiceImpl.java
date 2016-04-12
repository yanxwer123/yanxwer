package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.dao.*;
import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.ATG.service.SysManageDic;
import com.kld.gsm.ATG.service.SysmanageService;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.coord.Constants;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.dao.*;
import com.kld.gsm.coord.domain.*;
import com.kld.gsm.coord.servcie.TankGunBasedDataService;
import com.kld.gsm.coord.server.handler.ConnectionSession;
import com.kld.gsm.coord.server.handler.ProtocolProcessor;
import com.kld.gsm.util.JsonMapper;
import com.kld.gsm.util.SybaseUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2015.11.8
 * @Description:罐枪基础数据同步实现
 */
@SuppressWarnings("all")
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class TankGunBasedDataServiceImpl implements TankGunBasedDataService {
    Logger LOGGER = Logger.getLogger(this.getClass());
    @Autowired
    private OilCanInforDao oilCanInforDao;
    @Autowired
    private OilMachineInforDao oilMachineInforDao;
    @Autowired
    private OilGunInforDao oilguninforDao;
    @Autowired
    private OilTypeDao oilTypeDao;
    @Autowired
    private NodeInforDao nodeInforDao;
    @Autowired
    private SysManageCanInfoDao sysManageCanInfoDao;
    @Autowired
    private SysManageOilMachineInfoDao sysManageOilMachineInfoDao;
    @Autowired
    private SysManageOilGunInfoDao sysManageOilGunInfoDao;
    @Autowired
    private SysManageOilTypeDao sysManageOilTypeDao;
    @Autowired
    private SysManageDepartmentDao sysManageDepartmentDao;
    @Autowired
    private  SysManageAlarmParameterDao sysManageAlarmParameterDao;
    @Autowired
    private SysmanageService sysmanageService;

    @Override
    public Integer saveTankGunBasedData(String id) {

        try {
            updateTankGunBasedData(id);
        }catch (Exception ex){
           //error(id);
            return 1;
        }
        return 0;
    }

    @Transactional(propagation =Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateTankGunBasedData(String id) throws Exception{
        //油罐信息表
        //System.out.println("进入油罐信息表");
        String sql = "SELECT oilcanno,oilcantype, outfactoryno, factoryname, outfacorytime,installdate,useyears,oilno,cubage,oilcanstatus,transflag,lederliter,canfactliter FROM oilcaninfor";
        LOGGER.info("oilCanInforDao.selectOilCanInfor1=="+sql);
        List<OilCanInfor> OilCanInforList =  oilCanInforDao.selectOilCanInfor1(sql);
        LOGGER.info("OilCanInforList.size:" + OilCanInforList.size());
        SysManageCanInfo SysManageCanInfo =new SysManageCanInfo();
        //更新mysql库 油罐信息表(先删除再插入)
        //System.out.println("执行删除");
        int deleteSysManageCanInfo= sysManageCanInfoDao.deleteAll();
         for (OilCanInfor oilCanInfor :OilCanInforList){
                SysManageCanInfo.setOilcan(oilCanInfor.getOilcanno());
                SysManageCanInfo.setOilcantype(oilCanInfor.getOilcantype());
                SysManageCanInfo.setOutfactoryno(oilCanInfor.getOutfactoryno());
                SysManageCanInfo.setFactoryname(SybaseUtils.getSybaseCNStr(oilCanInfor.getFactoryname()));
                SysManageCanInfo.setOutfacorytime(oilCanInfor.getOutfacorytime());
                SysManageCanInfo.setInstalldate(oilCanInfor.getInstalldate());
                SysManageCanInfo.setUseyears(oilCanInfor.getUseyears());
                SysManageCanInfo.setOilno(oilCanInfor.getOilno());
                SysManageCanInfo.setCubage(oilCanInfor.getCubage());
                SysManageCanInfo.setOilcanstatus(oilCanInfor.getOilcanstatus());
                SysManageCanInfo.setLederl(oilCanInfor.getLederliter());
                SysManageCanInfo.setCanreall(oilCanInfor.getCanfactliter());
                SysManageCanInfo.setTranstatus("0");//首次插入设置为未传输到省中心
             //System.out.println("开始插入油罐信息表");

                int insertSysManageCanInfor = sysManageCanInfoDao.insert(SysManageCanInfo);
            }
        //油机信息表
        //System.out.println("进入油机信息表");
        sql = "SELECT oilmachineno,  oilmachinemodel, outfactoryno,  factoryname,  outfacorytime, installdate,  useyears,  oilgunnum, oilunitnum, oilmachinestatus,transflag,machinetype,\n FROM oilmachineinfor";
        LOGGER.info("oilMachineInforDao.selectOilMachineInfor1=="+sql);
        List<OilMachineInfor> oilMachineInforList = oilMachineInforDao.selectOilMachineInfor1(sql);
        LOGGER.info("oilMachineInforList.size:" + oilMachineInforList.size());
        SysManageOilMachineInfo sysManageOilMachineInfo = new SysManageOilMachineInfo();
        //更新mysql库 油机信息表(先删除再插入)
        //System.out.println("开始删除油机信息表");
        int deleteSysManageOilMachineInfo=sysManageOilMachineInfoDao.deleteAll();
            for (OilMachineInfor oilMachineInfor : oilMachineInforList) {
                sysManageOilMachineInfo.setMacno(oilMachineInfor.getOilmachineno());
                sysManageOilMachineInfo.setMacmodel(oilMachineInfor.getOilmachinemodel());
                sysManageOilMachineInfo.setOutfactoryno(oilMachineInfor.getOutfactoryno());
                sysManageOilMachineInfo.setOutfactorytime(oilMachineInfor.getOutfacorytime());
                sysManageOilMachineInfo.setFactoryname(SybaseUtils.getSybaseCNStr(oilMachineInfor.getFactoryname()));
                sysManageOilMachineInfo.setInstalldate(oilMachineInfor.getInstalldate());
                sysManageOilMachineInfo.setUseyears(oilMachineInfor.getUseyears());
                sysManageOilMachineInfo.setOilgunnum(oilMachineInfor.getOilgunnum());
                sysManageOilMachineInfo.setOilunitnum(oilMachineInfor.getOilunitnum());
                sysManageOilMachineInfo.setOilmachinestatus(oilMachineInfor.getOilmachinestatus());
                sysManageOilMachineInfo.setMactype(oilMachineInfor.getMachinetype());
                sysManageOilMachineInfo.setTranstatus("0");
                Integer macno = sysManageOilMachineInfo.getMacno();
                //System.out.println("开始插入油机信息表");
                int insertSysManageOilMachineInfo=sysManageOilMachineInfoDao.insert(sysManageOilMachineInfo);
            }


        //油枪信息表
        //System.out.println("开始油枪信息表");
        sql = "SELECT * FROM oilguninfor";
        LOGGER.info("oilguninforDao.selectOilGunInfor1=="+sql);
        List<OilGunInfor> oilGunInforList = oilguninforDao.selectOilGunInfor1(sql);
        LOGGER.info("oilGunInforList.size:" + oilGunInforList.size());
        SysManageOilGunInfo sysManageOilGunInfo =new SysManageOilGunInfo();
        //更新mysql库 库油枪信息表(先删除再插入)
        //System.out.println("开始删除油枪信息表");
        int deleteSysManageOilGunInfo=sysManageOilGunInfoDao.deleteAll();
            for (OilGunInfor oilGunInfor :oilGunInforList){
                sysManageOilGunInfo.setOilcan(oilGunInfor.getOilcanno());
                sysManageOilGunInfo.setMacno(oilGunInfor.getMachineno());
                sysManageOilGunInfo.setOilgun(oilGunInfor.getOilgunno());
                sysManageOilGunInfo.setOilgunname(SybaseUtils.getSybaseCNStr(oilGunInfor.getOilgunname()));
                sysManageOilGunInfo.setCtrlunitnum(oilGunInfor.getCtrlunitnum());
                sysManageOilGunInfo.setLinkadr(oilGunInfor.getLinkadr());
                sysManageOilGunInfo.setGunstatus(oilGunInfor.getGunstatus());
                sysManageOilGunInfo.setInitpump(oilGunInfor.getInitpump());
                sysManageOilGunInfo.setTranstatus("0");
                //System.out.println("开始插入油枪信息表");
                int insertSysManageOilGunInfo =sysManageOilGunInfoDao.insert(sysManageOilGunInfo);
            }


        //机走油品编码表
        //System.out.println("开始机走油品编码表");
        sql = "SELECT * FROM oiltype";
        LOGGER.info("oilTypeDao.selectOilType1=="+sql);
        List<OilType> oilTypeList = oilTypeDao.selectOilType1(sql);
        LOGGER.info("oilTypeList.size:" + oilTypeList.size());
        //更新mysql库 机走油品编码(先删除再插入)
        //System.out.println("开始删除机走油品编码表"+new Date());
        int deleteSysManageOilType=sysManageOilTypeDao.deleteAll();
        //System.out.println("开始插入走油品编码表：："+new Date());
        List<SysManageOilType>  sysmanageoiltypeList=new ArrayList<SysManageOilType>();
        for (OilType oilType :oilTypeList){
            SysManageOilType sysManageOilType =new SysManageOilType();
            sysManageOilType.setOilno(oilType.getOilno());
                sysManageOilType.setOilname(SybaseUtils.getSybaseCNStr(oilType.getOilname()));
                sysManageOilType.setMacoilno(oilType.getMachineoilno());
                sysManageOilType.setOiltype(oilType.getOilattribute());
                sysManageOilType.setControl(oilType.getControl());
                sysManageOilType.setValidflag(oilType.getValidflag());
                sysManageOilType.setValidtime(oilType.getValidtime());
                sysManageOilType.setModifytime(oilType.getModifytime());
                sysManageOilType.setModifynodeno(oilType.getModifynodeno());
                sysManageOilType.setModifyope(oilType.getModifyope());
                sysManageOilType.setLevelcode(oilType.getLevelcode());
                sysManageOilType.setLastoilflag(oilType.getLastoilflag());
                sysManageOilType.setOillevel(oilType.getOillevel());
                sysManageOilType.setInuseflag(oilType.getInuseflag());
                sysManageOilType.setTranstatus("0");
                sysmanageoiltypeList.add(sysManageOilType);
        }
            sysManageOilTypeDao.insertList(sysmanageoiltypeList);
        //System.out.println("油品插入完成："+new Date());


       //单位信息表
        //System.out.println("开始单位信息表");
        String nodeno=null;
        sql = "SELECT * FROM NodeInfor";
        LOGGER.info("nodeInforDao.selectNodeInfor1=="+sql);
        List<NodeInfor> NodeInforList=nodeInforDao.selectNodeInfor1(sql);
        LOGGER.info("NodeInforList.size:" + NodeInforList.size());
        SysManageDepartment sysManageDepartment=new SysManageDepartment();
        //更新mysql库  单位信息表(先删除，再插入)
        //System.out.println("开始删除单位信息表");
        int deleteSysManageDepartment=sysManageDepartmentDao.deleteAll();
        for (NodeInfor nodeInfor :NodeInforList){
            sysManageDepartment.setPhysicalnodeno(nodeInfor.getPhysicalnodeno());
            sysManageDepartment.setNodeno(nodeInfor.getNodeno());
            sysManageDepartment.setSinopecnodeno(nodeInfor.getSinopecnodeno());
            sysManageDepartment.setNodename((null == nodeInfor.getNodename() || "".equals(nodeInfor.getNodename())) ? "" : SybaseUtils.getSybaseCNStr(nodeInfor.getNodename()));
            sysManageDepartment.setNodetag((null == nodeInfor.getNodetag() || "".equals(nodeInfor.getNodetag())) ? "" : SybaseUtils.getSybaseCNStr(nodeInfor.getNodetag()));
            sysManageDepartment.setAreano(nodeInfor.getAreano());
            sysManageDepartment.setGroupno(nodeInfor.getGroupno());
            sysManageDepartment.setNodenoowner(nodeInfor.getNodenoowner());
            sysManageDepartment.setEnterpriseno(nodeInfor.getEnterpriseno());
            sysManageDepartment.setNodeattribute(nodeInfor.getNodeattribute());
            sysManageDepartment.setNodespecific(nodeInfor.getNodespecific());
            sysManageDepartment.setRegtype(nodeInfor.getRegtype());
            sysManageDepartment.setOrgcode(nodeInfor.getOrgcode());
            sysManageDepartment.setNodeaddrs((null == nodeInfor.getNodeaddrs() || "".equals(nodeInfor.getNodeaddrs())) ? "" : SybaseUtils.getSybaseCNStr(nodeInfor.getNodeaddrs()));
            sysManageDepartment.setPostno(nodeInfor.getPostno());
            sysManageDepartment.setManager((null == nodeInfor.getManager() || "".equals(nodeInfor.getManager())) ? "" : SybaseUtils.getSybaseCNStr(nodeInfor.getManager()));
            sysManageDepartment.setTelphno(nodeInfor.getTelphno());
            sysManageDepartment.setRemark((null == nodeInfor.getRemark() || "".equals(nodeInfor.getRemark())) ? "" :SybaseUtils.getSybaseCNStr(nodeInfor.getRemark()));//备注
            sysManageDepartment.setFoxno(nodeInfor.getFaxno());
            sysManageDepartment.setEmail(nodeInfor.getEmail());
            sysManageDepartment.setWorktime(nodeInfor.getWorktime());
            sysManageDepartment.setAccountowner(nodeInfor.getAccountowner());
            sysManageDepartment.setNodetree(nodeInfor.getNodetree());
            sysManageDepartment.setProvinceno(nodeInfor.getProvinceno());
            sysManageDepartment.setNodetype(nodeInfor.getNodetype());
            sysManageDepartment.setUsefulstatus(nodeInfor.getUsefulstatus());
            sysManageDepartment.setLastnodeno(nodeInfor.getLastnodeno());
            sysManageDepartment.setModifytime(nodeInfor.getModifytime());
            sysManageDepartment.setTaxpayercode(nodeInfor.getTaxpayercode());
            sysManageDepartment.setCorporationname((null == nodeInfor.getCorporationname() || "".equals(nodeInfor.getCorporationname())) ? "" :SybaseUtils.getSybaseCNStr(nodeInfor.getCorporationname()));
            sysManageDepartment.setExpand1((null == nodeInfor.getExpand1() || "".equals(nodeInfor.getExpand1())) ? "" :SybaseUtils.getSybaseCNStr(nodeInfor.getExpand1()));
            sysManageDepartment.setExpand2((null == nodeInfor.getExpand2() || "".equals(nodeInfor.getExpand2())) ? "" :SybaseUtils.getSybaseCNStr(nodeInfor.getExpand2()));
            sysManageDepartment.setAreadesc((null == nodeInfor.getArea_desc() || "".equals(nodeInfor.getArea_desc())) ? "" :SybaseUtils.getSybaseCNStr(nodeInfor.getArea_desc()));
            sysManageDepartment.setNodenoownerdesc((null == nodeInfor.getNodeno_ownerdesc() || "".equals(nodeInfor.getNodeno_ownerdesc())) ? "" :SybaseUtils.getSybaseCNStr(nodeInfor.getNodeno_ownerdesc()));
            sysManageDepartment.setProvincedesc((null == nodeInfor.getProvince_desc() || "".equals(nodeInfor.getProvince_desc())) ? "" :SybaseUtils.getSybaseCNStr(nodeInfor.getProvince_desc()));
            sysManageDepartment.setTranstatus("0");
            //System.out.println("开始插入单位信息表");
            nodeno=sysManageDepartment.getSinopecnodeno();
            int insertSysManageDepartment=sysManageDepartmentDao.insert(sysManageDepartment);
            //System.out.println("…………………………………………………………………………结束");
            }

        //预报警设置表

        //region 方法一，纯Java方法
        List<SysManageAlarmParameter>  sysManageAlarmParameterList=sysManageAlarmParameterDao.selectAll();
        //System.out.println("更新和删除逻辑kaishi");
        //region 排重
        List delList=new ArrayList();
        //删除Sybase表里没有的
        for(SysManageAlarmParameter sysManageAlarmParameter:sysManageAlarmParameterList){
            boolean bHave=false;
            for (OilCanInfor oilCanInfor : OilCanInforList){
                if(sysManageAlarmParameter.getOilcan().equals(oilCanInfor.getOilcanno()))
                {
                    bHave=true;
                    break;
                }
            }
            if(!bHave) {
                delList.add(sysManageAlarmParameter.getOilcan());
            }
        }
        List addList=new ArrayList();
        for (OilCanInfor oilCanInfor : OilCanInforList){
            boolean bHave=false;
            for(SysManageAlarmParameter sysManageAlarmParameter:sysManageAlarmParameterList){
                if(sysManageAlarmParameter.getOilcan().equals(oilCanInfor.getOilcanno()))
                {
                    bHave=true;
                    break;
                }
            }
            if(!bHave) {
                addList.add(oilCanInfor.getOilcanno());
            }
        }
        //增加Sybase表里多的
        //end region
        /*List list=new ArrayList();//保存预报警设置表中的油罐编号
        List list2=new ArrayList();//保存得到的油罐编号
        for(SysManageAlarmParameter sysManageAlarmParameter:sysManageAlarmParameterList){
            list.add(sysManageAlarmParameter.getOilcan());
        }
        for (OilCanInfor oilCanInfor : OilCanInforList){
            list2.add(oilCanInfor.getOilcanno());
        }
        List list3=list;
        list3.remove(list2);//仅保留预报警设置表中多余 的油罐编号
        list2.remove(list);//仅保留新得到预警设置中没有的油罐编号*/
        if(delList.size()>0){
            Iterator<Integer> it = delList.iterator();   //创建迭代器
            while (it.hasNext()) {       //循环遍历迭代器
                //执行删除操作
                //System.out.println("删除逻辑kaishi");
                int deletSysManageAlarmParameter=sysManageAlarmParameterDao.deleteByPrimaryKey(it.next());
                //System.out.println("删除逻辑jieshu");
            }
        }
        if(addList.size()>0){
            Iterator<Integer> it = addList.iterator();   //创建迭代器
            while (it.hasNext()) {       //循环遍历迭代器
                //执行插入操作
                //System.out.println("插入逻辑kaishi");

                int insertSysManageAlarmParameter=sysManageAlarmParameterDao.insertByKey(it.next());

                //System.out.println("插入逻辑结束");
            }
        }
        //endregion


        //调用方法，更新从省中心拿来的数据
        /*SysManageDic dic= Context.getInstance().getBean(SysManageDic.class);
        dic.GetByCode("zxfwqdz");
        sysmanageService.GetAlarmPar(dic.GetByCode("zxfwqdz").getValue(),nodeno);*/

        //region 方法二 结合数据库查询结果
       /* //System.out.println("更新和删除逻辑kaishi");
        List<SysManageAlarmParameter> sysManageAlarmParameterList=sysManageAlarmParameterDao.selectAlarmParameter();

        //System.out.println("更新和删除逻辑2");

        if(sysManageAlarmParameterList.size()>0){
            for(SysManageAlarmParameter sysManageAlarmParameter:sysManageAlarmParameterList){
                //执行删除操作
                //System.out.println("删除逻辑2");

                int deletSysManageAlarmParameter=sysManageAlarmParameterDao.deleteByPrimaryKey(sysManageAlarmParameter.getOilcan());
            }
        }
        //System.out.println("插入逻辑存到。。。。");
        List<SysManageCanInfo> SysManageCanInfoList=sysManageCanInfoDao.selectSysmangeTank();
        if(SysManageCanInfoList.size()>0){
            for(SysManageCanInfo SysManageCanInfo1:SysManageCanInfoList){
                SysManageAlarmParameter sysManageAlarmParameter=new SysManageAlarmParameter();
                sysManageAlarmParameter.setOilcan(SysManageCanInfo1.getOilcan());
                //执行插入操作
                //System.out.println("插入逻辑2");
                int insertSysManageAlarmParameter=sysManageAlarmParameterDao.insert(sysManageAlarmParameter);
            }
        }*/
        //endregion

    }
    /*private void error(String id) {
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setId(id);
        resultMsg.setResult("1");
        List list = new ArrayList();
        Map map = new LinkedHashMap();
        map.put("error", "Mysql t Exception");
        list.add(map);
        resultMsg.setData(list);

        GasMsg gasMsg = new GasMsg();
        gasMsg.setPid(Constants.PID_Code.A15_10011.toString());
        gasMsg.setMessage(new JsonMapper().toJson(resultMsg));

        ConnectionSession connectionSession = ProtocolProcessor.getInstance().searchDitCtxByGasStationNo(id, ProtocolProcessor.getInstance().appMapper);
        if(!connectionSession.equals("")&&connectionSession!=null){
            connectionSession.getCtx().writeAndFlush(gasMsg);
        }
        System.err.println("无应用程序，且操作失败。。。。。。id:[" + id + "]  com.kld.gsm.syntocenter.socket[" + connectionSession.getCtx()+"]");

    }*/

}

