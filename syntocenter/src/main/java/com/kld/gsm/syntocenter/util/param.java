package com.kld.gsm.syntocenter.util;


import java.util.HashMap;
import java.util.Map;

/*
Created BY niyang
Created Date 2015/11/19
*/
public class param {

    public Map<String,String> getparam(){
        //获取站级信息
        Station station=new Station();
        Map<String,String> hm =new HashMap<String,String>();
        hm.put("NodeNo",station.GetNodeNo());
      // hm.put("OUCode",station.GetOuCode());
        return hm;
    }
}
