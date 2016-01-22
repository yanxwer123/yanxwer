package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.SysManageTimeSaleOut;
import com.kld.gsm.ATG.domain.SysManageTimeSaleOutKey;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MySqlRepository
public interface SysManageTimeSaleOutDao {
    int deleteByPrimaryKey(SysManageTimeSaleOutKey key);

    int insert(SysManageTimeSaleOut record);

    int insertSelective(SysManageTimeSaleOut record);

    SysManageTimeSaleOut selectByPrimaryKey(SysManageTimeSaleOutKey key);

    int updateByPrimaryKeySelective(SysManageTimeSaleOut record);

    int updateByPrimaryKey(SysManageTimeSaleOut record);

    List<SysManageTimeSaleOut> selectByTrans(String trans);

    /**
     * 获取几个连续的小时之间的销量总计
     * @return
     */
    List<SysManageTimeSaleOut> getOilSaleSum(Map para);

    int deleteAll();

    int insertAll();
}