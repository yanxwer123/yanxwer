package com.kld.gsm.util;

import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="yanxwer@163.com">yanxiaowei</a>
 * @version 1.0
 * @CreationTime: 2015/11/21 18:51
 * @Description:通过VT获取V20的计算公式
 */
public class V20Utils {

    private static V20Utils instance =null;
    public static V20Utils getInstance(){
    if(instance ==null){
        instance=new V20Utils();
    }
        return instance;
    }

    /**
     * VT升数转V20升数的计算公式：汽油 （VT-V20）*0.0012*VT升数
     柴油（VT-V20）*0.0008*VT升数
     * @param VT 温度
     * @param V  净油体积
     * @return V
     */
    //汽油
    public Double getGasV20(Double VT,Double V ){
        V =V -(V*(VT-20)*0.0012);
        DecimalFormat df = new DecimalFormat("######0.00");
        V=Double.parseDouble(df.format(V));
        return V ;
    }
    //柴油
    public Double getDieV20(Double VT,Double V){
        V=V-(V*(VT-20)*0.0008);
        DecimalFormat df = new DecimalFormat("######0.00");
        V=Double.parseDouble(df.format(V));
        return V;
    }
}
