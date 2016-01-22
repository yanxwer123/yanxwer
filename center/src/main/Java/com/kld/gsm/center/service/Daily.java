package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.hn.JTGC;
import com.kld.gsm.center.domain.oss_monitor_tankoil;
import org.apache.tools.ant.taskdefs.optional.extension.LibFileSet;

import java.text.ParseException;
import java.util.List;

/**
 * Created by xhz on 2015/12/9.
 */
public interface Daily {
    ResultMsg selectRemoteInfo(Integer page, Integer rows,String begin,String end,String oucode);
    ResultMsg selectRemoteAllInfo(String begin,String end,String oucode);
    String selectOilName(String oilcan,String oucode);

    //静态罐存
    List<oss_monitor_tankoil> addJTGC(List<JTGC> jtgcs,String nodeno);

    int Updatetankoil(List<oss_monitor_tankoil> records);
}





