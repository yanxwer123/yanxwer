package com.kld.gsm.coord.dao;

import com.kld.gsm.ATG.common.base.BaseDao;
import com.kld.gsm.coord.domain.VouchStock;
import com.kld.gsm.coord.mybatis.SybaseRepository;
import org.apache.ibatis.annotations.Param;

@SybaseRepository
public interface VouchStockDao extends BaseDao<VouchStock, Long> {
    int insert(VouchStock vouchStock);
    int insert1(@Param("sql")String sql);
}
