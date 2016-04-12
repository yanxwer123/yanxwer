package com.kld.gsm.coord.dao;

import com.kld.gsm.coord.domain.OilMachineInfor;
import com.kld.gsm.coord.mybatis.SybaseRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2015.11.8
 * @Description:
 */
@SybaseRepository
public interface OilMachineInforDao {
    List<OilMachineInfor> selectOilMachineInfor();
    List<OilMachineInfor> selectOilMachineInfor1(@Param("sql")String sql);

}
