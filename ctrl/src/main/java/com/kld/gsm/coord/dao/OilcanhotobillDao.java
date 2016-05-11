package com.kld.gsm.coord.dao;

import com.kld.gsm.coord.domain.Oilcanhotobill;
import com.kld.gsm.coord.mybatis.SybaseRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yinzhiguang on 2015/11/8.
 */
@SybaseRepository
public interface OilcanhotobillDao {
    List<Oilcanhotobill> getOilcanhotobill(String oilvoch);

    List<Oilcanhotobill> getOilcanhotobill1(@Param("sql")String sql);

}
