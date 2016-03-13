package com.kld.gsm.ATG.dao;


import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.SysManageOilIn;
import com.kld.gsm.ATG.domain.SysManageOilInKey;
@MySqlRepository
public interface SysManageOilInDao {
    int deleteByPrimaryKey(SysManageOilInKey key);

    int insert(SysManageOilIn record);

    int insertSelective(SysManageOilIn record);

    SysManageOilIn selectByPrimaryKey(SysManageOilInKey key);

    int updateByPrimaryKeySelective(SysManageOilIn record);

    int updateByPrimaryKey(SysManageOilIn record);
}