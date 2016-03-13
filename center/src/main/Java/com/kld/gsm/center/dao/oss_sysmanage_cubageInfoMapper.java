package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_sysmanage_cubage;
import com.kld.gsm.center.domain.oss_sysmanage_cubageInfo;
import com.kld.gsm.center.domain.oss_sysmanage_cubageInfoKey;
import org.springframework.stereotype.Repository;

import java.util.List;

@MysqlRepository
public interface oss_sysmanage_cubageInfoMapper {
    int deleteByPrimaryKey(oss_sysmanage_cubageInfoKey key);

    int insert(oss_sysmanage_cubageInfo record);

    int insertSelective(oss_sysmanage_cubageInfo record);

    oss_sysmanage_cubageInfo selectByPrimaryKey(oss_sysmanage_cubageInfoKey key);

    int updateByPrimaryKeySelective(oss_sysmanage_cubageInfo record);

    int updateByPrimaryKey(oss_sysmanage_cubageInfo record);

    List<oss_sysmanage_cubageInfo> selectByNodenoAndVersion(String nodeno,String version);

    List<oss_sysmanage_cubageInfo> selectByNodenoAndVersionandcanno(String nodeno,String version,String canno);

    List<oss_sysmanage_cubageInfo> getUntranCubageInfos(oss_sysmanage_cubage cubage);

}