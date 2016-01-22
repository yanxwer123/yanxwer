package com.kld.app.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-12-16 2:50
 * @Description:
 */
public class SysConfig {
    private static Properties map=new Properties();
    static{
        try {
            InputStream is=SysConfig.class.getResourceAsStream("/config/important.properties");
            map.load(is);
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getIP(){
        return map.getProperty("ctrl.ip");
    }

    public static int getPort() {

        return Integer.valueOf(map.getProperty("ctrl.port"));
    }

    public static String regmoteIp(){
        return "http://"+map.getProperty("center.address");
    }

    public static int CtrlTimeOut(){
        return Integer.parseInt(map.getProperty("ctrl.timeout"));
    }

    public static void main(String[] args) {

        ////System.out.println(SysConfig.getIP());
        ////System.out.println(SysConfig.getPort());

    }
}
