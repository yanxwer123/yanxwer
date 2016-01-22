package com.kld.gsm.coord.utils;

import com.kld.gsm.ATGDevice.ATGManager;
import com.kld.gsm.ATGDevice.atg_failure_data_out_t;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2016/1/7 21:38
 * @Description:
 */
public class ComparatorUtils implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        atg_failure_data_out_t f1 = (atg_failure_data_out_t)o1;
        atg_failure_data_out_t f2 = (atg_failure_data_out_t)o2;
        //1比较date
        int flag = f1.strDate.compareTo(f2.strDate);
        if (flag == 0) {
            //2比较time
            return f1.strTime.compareTo(f2.strTime);
        } else {
            return flag;
        }
    }
    //region 测试方法
    /*public static void main(String[] args){
        List<atg_failure_data_out_t> failureDataOutTList = new ArrayList<atg_failure_data_out_t>();
        ComparatorUtils comparator=new ComparatorUtils();
        atg_failure_data_out_t a1 = new atg_failure_data_out_t();
        a1.strDate="20161007";
        a1.strTime="121212";
        a1.uOilCanNO=1;
        atg_failure_data_out_t a2 = new atg_failure_data_out_t();
        a2.strDate="20160507";
        a2.strTime="222222";
        a2.uOilCanNO=2;
        atg_failure_data_out_t a3 = new atg_failure_data_out_t();
        a3.strDate="20160107";
        a3.strTime="020101";
        a3.uOilCanNO=2;
        atg_failure_data_out_t a4 = new atg_failure_data_out_t();
        a4.strDate="20160107";
        a4.strTime="150001";
        a4.uOilCanNO=1;
        failureDataOutTList.add(a1);
        failureDataOutTList.add(a2);
        failureDataOutTList.add(a3);
        failureDataOutTList.add(a4);
        Collections.sort(failureDataOutTList, comparator);
        for (int i=0;i<failureDataOutTList.size();i++){
            atg_failure_data_out_t user_temp=(atg_failure_data_out_t)failureDataOutTList.get(i);
            System.out.println(user_temp.strDate+","+user_temp.strTime+","+user_temp.uOilCanNO);
        }
    }*/
    //endregion
}
