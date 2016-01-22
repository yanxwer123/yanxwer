package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.SysManageOilGunInfo;

import java.util.List;

@MySqlRepository
public interface SysManageOilGunInfoDao {
    int deleteAll();
    int deleteByPrimaryKey(Integer oilgun);



    int insert(SysManageOilGunInfo record);

    int insertSelective(SysManageOilGunInfo record);

    SysManageOilGunInfo selectByPrimaryKey(Integer oilgun);

    int updateByPrimaryKeySelective(SysManageOilGunInfo record);

    int updateByPrimaryKey(SysManageOilGunInfo record);

    List<SysManageOilGunInfo> selectAllOilGun();

    List<SysManageOilGunInfo> selectByTrans(String trans);

    List<SysManageOilGunInfo> selectByOilCan(String OilCanNo);

    List<Integer> selectOilGunByOilCanNo(Integer OilCanNo);

    List<SysManageOilGunInfo> selectOilInfoByOilCanNo(Integer OilCanNo);

    int selectOilCanByOilGun(int macno);
    List<SysManageOilGunInfo> findByOilCanNo(String canno);
}