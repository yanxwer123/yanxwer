package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_sysmanage_cubage;
import com.kld.gsm.center.domain.oss_sysmanage_cubageInfo;
import com.kld.gsm.center.domain.oss_sysmanage_cubageKey;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@MysqlRepository
public interface oss_sysmanage_cubageMapper {
    int deleteByPrimaryKey(oss_sysmanage_cubageKey key);

    int insert(oss_sysmanage_cubage record);

    int insertSelective(oss_sysmanage_cubage record);

    oss_sysmanage_cubage selectByPrimaryKey(oss_sysmanage_cubageKey key);

    int updateByPrimaryKeySelective(oss_sysmanage_cubage record);

    int updateByPrimaryKey(oss_sysmanage_cubage record);

    int updateStatus(String nodeno,String oilcan);

    List<oss_sysmanage_cubage> selectByNodenoAndStatus(String nodeno,Integer status);

    List<oss_sysmanage_cubage> getCubages(Map<String,Object> map);

    int getCubageCounts(Map<String,Object> map);

    List<oss_sysmanage_cubage> getCubageVersions(Map<String,Object> map);

    List<Map<String,Object>> getCubageInfos(Map<String,Object> map);

    int useCubageSave(Map<String,Object> map);

    List<oss_sysmanage_cubage> getUntranCubages(Map<String,Object> map);

}