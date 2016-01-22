package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.StationStatus;
import com.kld.gsm.center.domain.oss_sysmanage_department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MysqlRepository
public interface oss_sysmanage_departmentMapper {
    int deleteByPrimaryKey(String sinopecnodeno);

    int insert(oss_sysmanage_department record);

    int insertSelective(oss_sysmanage_department record);

    oss_sysmanage_department selectByPrimaryKey(String sinopecnodeno);

    int updateByPrimaryKeySelective(oss_sysmanage_department record);

    int updateByPrimaryKey(oss_sysmanage_department record);

    int merge(oss_sysmanage_department record);

    List<oss_sysmanage_department> selectById(@Param("list")List<StationStatus> stationStatuses);
}