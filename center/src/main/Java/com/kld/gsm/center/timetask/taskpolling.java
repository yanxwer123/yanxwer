package com.kld.gsm.center.timetask;


import com.kld.gsm.center.domain.oss_sysmanage_DataUploadList;
import com.kld.gsm.center.service.SystemManage;
import com.kld.gsm.center.util.action;
import com.kld.gsm.center.util.ftpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.naming.Context;
import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/*
Created BY niyang
Created Date 2015/12/19
*/
public class taskpolling {

    @Autowired
    private  SystemManage systemManage;

    private static final Logger LOG = Logger.getLogger("taskpolling");

    public void upload(){
        try{
            LOG.info("upload begin");
            //region getupdatalist
            List<oss_sysmanage_DataUploadList> uploadList=systemManage.getUplistByStatus();
            //endregion

            //region to hunan

            String host=new action().getFtpaddr();
            String username=new action().getFtpUsername();
            String password=new action().getFtpPwd();
            String localpath=new action().getFtpLocalpath();
            String remotepre=new action().getFtpDir();
            try {

                ftpClient ftclient=new ftpClient();
                ftclient.setHost(host);
                ftclient.setPort(21);
                ftclient.setUsername(username);
                ftclient.setPassword(password);
                ftclient.setBinaryTransfer(true);
                ftclient.setPassiveMode(false);
                ftclient.setEncoding("utf-8");
                LOG.info("Ftp link in");
                for (oss_sysmanage_DataUploadList item:uploadList){
                    LOG.info(item);
                    String filename=item.getNodeno()+"_"+item.getFilename().substring(item.getFilename().length()-12);
                    String localfile=localpath+filename;
                    File file=new File(localfile);
                    if (file.exists()){
                        LOG.info(item + "found");
                        try {
                            //region ftpclient
                            String remotepath=remotepre+filename;
                            LOG.info("localpath:" + localpath);
                            LOG.info("remote:"+remotepath);
                            LOG.info(ftclient.listNames(false));
                            boolean ret = ftclient.put(remotepath, localfile,false);
                            LOG.info("upload result:"+ret);
                            LOG.info("from"+localfile+"to"+remotepath);
                            if (!ret){
                                return;
                            }
                            //region after upload
                            item.setTranstatus("1");
                            //endregion
                            //region update
                            systemManage.UpdateUpload(item);
                            //endregion
                            //region delete local file
                            file.delete();

                            //endregion
                        }catch (Exception e){
                            LOG.error(e.getMessage());
                        }
                        //endregion

                    }else{
                        LOG.error(item+" is not exist.");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            //endregion

            //region updateflag

            //endregion


        }catch (Exception e){
            LOG.error(e.getMessage());
        }
    }
}
