package com.kld.gsm.util;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by luyan on 15/8/27.
 */
public class SysConfig {

    private static Properties  map=new Properties();
    static{
        try {
            InputStream is=SysConfig.class.getResourceAsStream("/conf/system.properties");
            map.load(is);
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getSystemUrl(){

        return map.getProperty("url");
    }

}
