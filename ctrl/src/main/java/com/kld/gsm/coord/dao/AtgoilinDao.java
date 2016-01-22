package com.kld.gsm.coord.dao;


import com.kld.gsm.coord.domain.AtgOilIn;
import com.kld.gsm.coord.mybatis.SybaseRepository;

/**
 * Created by chen on 2015/11/7.
 */
@SybaseRepository
public interface AtgoilinDao {

    int insertAtgoilin(AtgOilIn atgOilIn);

    int updateAtgoilin(int Oilcan);



}
