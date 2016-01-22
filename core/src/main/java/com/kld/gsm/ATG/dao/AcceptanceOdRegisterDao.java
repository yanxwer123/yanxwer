package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MySqlRepository
public interface AcceptanceOdRegisterDao {
    int deleteByPrimaryKey(String deliveryno);

    int insert(com.kld.gsm.ATG.domain.AcceptanceOdRegister record);

    int insertSelective(com.kld.gsm.ATG.domain.AcceptanceOdRegister record);

    AcceptanceOdRegister selectByPrimaryKey(String deliveryno);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.AcceptanceOdRegister record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.AcceptanceOdRegister record);

    List<com.kld.gsm.ATG.domain.AcceptanceOdRegister> selectByTrans(String trans);

    List<AcceptanceOdRegister> selectWHDByoil(String oilno);

    List<HashMap<String,?>>selectjhysbynoanddate(Map params);

    Map getOdregRate(Map param);

    //根据日结  最小班次号和最大班次号 查询到每种油品的标准体积
    List<HashMap> findByShift(Map map);
    int updateByManualNo(HashMap map);

}