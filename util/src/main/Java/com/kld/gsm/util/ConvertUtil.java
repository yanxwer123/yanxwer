package com.kld.gsm.util;


import org.apache.commons.beanutils.ConvertUtils;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ConvertUtil {

    public static <T> T getValue(String value, String fieldName, Class<T> clazz) {

        if (value == null) { // 如果获取参数值为null,则返回null
            return null;
        } else if (!value.equals("")) { // 如果获取参数值不为"",则通过convertGt方法进行类型转换后返回结果
            return convertGt(value, clazz);
        } else if (clazz.getName().equals(String.class.getName())) { // 如果获取参数值为""
            return convertGt(value, clazz);
        } else {// 如果获取参数值为"",并且clazz不是是String类型,则返回null
            return null;
        }
    }

    /**
     * @param <T>
     * @param value
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertGt(String value, Class<T> clazz) {
        if (value == null) { // 如果值为null,则返回null
            return null;
        } else if (value.equals("")
                && !clazz.getName().equals(String.class.getName())) { // 如果value值为"",而且要转为的类型不是string类型，那么就统一返回null，也就是空字符串不能转成任何其他类型的实体，只能返回null
            return null;
        } else if (Date.class.getName().equalsIgnoreCase(clazz.getName())) { // 增加对从String类型到Date
            return (T) convertSTD(value);
        }
        return (T) ConvertUtils.convert(value, clazz);
    }

    //日期类型的转换
    private static SimpleDateFormat simpleDateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date convertSTD(String date) {
        try {
            return simpleDateFormate.parse(date);
        } catch (ParseException e) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.valueOf(System.currentTimeMillis()));
            return Timestamp.valueOf(formatter.format(calendar.getTime()));
        }
    }

    public static String convertDTS(Date date) {
        return simpleDateFormate.format(date);
    }

    @Test
    public void TimStampTest() {
        String str = "1450622585296";
//        Date dat = new Date(System.currentTimeMillis());
//        GregorianCalendar gc = new GregorianCalendar();
//        gc.setTime(dat);
//        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        String sb = format.format(gc.getTime());
//        //System.out.println(sb);

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.valueOf(System.currentTimeMillis()));
        Date date = Timestamp.valueOf(formatter.format(calendar.getTime()));
        //System.out.println(date);

    }
}
