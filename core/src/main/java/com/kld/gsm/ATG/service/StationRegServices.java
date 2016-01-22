package com.kld.gsm.ATG.service;


/*
Created BY niyang
Created Date 2015/11/21
*/
public interface StationRegServices {

    boolean syndept(String host);

    // 液位仪
    boolean syniq(String host);

    // 油枪
    boolean synoilgun(String host);

    // 油机
    boolean  synoilmac(String host);

    // 探棒与罐关系表
    boolean synpatrel(String host);

    // 探棒信息表
    boolean synprobebar(String host);

    //油罐信息表
    boolean syntankinfo(String host);

    /**
     * @description 同步基础信息，主调度
     *
     * */
    boolean synsys(String host);

}
