package com.kld.gsm.coord.domain;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2016/4/11 15:32
 * @Description:
 */
public class AbsValueBean {
    Logger LOGGER = Logger.getLogger(this.getClass());
    public String getInsertSql(String tableName){
        String sql = "";
        try {
            String fieldst = "";
            String valuest = "";
            StringBuffer fieldStr = new StringBuffer();
            StringBuffer valueStr = new StringBuffer();
            Field[] fields = this.getClass().getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (int i = 0; i < fields.length; i++) {
                    Method method = this.getClass().getMethod("get" + toUpperCaseFirstOne(fields[i].getName()));
                    Object obj = method.invoke(this);
                    if (obj != null && !"".equals(obj)) {
                        fields[i].setAccessible(true);
                        fieldStr.append(fields[i].getName());
                        Class fieldClass = fields[i].getType();
                        if( fieldClass == Date.class){
                            Date date = (java.util.Date)obj;
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String datestr = sdf.format(date);
                            valueStr.append("'"+datestr+"'");
                        }else {
                            valueStr.append("'"+obj+"'");
                        }
                        //如果不是最后一个字段，则加逗号
                        fieldStr.append(",");
                        valueStr.append(",");
                    }
                    fieldst = fieldStr.substring(0, fieldStr.length()-1);
                    valuest = valueStr.substring(0,valueStr.length()-1);
                }
            }
            sql = " insert into " + tableName
                    + " (" + fieldst + ") values (" + valuest + ")";
            LOGGER.info("AbsValueBean的insert语句:" + sql);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        return sql;
    }
    public String getSelectAllSql(String tableName){
        String sql = "";
        try {
            StringBuffer fieldStr = new StringBuffer();
            Field[] fields = this.getClass().getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (int i = 0; i < fields.length; i++) {
                    fields[i].setAccessible(true);
                    fieldStr.append(fields[i].getName());
                    //如果不是最后一个字段，则加逗号
                    if (i != (fields.length - 1)) {
                        fieldStr.append(",");
                    }
                }
            }
            sql = " SELECT "+fieldStr+" FROM "+tableName;
            LOGGER.info("AbsValueBean的selectAllSql语句:" + sql);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        return sql;
    }
    //首字母转小写
    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    //首字母转大写
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    public  static void main(String args[]) throws Exception{
        VouchStock v = new VouchStock();
        v.setTtc(111111);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        v.setTakedate(date);
        System.out.println(v.getInsertSql("3333"));
    }
}
