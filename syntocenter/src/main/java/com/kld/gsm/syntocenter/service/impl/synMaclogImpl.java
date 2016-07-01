package com.kld.gsm.syntocenter.service.impl;

import com.kld.gsm.ATG.dao.SysManageDataUploadListDao;
import com.kld.gsm.ATG.dao.SysManageDepartmentDao;
import com.kld.gsm.ATG.domain.SysManageDataUploadList;
import com.kld.gsm.ATG.domain.SysManageDepartment;
import com.kld.gsm.MacLog.GunStatusEnum;
import com.kld.gsm.MacLog.MacLogInfo;
import com.kld.gsm.Socket.Constants;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.syntocenter.domain.CanInfo;
import com.kld.gsm.syntocenter.domain.GunInfo;
import com.kld.gsm.syntocenter.server.ApplicationMain;
import com.kld.gsm.syntocenter.service.synMaclog;
import com.kld.gsm.syntocenter.socket.ob.Watcher;
import com.kld.gsm.syntocenter.util.*;
import com.kld.gsm.util.DateUtil;
import com.kld.gsm.util.JsonMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*
Created BY niyang
Created Date 2015/12/8
*/
@SuppressWarnings("All")
@Service
public class synMaclogImpl implements synMaclog, Watcher {
    private static final Logger LOG = Logger.getLogger("synMaclogImpl");
    /**
     * ftp map：host,username,password
     */
    @Override
    public boolean synMaclog(String remotepath,String localpath,Map ftp) {
        if (ftp.keySet().isEmpty()){
            //System.out.println("ftp config is null.");
        }
        ftpClient ftclient = new ftpClient();
        ftclient.setHost(ftp.get("host").toString());
        ftclient.setPort(21);
        ftclient.setUsername(ftp.get("username").toString());
        ftclient.setPassword(ftp.get("password").toString());
        ftclient.setBinaryTransfer(false);
        ftclient.setPassiveMode(false);
        ftclient.setEncoding("utf-8");
        //System.out.println("Ftp link in");
        try {
            boolean ret = ftclient.put(remotepath, localpath);
            //System.out.println("from"+localpath+"to"+remotepath);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean synTankandGunRTStatus() {
        GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(ApplicationMain.sign, new ArrayList(), Constants.PID_Code.A15_10002.toString());
        ApplicationMain.CC.writeAndFlush(gasMsg);
        gasMsg = ResultUtils.getInstance().sendSUCCESS(ApplicationMain.sign, new ArrayList(), Constants.PID_Code.A15_10004.toString());
        ApplicationMain.CC.writeAndFlush(gasMsg);
        return false;
    }

    @Resource
    private SysManageDepartmentDao sysManageDepartmentDao;

    @Override
    public void update(GasMsg gasMsg) {

        //region getnodeno

        String nodeno="";
        List<SysManageDepartment> sysManageDepartments=sysManageDepartmentDao.selectfirst();
        if (!sysManageDepartments.isEmpty()){
            nodeno= sysManageDepartments.get(0).getSinopecnodeno();
        }else{
            //System.out.println("Can't get nodeno");
            return;
        }
        //endregion

        if (gasMsg.getPid().equals("A15_10002")) {
            //油枪状态
            ResultMsg resultMsg = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
            List<MacLogInfo> macLogInfos = resultMsg.getData();
            List<GunInfo> gunInfos = new ArrayList<GunInfo>();
            for (int i = 0; i < macLogInfos.size(); i++) {
                // region获取罐号
                GunInfo gunInfo = new GunInfo();
                Map<String, ?> map = (Map) (macLogInfos.get(i));
                //枪map
                gunInfo.setOilGun(Integer.parseInt(map.get("GunNum").toString()));
                gunInfo.setOilGunStatus(((GunStatusEnum) map.get("GunStatus")).value());
                gunInfo.setGetTime(new Date());
                gunInfo.setPumpUp(map.get("PumpReadout").toString());
                gunInfo.setNodeno(nodeno);
                gunInfos.add(gunInfo);
            }
            //获取action地址
            action ac = new action();
            String path = ac.getUri("resource.services.TI.addQSSZT");

            Map<String, String> hm = new param().getparam();
            //发送站级数据
            httpClient client = new httpClient();
            try {
                client.request(path, gunInfos, hm);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (gasMsg.getPid().equals("A15_10004")) {
            //实时库存
            List<CanInfo> canInfos = new ArrayList<CanInfo>();
            ResultMsg resultMsg = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
            List<Map<String, ?>> candata = resultMsg.getData();
            for (int m = 0; m < candata.size(); m++) {
                CanInfo canInfo = new CanInfo();
                Map<String, ?> map = candata.get(m);
                canInfo.setNodeno(nodeno);
                canInfo.setOilcan(Integer.getInteger(map.get("uOilCanNo").toString()));
                canInfo.setOill(Double.parseDouble(map.get("fOilCubage").toString()));
                canInfo.setStandardl(Double.parseDouble(map.get("fOilStandCubage").toString()));
                canInfo.setStoretime(new Date());
                canInfo.setTemperature(Double.parseDouble(map.get("fOilTemp").toString()));
                canInfo.setTemp1(Double.parseDouble(map.get("fOilTemp1").toString()));
                canInfo.setTemp2(Double.parseDouble(map.get("fOilTemp2").toString()));
                canInfo.setTemp3(Double.parseDouble(map.get("fOilTemp3").toString()));
                canInfo.setTemp4(Double.parseDouble(map.get("fOilTemp4").toString()));
                canInfo.setTemp5(Double.parseDouble(map.get("fOilTemp5").toString()));
                canInfo.setDensitiesstandard(Double.parseDouble(map.get("fStandDensity").toString()));
                canInfo.setDensities(Double.parseDouble(map.get("fApparentDensity").toString()));
                canInfo.setHeighttotal(Double.parseDouble(map.get("fTotalHeight").toString()));
                canInfo.setHeightwater(Double.parseDouble(map.get("fWaterHeight").toString()));
                canInfo.setWaterl(Double.parseDouble(map.get("fWaterBulk").toString()));
                if (!ApplicationMain.oilcanmap.isEmpty() && ApplicationMain.oilcanmap.get(map.get("uOilCanNo").toString()) != null) {
                    canInfo.setOilno(ApplicationMain.oilcanmap.get(map.get("uOilCanNo").toString()));
                }
                canInfo.setVolumeempty(Double.parseDouble(map.get("fEmptyCubage").toString()));
                if (!ApplicationMain.canversion.isEmpty() && ApplicationMain.canversion.get(map.get("uOilCanNo").toString()) != null) {
                    canInfo.setVersion(ApplicationMain.canversion.get(map.get("uOilCanNo").toString()));
                }
                canInfos.add(canInfo);
            }

            //获取action地址
            action ac = new action();
            String path = ac.getUri("resource.services.TI.addGSSZT");

            Map<String, String> hm = new param().getparam();
            //发送站级数据
            httpClient client = new httpClient();
            try {
                client.request(path, canInfos, hm);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Resource
    private SysManageDataUploadListDao sysManageDataUploadListDao;

    @Override
    public boolean synMacLogAuto() {

        ArrayList<String> pathlist = new ArrayList<String>();
        String localpath = new action().getFtpLocalpath();
        String remotepre = new action().getFtpRemotepath();
        String fileper = "";
        String host = new action().getFtpaddr();
        String username = new action().getFtpusername();
        String password = new action().getFtppassword();
        try {

            Date date=new Date();
            Date datenew= DateUtil.convertStringToDate(DateUtil.addDay(DateUtil.getDate(date, "yyyy MM dd"), -4));
            LOG.info(datenew.toString());
            for (int i=0;i<3;i++){
                datenew= DateUtil.convertStringToDate(DateUtil.addDay(DateUtil.getDate(datenew, "yyyy MM dd"), 1));
                String path=localpath+fileper+DateUtil.getDate(datenew,"yyyyMMdd")+".log";
                LOG.info("add " + path);
                pathlist.add(path);
            }
            ftpClient ftclient = new ftpClient();
            ftclient.setHost(host);
            ftclient.setPort(21);
            ftclient.setUsername(username);
            ftclient.setPassword(password);
            ftclient.setBinaryTransfer(true);
            ftclient.setPassiveMode(false);
            ftclient.setEncoding("utf-8");

            LOG.info("Ftp link in");
            LOG.info(ftclient.listNames(false).toString());
            for (String item:pathlist){
                LOG.info(item);
                File file=new File(item);
                if (file.exists()){
                    LOG.info(item+"found");
                    //region check status
                    List<SysManageDataUploadList> duplist = sysManageDataUploadListDao.selectByfilename(item);
                    if (duplist != null && duplist.size() > 0) {
                        continue;
                    }
                    //endregion
                    try {
                         boolean zipswitch=new action().getZipSwitch();

                        //region ftpclient
                        String remotepath=remotepre+ApplicationMain.NodeNo+"_"+item.substring(item.length()-12,item.length());
                        String zipname=item.replace(".log",".zip");
                        LOG.info("localpath:"+localpath);
                        LOG.info("remote:"+remotepath);
                        LOG.info(ftclient.listNames(false));
                        if (zipswitch){
                            ZipUtils.createZip(item,zipname);
                        }
                        boolean ret=false;
                        if (!zipswitch){
                            ret= ftclient.put(remotepath, item,false);
                            LOG.info("upload result:"+ret);
                            LOG.info("from:"+item+",to:"+remotepath);
                        }else{
                            remotepath=remotepath.replace(".log",".zip");
                            ret=ftclient.put(remotepath, zipname,false);
                            LOG.info("upload result:"+ret);
                            LOG.info("from:"+zipname+",to:"+remotepath);
                        }
                       //region after upload
                        SysManageDataUploadList dataUploadList=new SysManageDataUploadList();
                        dataUploadList.setTranstatus("0");
                        dataUploadList.setCreatetime(new Date());
                        dataUploadList.setCreator("tocenter");
                        dataUploadList.setFilename(item);
                        if(zipswitch){
                            dataUploadList.setFilename(zipname);
                        }
                        dataUploadList.setResult(1);
                        sysManageDataUploadListDao.insert(dataUploadList);
                        //endregion
                        //region delete local file
                        File lf=new File(item);
                        if (lf.exists()) {
                            lf.delete();
                        }
                        if(zipswitch){
                            File ziplf=new File(zipname);
                            if (ziplf.exists()) {
                                ziplf.delete();
                            }
                        }
                    //endregion
                    }catch (Exception e){
                        LOG.error(e.getMessage());
                    }
                    //endregion
                } else {
                    LOG.info(item+" is not exist.");
                }
            }
            ftclient.disconnect();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
