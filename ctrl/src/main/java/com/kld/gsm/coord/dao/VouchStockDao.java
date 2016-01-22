package com.kld.gsm.coord.dao;

import com.kld.gsm.ATG.common.base.BaseDao;
import com.kld.gsm.coord.domain.VouchStock;
import com.kld.gsm.coord.mybatis.SybaseRepository;

@SybaseRepository
public interface VouchStockDao extends BaseDao<VouchStock, Long> {
    int insert(VouchStock vouchStock);
}
