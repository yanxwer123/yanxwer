package com.kld.gsm.coord.domain;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
            StringBuffer fieldStr = new StringBuffer();
            StringBuffer valueStr = new StringBuffer();
            Field[] fields = this.getClass().getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (int i = 0; i < fields.length; i++) {
                    fields[i].setAccessible(true);
                    fieldStr.append(fields[i].getName());
                    Method method = this.getClass().getMethod("get" + toUpperCaseFirstOne(fields[i].getName()));
                    Object obj = method.invoke(this);
                    if (obj != null && !"".equals(obj)) {
                        valueStr.append(obj);
                    } else {
                        valueStr.append("''");
                    }
                    //如果不是最后一个字段，则加逗号
                    if (i != (fields.length - 1)) {
                        fieldStr.append(",");
                        valueStr.append(",");
                    }
                }
            }
            sql = " insert into " + tableName
                    + " (" + fieldStr + ") values (" + valueStr + ")";
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
        BillInfor billInfor = new BillInfor();
        billInfor.setBillname("111111");
        billInfor.setMaxvouchno("");
        System.out.println(billInfor.getSelectAllSql("3333"));
    }
}
