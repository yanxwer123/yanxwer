package com.kld.gsm.coord.dao;

import com.kld.gsm.coord.domain.OilType;
import com.kld.gsm.coord.mybatis.SybaseRepository;

import java.util.List;

/**
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2015.11.8
 * @Description:机走油品编码
 */
@SybaseRepository
public interface OilTypeDao {
    List<OilType> selectOilType();
    List<OilType> selectOilType1(String sql);

}
