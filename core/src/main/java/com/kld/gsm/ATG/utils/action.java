package com.kld.gsm.ATG.utils;

import com.kld.gsm.ATG.domain.SysManageDict;
import com.kld.gsm.ATG.service.SysManageDic;
import com.kld.gsm.ATG.service.imp.SysManageDicImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import com.kld.gsm.ATG.dao.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;


/*
Created BY niyang
Created Date 2015/11/18
*/
public  class action {
    public  String getUri(String host,String propertyName){
        Resource resource = new ClassPathResource("/config/services.properties");

        Properties props = null;
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println("not get services.properties");
        }
        String filepath=host+props.getProperty(propertyName);
        return filepath;
    }
}
