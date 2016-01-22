package com.kld.gsm.syntocenter.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;



/*
Created BY niyang
Created Date 2015/11/18
*/
public  class action {
    public  String getUri(String propertyName){
        Resource base=new ClassPathResource("/config/system.properties");
        Properties probase = null;
        try {
             probase=PropertiesLoaderUtils.loadProperties(base);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Resource resource = new ClassPathResource("/config/services.properties");
        Properties props = null;
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filepath = "http://"+probase.getProperty("resource.domain.address")+props.getProperty(propertyName);
        return filepath;
    }

    public String getHost(){
        Resource base=new ClassPathResource("/config/system.properties");
        Properties probase = null;
        try {
            probase=PropertiesLoaderUtils.loadProperties(base);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Resource resource = new ClassPathResource("/config/services.properties");
        Properties props = null;
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filepath = "http://"+probase.getProperty("resource.domain.address");
        return filepath;
    }

    public String getFtpaddr(){
        Resource base=new ClassPathResource("/config/system.properties");
        Properties probase = null;
        try {
            probase=PropertiesLoaderUtils.loadProperties(base);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return probase.getProperty("resource.ftp.address");
    }

    public String getFtpusername(){
        Resource base=new ClassPathResource("/config/system.properties");
        Properties probase = null;
        try {
            probase=PropertiesLoaderUtils.loadProperties(base);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return probase.getProperty("resource.ftp.username");
    }

    public String getFtppassword(){
        Resource base=new ClassPathResource("/config/system.properties");
        Properties probase = null;
        try {
            probase=PropertiesLoaderUtils.loadProperties(base);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return probase.getProperty("resource.ftp.password");
    }

    public String getCtrladdr(){
        Resource base=new ClassPathResource("/config/system.properties");
        Properties probase = null;
        try {
            probase=PropertiesLoaderUtils.loadProperties(base);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return probase.getProperty("resource.ctrl.address");
    }

    public String getFtpLocalpath(){
        //resource.ftp.localpath
        Resource base=new ClassPathResource("/config/system.properties");
        Properties probase = null;
        try {
            probase=PropertiesLoaderUtils.loadProperties(base);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return probase.getProperty("resource.ftp.localpath");
    }

    public String getFtpRemotepath(){
        Resource base=new ClassPathResource("/config/system.properties");
        Properties probase = null;
        try {
            probase=PropertiesLoaderUtils.loadProperties(base);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return probase.getProperty("resource.ftp.remotepath");
    }


    public String getctrlport(){
        Resource base=new ClassPathResource("/config/system.properties");
        Properties probase = null;
        try {
            probase=PropertiesLoaderUtils.loadProperties(base);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return probase.getProperty("resource.ctrl.port");
    }

    public boolean getRTopen(){
        Resource base=new ClassPathResource("/config/system.properties");
        Properties probase = null;
        try {
            probase=PropertiesLoaderUtils.loadProperties(base);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "1".equals(probase.getProperty("resource.realtime.tankandgun"));
    }

    public boolean getZipSwitch(){
        Resource base=new ClassPathResource("/config/system.properties");
        Properties probase = null;
        try {
            probase=PropertiesLoaderUtils.loadProperties(base);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "1".equals(probase.getProperty("resource.rtstcok.switch"));
    }




}
