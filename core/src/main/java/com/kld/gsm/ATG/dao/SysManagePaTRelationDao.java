package com.kld.gsm.ATG.dao;

import java.util.List;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.SysManagePaTRelation;
import com.kld.gsm.ATG.domain.SysManagePaTRelationKey;

@MySqlRepository
public interface SysManagePaTRelationDao {
    int deleteByPrimaryKey(SysManagePaTRelationKey key);

    int insert(SysManagePaTRelation record);

    int insertSelective(SysManagePaTRelation record);

    SysManagePaTRelation selectByPrimaryKey(SysManagePaTRelationKey key);

    int updateByPrimaryKeySelective(SysManagePaTRelation record);

    int updateByPrimaryKey(SysManagePaTRelation record);
    
    List<SysManagePaTRelation> selectAll();

    List<SysManagePaTRelation> selectByTrans(String trans);

    List<SysManagePaTRelation> select(SysManagePaTRelation record);
   //todo 缺少map实现.
    List<SysManagePaTRelation> selectByOilNo(String oilCan);

}