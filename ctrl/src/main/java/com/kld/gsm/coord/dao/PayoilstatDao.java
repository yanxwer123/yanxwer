package com.kld.gsm.coord.dao;

import com.kld.gsm.coord.domain.Payoilstat;
import com.kld.gsm.coord.mybatis.SybaseRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import java.util.List;

/**
 * Created by yinzhiguang on 2015/11/8.
 */
@SybaseRepository
public interface PayoilstatDao {
    List<Payoilstat> getPayoilstat(String oilvoch);
    List<Payoilstat> getPayoilstat1(@Param("sql")String sql);
    List<Payoilstat> getPayoilstat2(@Param("sql1")String sql);

}
