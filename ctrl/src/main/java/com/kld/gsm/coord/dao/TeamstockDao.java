package com.kld.gsm.coord.dao;

import com.kld.gsm.coord.domain.Teamstock;
import com.kld.gsm.coord.mybatis.SybaseRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import java.util.List;

/**
 * 1.1.13.9	班结库存表(ATGTEAMSTOCK)
 */
@SybaseRepository
public interface TeamstockDao {
    List<Teamstock> getTeamstock(String oilvoch);
    List<Teamstock> getTeamstock1(@Param("sql")String sql);
}
