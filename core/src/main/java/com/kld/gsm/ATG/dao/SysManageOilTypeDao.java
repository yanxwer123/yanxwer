package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.SysManageOilType;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@MySqlRepository
public interface SysManageOilTypeDao {
    int insert(SysManageOilType record);
    SysManageOilType selectByPrimaryKey(String oilno);
    int updateByPrimaryKey(SysManageOilType record);
    int deleteByPrimaryKey(String oilno);
    int insertSelective(SysManageOilType record);
    int deleteAll();
    int insertList(@Param("list")List<SysManageOilType> sysmanageoiltypeList);
    List<SysManageOilType> selectByTrans(String trans);
    List<SysManageOilType> selectByInUse();
    //按照oilno查询出油品名称
    String selectOilNo(String OilNo);
    /**
     * 查询在使用的油品列表 ny
     * */
    List<SysManageOilType> selectUse();
}