package com.kld.gsm.coord.dao;

import com.kld.gsm.coord.domain.Oilhotobill;
import com.kld.gsm.coord.mybatis.SybaseRepository;

import java.util.List;

import java.util.List;

/**
 * Created by yinzhiguang on 2015/11/8.
 */
@SybaseRepository
public interface OilhotobillDao {
   List<Oilhotobill> getOilhotobill(String oilvoch);
}
