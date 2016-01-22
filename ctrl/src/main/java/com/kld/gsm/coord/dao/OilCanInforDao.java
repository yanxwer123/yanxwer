package com.kld.gsm.coord.dao;

import com.kld.gsm.coord.domain.OilCanInfor;
import com.kld.gsm.coord.mybatis.SybaseRepository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2015/11/10 11:30
 * @Description:
 */
@SybaseRepository
public interface OilCanInforDao {
    List<OilCanInfor> selectOilCanInfor();
    List<OilCanInfor> findByOilNo(String oilno);
}
