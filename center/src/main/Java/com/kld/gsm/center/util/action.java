package com.kld.gsm.center.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.swing.plaf.PanelUI;
import java.io.IOException;
import java.util.Properties;


/*
Created BY niyang
Created Date 2015/11/18
*/
public  class action {
    public  String getUri(String propertyName){
        Resource base=new ClassPathResource("/conf/system.properties");
        Properties probase = null;
        try {
             probase=PropertiesLoaderUtils.loadProperties(base);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Resource resource = new ClassPathResource("/conf/hnservices.properties");
        Properties props = null;
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filepath = "http://"+probase.getProperty("resource.hn.address")+props.getProperty(propertyName);
        return filepath;
    }

    public String getOpenSet(){
        Resource base=new ClassPathResource("/conf/system.properties");
        Properties probase = null;
        try {
            probase=PropertiesLoaderUtils.loadProperties(base);
            return probase.getProperty("resource.hu.OpenTrans");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "0";

    }


    public String getFtpaddr(){
        Resource base=new ClassPathResource("/conf/system.properties");
        Properties probase = null;
        try {
            probase=PropertiesLoaderUtils.loadProperties(base);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return probase.getProperty("resourse.hnftp.addr");
    }

    public String getFtpUsername(){
        Resource base=new ClassPathResource("/conf/system.properties");
        Properties probase = null;
        try {
            probase=PropertiesLoaderUtils.loadProperties(base);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return probase.getProperty("resourse.hnftp.username");
    }

    public String getFtpPwd(){
        Resource base=new ClassPathResource("/conf/system.properties");
        Properties probase = null;
        try {
            probase=PropertiesLoaderUtils.loadProperties(base);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return probase.getProperty("resourse.hnftp.password");
    }


    public String getFtpDir(){
        Resource base=new ClassPathResource("/conf/system.properties");
        Properties probase = null;
        try {
            probase=PropertiesLoaderUtils.loadProperties(base);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return probase.getProperty("resourse.hnftp.dir");
    }

    public String getFtpLocalpath(){
        //resource.ftp.localpath
        Resource base=new ClassPathResource("/conf/system.properties");
        Properties probase = null;
        try {
            probase=PropertiesLoaderUtils.loadProperties(base);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return probase.getProperty("resourse.ftp.localpath");
    }
}
