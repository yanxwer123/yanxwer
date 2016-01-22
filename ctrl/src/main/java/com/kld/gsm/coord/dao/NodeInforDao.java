package com.kld.gsm.coord.dao;

import com.kld.gsm.coord.domain.NodeInfor;
import com.kld.gsm.coord.mybatis.SybaseRepository;

import java.util.List;

/**
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2015.11.8
 * @Description:单位信息
 */
@SybaseRepository
public interface NodeInforDao  {
    List<NodeInfor> selectNodeInfor();
}
