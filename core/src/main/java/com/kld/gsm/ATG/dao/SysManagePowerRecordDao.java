package com.kld.gsm.ATG.dao;


import com.kld.gsm.ATG.domain.SysManagePowerRecord;
import com.kld.gsm.ATG.domain.SysManagePowerRecordKey;

public interface SysManagePowerRecordDao {
    int deleteByPrimaryKey(SysManagePowerRecordKey key);

    int insert(SysManagePowerRecord record);

    int insertSelective(SysManagePowerRecord record);

    SysManagePowerRecord selectByPrimaryKey(SysManagePowerRecordKey key);

    int updateByPrimaryKeySelective(SysManagePowerRecord record);

    int updateByPrimaryKey(SysManagePowerRecord record);
}