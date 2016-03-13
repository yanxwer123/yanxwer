package com.kld.gsm.center.service;

import javax.servlet.ServletOutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liuming on 2016/2/16.
 * 首页汇总信息接口服务
 */
public interface MonitorSummaryService {


    List<HashMap<String,Object>> selectSummary(HashMap<String,Object> map);

    List<HashMap<String,Object>> selectSummarybyParent(HashMap<String,Object> map);

    List<HashMap<String,Object>> selectSummaryCountbyParent(HashMap<String,Object> map);

    List<HashMap<String,Object>> getInventoryList(HashMap<String,Object> map);

    List<HashMap<String,Object>> getInventoryAllList(HashMap<String,Object> map);

    HashMap GetGqss(String nodeno);

    int AddSummaryData();

    public void downloadzdkc(List<HashMap<String,Object>> list,String [] titles,ServletOutputStream outputStream);




}
