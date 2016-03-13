package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_sysmanage_oilType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@MysqlRepository
public interface oss_sysmanage_oilTypeMapper {
    int deleteByPrimaryKey(String nodeno);

    int insert(oss_sysmanage_oilType record);

    int insertSelective(oss_sysmanage_oilType record);

    oss_sysmanage_oilType selectByPrimaryKey(String nodeno);

    int updateByPrimaryKeySelective(oss_sysmanage_oilType record);

    int updateByPrimaryKey(oss_sysmanage_oilType record);

    int merge(oss_sysmanage_oilType record);

    List<HashMap<String,String>> selectOilType();

    List<oss_sysmanage_oilType> selectinuse();

    String selectByoilCanNo(HashMap map);

    String selectByoilNo(HashMap map);

}