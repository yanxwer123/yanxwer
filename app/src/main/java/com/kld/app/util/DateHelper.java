package com.kld.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 1 on 2015/10/26.
 */
public class  DateHelper {
    public static String getNowDateFmt(String format){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    public static String getNowDateFmt(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }
    public static String getDateFmt(Date date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(format!=null&&!"".equals(format.trim())) {
            sdf = new SimpleDateFormat(format);
        }
        return sdf.format(date);
    }
    public static String getDateFmt(String date,String fformat,String tformat){
        Date d = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            sdf = new SimpleDateFormat(fformat);
            d = sdf.parse(date);
            sdf = new SimpleDateFormat(tformat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(d);
    }

    public static Date StringToDateFmt(String sdate,String format){
        if(sdate==null||"".equals(sdate.trim())){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            sdf =new SimpleDateFormat(format);
            ////System.out.println(sdate+"FFFFFFFFFF");
            date = sdf.parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static Date StringToDateFmt(String sdate){
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static Date getDate(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
