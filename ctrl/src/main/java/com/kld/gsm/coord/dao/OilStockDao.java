package com.kld.gsm.coord.dao;


import com.kld.gsm.ATG.domain.AcceptanceOdRegisterInfo;
import com.kld.gsm.coord.mybatis.SybaseRepository;

/**
 * Created by chen on 2015/11/5.
 */
@SybaseRepository
public interface OilStockDao {
    AcceptanceOdRegisterInfo selectOilStock(String oilStock);

}
