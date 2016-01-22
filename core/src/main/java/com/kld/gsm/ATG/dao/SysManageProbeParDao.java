package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.SysManageProbePar;

import java.util.HashMap;
import java.util.List;

@MySqlRepository
public interface SysManageProbeParDao {
    int deleteByPrimaryKey(String probemodel);

    int insert(SysManageProbePar record);

    int insertSelective(SysManageProbePar record);

    SysManageProbePar selectByPrimaryKey(String probemodel);

    int updateByPrimaryKeySelective(SysManageProbePar record);

    int updateByPrimaryKey(SysManageProbePar record);
    List<SysManageProbePar> selectAll();

    List<SysManageProbePar> selectByTrans(String trans);

    int ExisOilCan(HashMap hashMap);
    int ExisProbePort(HashMap hashMap);
}