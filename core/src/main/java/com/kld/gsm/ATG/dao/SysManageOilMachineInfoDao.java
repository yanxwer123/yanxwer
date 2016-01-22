package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.SysManageOilMachineInfo;

import java.util.List;

@MySqlRepository
public interface SysManageOilMachineInfoDao {
    int deleteAll();

    int deleteByPrimaryKey(Integer macno);


    int insert(SysManageOilMachineInfo record);

    int insertSelective(SysManageOilMachineInfo record);

    SysManageOilMachineInfo selectByPrimaryKey(Integer macno);

    int updateByPrimaryKeySelective(SysManageOilMachineInfo record);

    int updateByPrimaryKey(SysManageOilMachineInfo record);

    List<SysManageOilMachineInfo> selectByTrans(String trans);
}