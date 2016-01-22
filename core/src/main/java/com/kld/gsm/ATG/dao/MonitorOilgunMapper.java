package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.MonitorOilgun;
import com.kld.gsm.ATG.domain.MonitorOilgunKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@MySqlRepository
public interface MonitorOilgunMapper {
    int deleteByPrimaryKey(MonitorOilgunKey key);

    int insert(MonitorOilgun record);

    int insertSelective(MonitorOilgun record);

    MonitorOilgun selectByPrimaryKey(MonitorOilgunKey key);

    int updateByPrimaryKeySelective(MonitorOilgun record);

    int updateByPrimaryKey(MonitorOilgun record);

    List<MonitorOilgun> selectByID(String id);

    int insertList(@Param("list")List<MonitorOilgun> guns);
}