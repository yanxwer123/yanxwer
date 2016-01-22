package com.kld.gsm.util;


import java.net.URLEncoder;

/**
 * Created by luyan on 15/11/4.
 */
public class SybaseUtils {
    public static String getSybaseCNStr(String str)
    {
        try {
             return new String(str.getBytes("latin1"), "gb2312");

        }catch (Exception e1)
        {
            //System.out.println(e1.getMessage());
        }
        return "";
    }
}
