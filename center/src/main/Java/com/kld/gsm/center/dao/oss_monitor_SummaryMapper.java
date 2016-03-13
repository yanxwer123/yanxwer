package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_monitor_Summary;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
@MysqlRepository
public interface oss_monitor_SummaryMapper {
    int deleteByPrimaryKey(String nodeno);

    int deleteAll();

    int insert(oss_monitor_Summary record);

    int insertlist(@Param("list")List<oss_monitor_Summary> oss_monitor_SummaryList);

    int insertSelective(oss_monitor_Summary record);

    oss_monitor_Summary selectByPrimaryKey(String nodeno);

    int updateByPrimaryKeySelective(oss_monitor_Summary record);

    int updateByPrimaryKey(oss_monitor_Summary record);

    List<HashMap<String,Object>> selectSummary(HashMap<String,Object> map);

    List<HashMap<String,Object>> selectSummarybyParent(HashMap<String,Object> map);

    List<HashMap<String,Object>> selectSummaryCountbyParent(HashMap<String,Object> map);

    List<HashMap<String,Object>> selectSummaryServer(HashMap<String,Object> map);

}