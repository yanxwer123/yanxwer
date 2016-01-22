package com.kld.gsm.syntocenter.server;

import com.kld.gsm.syntocenter.springContext.springFactory;
import com.kld.gsm.ATG.service.*;

import java.util.Date;


/*
Created BY niyang
Created Date 2015/12/11
*/
public class ftpmain {

    public static void main(String[] args) throws Exception {
      /*  //System.out.println(Charset.defaultCharset());

        System.getProperties().put("file.encoding", "GBK");
        System.getProperties().list(System.out);

        //System.out.println(Charset.defaultCharset());*/



        AcceptSevices service=springFactory.getInstance().getBean(AcceptSevices.class);
        service.sendOdreg("http://localhost:8080","32550050");
/*        FTPClientTemplate ftclient=new FTPClientTemplate();
        ftclient.setHost("192.168.1.126");
        ftclient.setPort(21);
        ftclient.setUsername("ny");
        ftclient.setPassword("123456");
        ftclient.setBinaryTransfer(false);
        ftclient.setPassiveMode(false);
        ftclient.setEncoding("utf-8");
        //System.out.println("get ftp");
        try {
            boolean ret = ftclient.put("/123_20160926.log", "D:/gsmtimestock/gsmtimestock20160926.log");
        }catch (Exception e){
            e.printStackTrace();
        }
        //System.out.println("");*/
    }
    }
