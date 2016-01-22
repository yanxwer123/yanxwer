package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_sysmanage_TankInfo;
import com.kld.gsm.center.domain.oss_sysmanage_TankInfoKey;

import java.util.List;

@MysqlRepository
public interface oss_sysmanage_TankInfoMapper {
    int deleteByPrimaryKey(oss_sysmanage_TankInfoKey key);

    int insert(oss_sysmanage_TankInfo record);

    int insertSelective(oss_sysmanage_TankInfo record);

    oss_sysmanage_TankInfo selectByPrimaryKey(oss_sysmanage_TankInfoKey key);

    int updateByPrimaryKeySelective(oss_sysmanage_TankInfo record);

    int updateByPrimaryKey(oss_sysmanage_TankInfo record);

    int merge(oss_sysmanage_TankInfo record);

    /**
     *
     * @return 返回所有的油罐
     */
    List<oss_sysmanage_TankInfo> findAll();

    /**
     *
     * @param oilno 油品编号
     * @return 油品名称
     */
    String findOilName(String oilno);
}