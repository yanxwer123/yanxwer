package com.kld.gsm;

import java.io.InputStream;
import java.util.Properties;

/**
 * 系统配置项
 * Created by luyan on 15/11/14.
 */
  public  class  SysConfig {

    private static Properties map=new Properties();
    static{
        try {
            InputStream is=SysConfig.class.getResourceAsStream("/config/core.properties");
            map.load(is);
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //是否读取液位仪
    public static boolean IsReadATG()
    {
        return false;
    }
    //是否写入mysql
    public static boolean IsWriteMysql()
    {
        return true;
    }
    //是否传输卡号和金额
    public static boolean IsTransCardNoPrice()
    {
        return false;
    }

    public static String getCenterIP(){
        return "http://"+map.getProperty("center.address");
    }
}
