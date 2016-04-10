package com.kld.gsm.coord.dao;

import com.kld.gsm.ATG.common.base.BaseDao;
import com.kld.gsm.ATG.domain.OilVouch;
import com.kld.gsm.coord.mybatis.SybaseRepository;

import java.util.List;
import java.util.Map;

@SybaseRepository
public interface OilVouchDao extends BaseDao<OilVouch, Long> {
    OilVouch getOilVouch(Map map);
    OilVouch getOilVouch1(String sql);
    List<OilVouch> selectByshift(String oilvoch);
    List<com.kld.gsm.ATG.domain.OilVouch> selectByshift1(String oilvoch);
}
