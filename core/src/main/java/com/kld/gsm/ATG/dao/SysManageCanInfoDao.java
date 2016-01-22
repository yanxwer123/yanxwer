package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.common.base.BaseDao;
import com.kld.gsm.ATG.domain.SysManageCanInfo;

import java.util.List;

@MySqlRepository
public interface SysManageCanInfoDao extends BaseDao<SysManageCanInfoDao,Long> {
    int deleteAll();

    int deleteByPrimaryKey(Integer oilcan);


    int insert(SysManageCanInfo record);

    int insertSelective(SysManageCanInfo record);

    SysManageCanInfo selectByPrimaryKey(Integer oilcan);

    int updateByPrimaryKeySelective(SysManageCanInfo record);

    List<SysManageCanInfo> selectSysmangeTank();

    int updateByPrimaryKey(SysManageCanInfo record);
    List<SysManageCanInfo> selectAll();

    List<SysManageCanInfo> selectByTrans(String trans);

    List<SysManageCanInfo> selectByOilNo(String oilno);

    List<String> findByOilcan(String oilno);
}