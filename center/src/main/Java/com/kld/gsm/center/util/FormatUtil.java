package com.kld.gsm.center.util;

import java.text.DecimalFormat;

/**
 * Created by qiwei on 2016/2/2.
 */
public class FormatUtil {
    public static final String DOUBLEKEEPDIGIT = "0.00";

    public  static String ConvertToString(Object o)
    {
        if(o==null)
        {
            return "";
        }
        else
        {
            return o.toString();
        }
    }
    public  static String DoubleFormat(Double o,String strFormat)
    {
        if(o==null)
        {
            return "";
        }
        else
        {
            DecimalFormat df   =new   java.text.DecimalFormat(strFormat);
            return df.format(o);
        }
    }
}
