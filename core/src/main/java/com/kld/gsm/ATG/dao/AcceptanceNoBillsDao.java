package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.AcceptanceNoBills;

import java.util.List;

@MySqlRepository
public interface AcceptanceNoBillsDao {
    int deleteByPrimaryKey(String deliveryno);

    int insert(com.kld.gsm.ATG.domain.AcceptanceNoBills record);

    int insertSelective(com.kld.gsm.ATG.domain.AcceptanceNoBills record);

    com.kld.gsm.ATG.domain.AcceptanceNoBills selectByPrimaryKey(String deliveryno);

    int updateByPrimaryKeySelective(com.kld.gsm.ATG.domain.AcceptanceNoBills record);

    int updateByPrimaryKey(com.kld.gsm.ATG.domain.AcceptanceNoBills record);

    List<AcceptanceNoBills> selectByTrans(String trans);

    int merge (AcceptanceNoBills record);
}