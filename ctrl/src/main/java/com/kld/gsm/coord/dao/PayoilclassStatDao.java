package com.kld.gsm.coord.dao;

import com.kld.gsm.coord.domain.PayoilclassStat;
import com.kld.gsm.coord.mybatis.SybaseRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import java.util.List;

/**
 * Created by yinzhiguang on 2015/11/8.
 */
@SybaseRepository
public interface PayoilclassStatDao {
    List<PayoilclassStat> getPayoilclassStat(String oilvoch);
    List<PayoilclassStat> getPayoilclassStat1(@Param("sql")String sql);
}
