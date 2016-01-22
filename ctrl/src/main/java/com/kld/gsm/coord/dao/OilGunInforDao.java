package com.kld.gsm.coord.dao;

import com.kld.gsm.coord.domain.OilGunInfor;
import com.kld.gsm.coord.mybatis.SybaseRepository;

import java.util.List;
/**
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2015.11.8
 * @Description:库油枪信息
 */
@SybaseRepository
public interface OilGunInforDao{

    List<OilGunInfor> selectOilGunInfor();


}
